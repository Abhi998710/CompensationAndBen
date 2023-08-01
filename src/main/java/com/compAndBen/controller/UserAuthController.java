package com.compAndBen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserAuthController {


        @RequestMapping("/helloworld")
        public String helloController()
        {
            return "Welcome to HousingHunting.com";
        }

    }