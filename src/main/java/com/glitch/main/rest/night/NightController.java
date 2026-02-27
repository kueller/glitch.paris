package com.glitch.main.rest.night;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.glitch.main.repositories.NightImageRepository;

@Controller
public class NightController {

    @Autowired
    private NightImageRepository imageRepository;

    @GetMapping("/night")
    public String night(Model model) {
        model.addAttribute("name", "night");
        model.addAttribute("images", imageRepository.findAll());
        return "night";
    }

}
