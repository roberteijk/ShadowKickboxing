/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.controllers;

import net.vandeneijk.shadowkickboxing.startup.SeedDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String getHomePage() {
        logger.info("Home page requested.");
        return "index";
    }
}
