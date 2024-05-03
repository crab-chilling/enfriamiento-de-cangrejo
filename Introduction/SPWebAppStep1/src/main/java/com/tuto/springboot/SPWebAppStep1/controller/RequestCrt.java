package com.tuto.springboot.SPWebAppStep1.controller;

import com.tuto.springboot.SPWebAppStep1.dao.PoneyDao;
import com.tuto.springboot.SPWebAppStep1.dto.PoneyFormDto;
import com.tuto.springboot.SPWebAppStep1.model.Poney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RequestCrt {

    @Value("${welcome.message}")
    private String message;

    @Autowired
    PoneyDao poneyDao;

    private static String messageLocal = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("messageLocal", messageLocal);
        return "index";
    }

    @RequestMapping(value = { "/view"}, method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("myPoney", poneyDao.getRandomPoney() );
        return "poneyView";
    }

    @RequestMapping(value = { "/list"}, method = RequestMethod.GET)
    public String viewList(Model model) {
        model.addAttribute("poneyList",poneyDao.getPoneyList() );
        return "poneyViewList";
    }


    @RequestMapping(value = { "/addPoney"}, method = RequestMethod.GET)
    public String addponey(Model model) {
        PoneyFormDto poneyForm = new PoneyFormDto();
        model.addAttribute("poneyForm", poneyForm);
        return "poneyForm";
    }

    @RequestMapping(value = { "/addPoney"}, method = RequestMethod.POST)
    public String addponey(Model model, @ModelAttribute("poneyForm") PoneyFormDto poneyForm) {
        Poney p = poneyDao.addPoney(poneyForm.getName(), poneyForm.getColor(), poneyForm.getSuperPower(), poneyForm.getImgUrl());
        model.addAttribute("myPoney", p);
        return "poneyView";
    }
}
