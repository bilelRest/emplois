package org.iset.emplois.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/child")
    public String childPage(Model model) {
        model.addAttribute("title", "Page Enfant");
        return "child";
    }
}
