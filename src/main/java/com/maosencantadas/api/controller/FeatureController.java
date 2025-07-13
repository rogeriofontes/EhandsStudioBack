package com.maosencantadas.api.controller;

import com.maosencantadas.commons.Constants;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/check-feature")
public class FeatureController {

    @Autowired
    private FF4j ff4j;

    @GetMapping
    public String checkFeature() {
        boolean isFeatureEnabled = ff4j.check(Constants.SEND_EMAIL);
        if (isFeatureEnabled) {
            return "Feature "+ Constants.SEND_EMAIL +"  is enabled!";
        } else {
            return "Feature "+ Constants.SEND_EMAIL +" is disabled.";
        }
    }
}
