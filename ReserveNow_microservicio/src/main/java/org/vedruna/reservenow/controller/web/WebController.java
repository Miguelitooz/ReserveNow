package org.vedruna.reservenow.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping(value = "/")
    public String index() {
        return "redirect:/redoc.html/";
    }
}
