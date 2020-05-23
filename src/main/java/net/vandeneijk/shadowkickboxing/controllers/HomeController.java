/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.controllers;

import net.vandeneijk.shadowkickboxing.models.ConnectionLog;
import net.vandeneijk.shadowkickboxing.models.Fight;
import net.vandeneijk.shadowkickboxing.models.Length;
import net.vandeneijk.shadowkickboxing.models.Speed;
import net.vandeneijk.shadowkickboxing.services.ConnectionLogService;
import net.vandeneijk.shadowkickboxing.services.FightService;
import net.vandeneijk.shadowkickboxing.services.LengthService;
import net.vandeneijk.shadowkickboxing.services.SpeedService;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightCleaner;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightFactory;
import net.vandeneijk.shadowkickboxing.startup.SeedDatabase;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    private final FightFactory fightFactory;
    private final FightCleaner fightCleaner;
    private final ConnectionLogService connectionLogService;

    public HomeController(FightService fightService, SpeedService speedService, LengthService lengthService, FightFactory fightFactory, FightCleaner fightCleaner, ConnectionLogService connectionLogService) {
        this.fightService = fightService;
        this.speedService = speedService;
        this.lengthService = lengthService;
        this.fightFactory = fightFactory;
        this.fightCleaner = fightCleaner;
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

    @PostMapping("/download")
    public ModelAndView download(ModelAndView modelAndView, @RequestParam("speed") long speedId, @RequestParam("length") long lengthId, HttpServletRequest request) {
        connectionLogService.save(new ConnectionLog("download", request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr()));

        Speed speed = speedService.findById(speedId).get();
        Length length = lengthService.findById(lengthId).get();
        fightFactory.createFight("English", speed, length);
        String fightName = fightService.retrieveFreshFight(speed, length).getName();
        modelAndView.setViewName("redirect:download/" + fightName + ".mp3");
        return modelAndView;
    }

    @GetMapping("/download/{file_name}")
    public void downloadFileName(@PathVariable("file_name") String fileName, HttpServletResponse response, HttpServletRequest request) {
        ConnectionLog connectionLog = new ConnectionLog(".mp3", request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr());
        boolean availability = false;

        fightCleaner.clean();

        Fight fight = getFight(fileName);
        if (fight != null) {
            byte[] audioFragment = fight.getAudioFragment();
            response.setContentType("audio/mpeg");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            try (InputStream is = new BufferedInputStream(new ByteArrayInputStream(audioFragment)); OutputStream os = response.getOutputStream()) {
                org.apache.commons.io.IOUtils.copy(is, os);
                response.flushBuffer();

                if (fight.getZdtFirstDownload() == null) {
                    fight.setZdtFirstDownload(ZonedDateTime.now());
                    fightService.save(fight);
                }

                availability = true;
            } catch (ClientAbortException caEx) {
                // Ignore.
            } catch (IOException ioEx) {
                logger.error("Error presenting a downloadable file to client. Exception: " + ioEx);
                throw new RuntimeException("IOError writing file to output stream");
            }
        }

        connectionLog.setAvailable(availability);
        connectionLogService.save(connectionLog);
    }

    private Fight getFight(String fileName) {
        String fightRandomId;
        String fightSpeedCode;
        String fightLengthCode;

        try {
            if (!fileName.substring(0, 4).equals("skb_")) return null;
            else if (!fileName.substring(16, 20).equals(".mp3")) return null;
            fightRandomId = fileName.substring(4, 12);
            fightSpeedCode = fileName.substring(12, 14);
            fightLengthCode = fileName.substring(14, 16);
        } catch (IndexOutOfBoundsException ioobEx) {
            return null;
        }

        Fight fight = fightService.findByRandomId(fightRandomId);
        if (fight != null && fight.getSpeed().getDescriptionIn2Chars().equals(fightSpeedCode) && fight.getLength().getDescriptionIn2Chars().equals(fightLengthCode)) return fight;

        return null;
    }
}
