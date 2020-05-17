/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.controllers;

import net.vandeneijk.shadowkickboxing.models.ConnectionLog;
import net.vandeneijk.shadowkickboxing.services.ConnectionLogService;
import net.vandeneijk.shadowkickboxing.services.FightService;
import net.vandeneijk.shadowkickboxing.services.LengthService;
import net.vandeneijk.shadowkickboxing.services.SpeedService;
import net.vandeneijk.shadowkickboxing.startup.SeedDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.ZonedDateTime;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);

    private final FightService fightService;
    private final SpeedService speedService;
    private final LengthService lengthService;
    private final ConnectionLogService connectionLogService;

    public HomeController(FightService fightService, SpeedService speedService, LengthService lengthService, ConnectionLogService connectionLogService) {
        this.fightService = fightService;
        this.speedService = speedService;
        this.lengthService = lengthService;
        this.connectionLogService = connectionLogService;
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String getHomePage(HttpServletRequest request, Model model) {
        String requestedItem = "index";

        connectionLogService.save(new ConnectionLog(requestedItem, request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr()));

        model.addAttribute("speedList", speedService.getSpeedList());
        model.addAttribute("lengthList", lengthService.getLengthList());

        return requestedItem;
    }

    @PostMapping("download/shadowkickboxing.mp3")
    public void download(@RequestParam("speed") long speedId, @RequestParam("length") long lengthId, HttpServletResponse response, HttpServletRequest request) {
        connectionLogService.save(new ConnectionLog(".mp3", request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr()));

        try (InputStream is = new BufferedInputStream(new ByteArrayInputStream(fightService.getFight(speedId, lengthId).getAudioFragment())); OutputStream os = response.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(is, os);
            response.flushBuffer();
        } catch (IOException ioEx) {
            logger.error("Error presenting a downloadable file to client. Exception: " + ioEx);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}
