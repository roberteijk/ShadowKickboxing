/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.controllers;

import net.vandeneijk.shadowkickboxing.models.*;
import net.vandeneijk.shadowkickboxing.services.*;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightCleaner;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightFactory;
import net.vandeneijk.shadowkickboxing.services.tafficregulatorservice.TrafficRegulator;
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
    private final DefensiveModeService defensiveModeService;
    private final FightFactory fightFactory;
    private final FightCleaner fightCleaner;
    private final TrafficRegulator trafficRegulator;
    private final ConnectionLogService connectionLogService;

    private final int[] downloadLimits = {3, 5, 9, 17, 33, 65};

    public HomeController(FightService fightService, SpeedService speedService, LengthService lengthService, DefensiveModeService defensiveModeService, FightFactory fightFactory, FightCleaner fightCleaner, TrafficRegulator trafficRegulator, ConnectionLogService connectionLogService) {
        this.fightService = fightService;
        this.speedService = speedService;
        this.lengthService = lengthService;
        this.defensiveModeService = defensiveModeService;
        this.fightFactory = fightFactory;
        this.fightCleaner = fightCleaner;
        this.trafficRegulator = trafficRegulator;
        this.connectionLogService = connectionLogService;
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String getHomePage(HttpServletRequest request, Model model) {
        String requestedItem = "index";

        connectionLogService.save(new ConnectionLog(requestedItem, request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr()));

        model.addAttribute("speedList", speedService.getSpeedList());
        model.addAttribute("lengthList", lengthService.getLengthList());
        model.addAttribute("defensiveModeList", defensiveModeService.getDefensiveModeList());

        return requestedItem;
    }

    @PostMapping("/download")
    public ModelAndView download(ModelAndView modelAndView, @RequestParam("speed") long speedId, @RequestParam("length") long lengthId, @RequestParam(value = "defensiveMode", defaultValue = "0") long defensiveModeId , HttpServletRequest request) {
        ConnectionLog connectionLog = new ConnectionLog("download", request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr());

        Speed speed = speedService.findById(speedId).get();
        Length length = lengthService.findById(lengthId).get();
        DefensiveMode defensiveMode = defensiveModeService.findById(defensiveModeId).get();
        String fightName = fightService.retrieveFreshFight(speed, length, defensiveMode).getName();
        modelAndView.setViewName("redirect:/download/" + fightName + ".mp3");

        connectionLog.setAvailable(true);
        connectionLogService.save(connectionLog);

        return modelAndView;
    }

    @GetMapping("/download")
    public String downloadWrongParam(HttpServletRequest request) {
        ConnectionLog connectionLog = new ConnectionLog("download", request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr());
        connectionLog.setAvailable(false);
        connectionLogService.save(connectionLog);

        return "error";
    }

    @GetMapping("/download/{file_name}")
    public void downloadFileName(@PathVariable("file_name") String fileName, HttpServletResponse response, HttpServletRequest request) {
        ConnectionLog connectionLog = new ConnectionLog(".mp3", request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr());
        boolean availability = false;

        Fight fight;
        if (!trafficRegulator.isTrafficAllowed("download", request.getRemoteAddr(), ZonedDateTime.now(), downloadLimits)) {
            try {
                response.sendRedirect("/exceededdownload");
            }  catch (IOException ioEx) {
                logger.error("Error redirecting to exceeded page. Exception: " + ioEx);
            }
        } else if ((fight = fightService.getFight(fileName)) != null) {

            byte[] audioFragment = fightService.getFightAudioData(fight);
            response.setContentType("audio/mpeg");
            response.setContentLength(audioFragment.length);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            try (InputStream is = new BufferedInputStream(new ByteArrayInputStream(audioFragment)); OutputStream os = new BufferedOutputStream(response.getOutputStream())) {
                org.apache.commons.io.IOUtils.copy(is, os);
                response.flushBuffer();

                availability = true;
                fightCleaner.clean();
                fightFactory.createFight("English", fight.getSpeed(), fight.getLength(), fight.getDefensiveMode());
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

    @GetMapping("/info")
    public String getInfoPage(HttpServletRequest request) {
        String requestedItem = "info";

        connectionLogService.save(new ConnectionLog(requestedItem, request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr()));

        return requestedItem;
    }

    @GetMapping("/error")
    public String getErrorPage(HttpServletRequest request) {
        String requestedItem = "error";

        connectionLogService.save(new ConnectionLog(requestedItem, request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr()));

        return requestedItem;
    }

    @GetMapping("/exceededdownload")
    public String getExceededPage(HttpServletRequest request) {
        String requestedItem = "exceededdownload";

        connectionLogService.save(new ConnectionLog(requestedItem, request.getRequestURI(), ZonedDateTime.now(), request.getRemoteAddr()));

        return requestedItem;
    }
}
