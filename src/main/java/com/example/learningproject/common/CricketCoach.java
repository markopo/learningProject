package com.example.learningproject.common;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class CricketCoach implements Coach {

    private List<String> workouts = List.of("30 pushups", "30 sit-ups", "20 burpees", "100 jumping jacks", "50 chin-ups");

    public CricketCoach() {
        System.out.println("In cstr: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        Random random = new Random();
        int n = random.nextInt(workouts.size());
        System.out.println("n: " + n);
        return workouts.get(n);
    }

//    @PostConstruct
//    public void doMyStartupStuff() {
//        System.out.println("In doMyStartupStuff: " + getClass().getSimpleName());
//    }
//
//    @PreDestroy
//    public void doMyCleanupStuff() {
//        System.out.println("In doMyCleanupStuff: " + getClass().getSimpleName());
//    }
}
