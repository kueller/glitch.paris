package com.glitch.main.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ModelGlobals {

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("toplevelpages", TopLevelPages.values() );
    }

}
