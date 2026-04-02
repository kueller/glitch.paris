package com.glitch.main.rest.etc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EtcController {

    @GetMapping("/etc")
    public String etc(Model model) {
        model.addAttribute("name", "etc");
        return "etc";
    }

}
