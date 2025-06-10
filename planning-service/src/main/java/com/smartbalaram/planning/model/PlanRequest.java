package com.smartbalaram.planning.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequest {
    private int age;
    private double monthlyIncome;
    private double monthlySavings;
    private double targetWealth;
    private int targetYears;
    private String riskLevel; // low, moderate, high
}
