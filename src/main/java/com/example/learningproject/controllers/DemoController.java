package com.example.learningproject.controllers;

import com.example.learningproject.common.Coach;
import com.example.learningproject.common.CricketCoach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    private Coach myCoach;


    @Autowired
    public DemoController(@Qualifier("aquatic") Coach theCoach) {
        System.out.println("In cstr: " + getClass().getSimpleName());
        myCoach = theCoach;
    }

    @GetMapping("/dailyworkout")
    public String getDailyWorkout() {
        return myCoach.getDailyWorkout();
    }

    @GetMapping("/check")
    public String check() {
        return "check";
    }
}
