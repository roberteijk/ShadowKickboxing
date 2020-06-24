/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.controllers;

import net.vandeneijk.shadowkickboxing.models.*;
import net.vandeneijk.shadowkickboxing.services.*;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightCleaner;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightFactory;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightFactoryJob;
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
import java.util.NoSuchElementException;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);

    private final InstructionService instructionService;
    private final FightService fightService;
    private final SpeedService speedService;
    private final LengthService lengthService;
    private final DefensiveModeService defensiveModeService;
    private final ExpertiseService expertiseService;
    private final FightFactory fightFactory;
    private final FightCleaner fightCleaner;
    private final TrafficRegulator trafficRegulator;
    private final ConnectionLogService connectionLogService;

    private final int[] downloadLimits = {5, 9, 17, 33, 65, 129};

    public HomeController(InstructionService instructionService, FightService fightService, SpeedService speedService, LengthService lengthService, DefensiveModeService defensiveModeService, ExpertiseService expertiseService, FightFactory fightFactory, FightCleaner fightCleaner, TrafficRegulator trafficRegulator, ConnectionLogService connectionLogService) {
        this.instructionService = instructionService;
        this.fightService = fightService;
        this.speedService = speedService;
        this.lengthService = lengthService;
        this.defensiveModeService = defensiveModeService;
        this.expertiseService = expertiseService;
        this.fightFactory = fightFactory;
        this.fightCleaner = fightCleaner;
        this.trafficRegulator = trafficRegulator;
        this.connectionLogService = connectionLogService;
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String getHomePage(HttpServletRequest request, Model model) {
        String requestedItem = "index";
        String requestMappingType = "get";

        logger.info("Page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr());
        connectionLogService.save(new ConnectionLog(requestedItem, request.getRequestURI(), requestMappingType, true, "", ZonedDateTime.now(), request.getRemoteAddr()));

        model.addAttribute("websiteDescription", getWebsiteDescription());
        model.addAttribute("generalInfo", getGeneralInfo());
        model.addAttribute("expertiseList", expertiseService.getExpertiseList());
        model.addAttribute("expertiseTitle", getExpertiseTitle());
        model.addAttribute("expertiseInfo", getExpertiseInfo());
        model.addAttribute("speedList", speedService.getSpeedList());
        model.addAttribute("speedTitle", getSpeedTitle());
        model.addAttribute("speedInfo", getSpeedInfo());
        model.addAttribute("lengthList", lengthService.getLengthList());
        model.addAttribute("lengthTitle", getLengthTitle());
        model.addAttribute("lengthInfo", getLengthInfo());
        model.addAttribute("defensiveModeList", defensiveModeService.getDefensiveModeList());
        model.addAttribute("defensiveModeTitle", getDefensiveModeTitle());
        model.addAttribute("defensiveModeInfo", getDefensiveModeInfo());
        model.addAttribute("generateAndDownloadButtonInfo", getGenerateAndDownloadButtonInfo());

        return requestedItem;
    }

    @PostMapping("/download")
    public ModelAndView download(ModelAndView modelAndView,
                                 @RequestParam("expertise") long expertiseId,
                                 @RequestParam("speed") long speedId,
                                 @RequestParam("length") long lengthId,
                                 @RequestParam(value = "defensiveMode", defaultValue = "3")
                                             long defensiveModeId, HttpServletRequest request) {
        String requestedItem = "download";
        String requestMappingType = "post";

        ConnectionLog connectionLog = new ConnectionLog(requestedItem, request.getRequestURI(), requestMappingType, true, "", ZonedDateTime.now(), request.getRemoteAddr());

        if (!trafficRegulator.isTrafficAllowed("download", request.getRemoteAddr(), ZonedDateTime.now(), downloadLimits)) {
            logger.info("Refused page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr() + " Reason: TrafficRegulator.");
            connectionLog.setAvailable(false);
            connectionLog.setInfo("TrafficRegulator");
            modelAndView.setViewName("redirect:/exceededdownload");
        } else {
            try {
                Expertise expertise = expertiseService.findById(expertiseId).get();
                Speed speed = speedService.findById(speedId).get();
                Length length = lengthService.findById(lengthId).get();
                DefensiveMode defensiveMode = defensiveModeService.findById(defensiveModeId).get();

                new FightFactoryJob("English", instructionService.findAll(), expertise, defensiveMode, speed, length); // TODO Remove. Only temp for testing.

                Fight fight = null;
                while ((fight = fightService.retrieveFreshFight(speed, length, defensiveMode, expertise)) == null) {
                    try {
                        Thread.sleep(333);
                    } catch (InterruptedException iEx) {
                        iEx.printStackTrace();
                    }
                }

//                fightFactory.createFight("English", fight.getSpeed(), fight.getLength(), fight.getDefensiveMode(), fight.getExpertise());


//                modelAndView.setViewName("redirect:/download/" + fight.getName() + ".mp3");

                logger.info("Page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr());
            } catch (NoSuchElementException nseEx) {
                modelAndView.setViewName("redirect:/error");

                logger.info("Refused page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr() + " Reason: Incorrect @RequestParam values.");
                connectionLog.setAvailable(false);
                connectionLog.setInfo("Incorrect @RequestParam values.");
            }
        }


        connectionLogService.save(connectionLog);

        return modelAndView;
    }

    @GetMapping("/download")
    public String downloadWrongParam(HttpServletRequest request) {
        String requestedItem = "download";
        String requestMappingType = "get";

        logger.warn("Refused page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr());
        ConnectionLog connectionLog = new ConnectionLog(requestedItem, request.getRequestURI(), requestMappingType, false, "", ZonedDateTime.now(), request.getRemoteAddr());
        connectionLogService.save(connectionLog);

        return "error";
    }

    @GetMapping("/download/{file_name}")
    public void downloadFileName(@PathVariable("file_name") String fileName, HttpServletResponse response, HttpServletRequest request) {
        String requestedItem = "download/" + fileName;
        String requestMappingType = "get";

        ConnectionLog connectionLog = new ConnectionLog(requestedItem, request.getRequestURI(), requestMappingType, true, "", ZonedDateTime.now(), request.getRemoteAddr());

        Fight fight;
        if (!trafficRegulator.isTrafficAllowed("download/filename", request.getRemoteAddr(), ZonedDateTime.now(), downloadLimits)) {
            try {
                logger.info("Refused page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr() + " Reason: TrafficRegulator.");
                connectionLog.setAvailable(false);
                connectionLog.setInfo("TrafficRegulator");
                response.sendRedirect("/exceededdownload");
            }  catch (IOException ioEx) {
                logger.error("Error redirecting to exceeded page. Exception: " + ioEx);
            }
        } else if ((fight = fightService.getFight(fileName)) != null) {
            logger.info("Page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr());
            fightCleaner.clean();

            byte[] audioFragment = fightService.getFightAudioData(fight);
            response.setContentType("audio/mpeg");
            response.setContentLength(audioFragment.length);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            try (InputStream is = new BufferedInputStream(new ByteArrayInputStream(audioFragment)); OutputStream os = new BufferedOutputStream(response.getOutputStream())) {
                org.apache.commons.io.IOUtils.copy(is, os);
                response.flushBuffer();
            } catch (ClientAbortException caEx) {
                // Ignore.
            } catch (IOException ioEx) {
                logger.error("Error presenting a downloadable file to client. Exception: " + ioEx);
                throw new RuntimeException("IOError writing file to output stream");
            }
        } else {
            try {
                logger.info("Refused page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr() + " Reason: File does not exist.");
                connectionLog.setAvailable(false);
                connectionLog.setInfo("File does not exist.");
                response.sendRedirect("/error");
            }  catch (IOException ioEx) {
                logger.error("Error redirecting to exceeded page. Exception: " + ioEx);
            }

        }

        connectionLogService.save(connectionLog);
    }

    // TODO Remove when not on top of Google search results.
    @GetMapping("/info")
    public void getInfoPage(HttpServletRequest request, HttpServletResponse response) {
        String requestedItem = "info";
        String requestMappingType = "get";

        logger.info("Page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr());
        connectionLogService.save(new ConnectionLog(requestedItem, request.getRequestURI(), requestMappingType, true, "redirected to /", ZonedDateTime.now(), request.getRemoteAddr()));

        try {
            response.sendRedirect("/");
        }  catch (IOException ioEx) {
            logger.error("Error redirecting to exceeded page. Exception: " + ioEx);
        }
    }

    @GetMapping("/error")
    public String getErrorPage(HttpServletRequest request) {
        String requestedItem = "error";
        String requestMappingType = "get";

        logger.info("Page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr());
        connectionLogService.save(new ConnectionLog(requestedItem, request.getRequestURI(), requestMappingType, true, "", ZonedDateTime.now(), request.getRemoteAddr()));

        return requestedItem;
    }

    @GetMapping("/exceededdownload")
    public String getExceededPage(HttpServletRequest request) {
        String requestedItem = "exceededdownload";
        String requestMappingType = "get";

        logger.info("Page \"" + requestedItem + "\" (" + requestMappingType + ") requested by: " + request.getRemoteAddr());
        connectionLogService.save(new ConnectionLog(requestedItem, request.getRequestURI(), requestMappingType, true, "", ZonedDateTime.now(), request.getRemoteAddr()));

        return requestedItem;
    }

    // Placeholder for language implementation.
    private String getWebsiteDescription() {
        return "Generate your own custom shadow boxing or kickboxing training mp3 files for free. Excellent as home workout for beginners and advanced fighters.";
    }

    // Placeholder for language implementation.
    private String getGeneralInfo() {
        return "Robert's shadow kickboxing generator is a great tool to learn or improve your boxing and kickboxing skills. The exercises given by the mp3 audio file will increase your cardiovascular capacity, flexibility, and balance. But the most important benefit will probably be faster reflexes and coordination.\n" +
                "\n" +
                "You can practice shadow boxing or kickboxing as a beginner, novice, intermediate or even advanced fighter. The speed and complexity of the training are customizable. To further develop you kicks, jabs and the like, you could include gloves, a heavy bag, or a mirror in your drills. With a headset you could even work out in the park. But at home is almost just as fun.\n" +
                "\n" +
                "Are you up to the challenge?\n" +
                "\n" +
                "Copyright 2020, Robert van den Eijk\n";
    }

    // Placeholder for language implementation.
    private String getExpertiseTitle() {
        return "style of training";
    }

    // Placeholder for language implementation.
    private String getExpertiseInfo() {
        return "Style of training determines which moves will be included in your training.\n" +
                "\n" +
                "For example, shadow kickboxing will allow the use of your hands, feet, knees, etc. while shadow boxing only allows you to use your hands.\n";
    }

    // Placeholder for language implementation.
    private String getSpeedTitle() {
        return "speed of instructions";
    }

    // Placeholder for language implementation.
    private String getSpeedInfo() {
        return "Speed of instructions determines the time you get to execute a command.\n" +
                "\n" +
                "For example, fast will test your reaction speed more than slow.\n";
    }

    // Placeholder for language implementation.
    private String getLengthTitle() {
        return "length of training";
    }

    // Placeholder for language implementation.
    private String getLengthInfo() {
        return "Length of training determines the number of training rounds in one file.\n" +
                "\n" +
                "Between rounds you get a 1-minute resting period.\n";
    }

    // Placeholder for language implementation.
    private String getDefensiveModeTitle() {
        return "defensive instructions";
    }

    // Placeholder for language implementation.
    private String getDefensiveModeInfo() {
        return "Defensive instructions determine whether you get block and/or evade instructions interlaced with offensive moves.\n" +
                "\n" +
                "Note that even with defensive moves enabled, offensive moves will still make up most of the instructions given.\n";
    }

    // Placeholder for language implementation.
    private String getGenerateAndDownloadButtonInfo() {
        return "A custom mp3 audio file will be generated and presented to you for download. The content of this audio file will depend on the chosen settings.\n" +
                "\n" +
                "Every file will have a unique combination of instructions and timings. even with the same specifications.\n" +
                "\n" +
                "Happy shadow (kick)boxing!!!\n";
    }
}
