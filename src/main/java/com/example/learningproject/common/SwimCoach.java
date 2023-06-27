package com.example.learningproject.common;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;


public class SwimCoach implements Coach {

    private List<String> workouts = List.of("Swim 1000 meters as a warmup.",
                                            "Swim 5000 m",
                                            "Swim 10 * 25 m intervals");

    public SwimCoach() {
        System.out.println("In cstr" + getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        Random random = new Random();
        int n = random.nextInt(workouts.size());
        return workouts.get(n);
    }
}
