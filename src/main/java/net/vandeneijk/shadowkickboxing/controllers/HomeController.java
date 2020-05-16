/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.controllers;

import net.vandeneijk.shadowkickboxing.models.Speed;
import net.vandeneijk.shadowkickboxing.repositories.FightRepository;
import net.vandeneijk.shadowkickboxing.repositories.SpeedRepository;
import net.vandeneijk.shadowkickboxing.startup.SeedDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);

    FightRepository fightRepository;
    SpeedRepository speedRepository;

    public HomeController(FightRepository fightRepository, SpeedRepository speedRepository) {
        this.fightRepository = fightRepository;
        this.speedRepository = speedRepository;
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String getHomePage(Model model) {
        logger.info("Home page requested.");
        List<Speed> speedList = new ArrayList<>();
        speedRepository.findAll().forEach(speedList::add);
        model.addAttribute("speedList", speedList);
        return "index";
    }

//    @GetMapping("/download")
//    public String getDownload() {
//
//    }

    @GetMapping(value = "/files/{file_name}")
    public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response, HttpServletRequest request, Model model) {
            logger.info("IP client: " + request.getRemoteAddr());
        try {
            // get your file as InputStream
            InputStream is = new BufferedInputStream(new ByteArrayInputStream(fightRepository.findById(1L).get().getAudioFragment()));
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            logger.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

}
