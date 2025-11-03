package com.software.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReglasNegocioConfig {

    @Value("${app.rules.cancelationDays:7}")
    private int cancelationDays;

    @Value("${app.rules.maxActiveEnrollments:6}")
    private int maxActiveEnrollments;

    public int getCancelationDays() { 
        return cancelationDays; 
    }
    
    public int getMaxActiveEnrollments() {
        return maxActiveEnrollments; 
    }
}
