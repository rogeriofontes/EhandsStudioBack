package com.maosencantadas.api.controller;

import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/features")
public class FeatureToggleController {

    @Autowired
    private FF4j ff4j;

    @PostMapping("/enable/{feature}")
    public String enableFeature(@PathVariable String feature) {
        if (!ff4j.exist(feature)) {
            return "Feature does not exist.";
        }
        ff4j.enable(feature);
        return "Feature " + feature + " enabled.";
    }

    @PostMapping("/disable/{feature}")
    public String disableFeature(@PathVariable String feature) {
        if (!ff4j.exist(feature)) {
            return "Feature does not exist.";
        }
        ff4j.disable(feature);
        return "Feature " + feature + " disabled.";
    }
}