package com.training.helpdesk.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HelpDeskController {
    
    /**
     * @return {@link ModelAndView} related to the '/help-desk' URL.
     */
    @GetMapping("/")
    public ModelAndView helpDeskApi() {
        return new ModelAndView("redirect:swagger-ui.html");
    }
}
