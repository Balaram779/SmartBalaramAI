package com.smartbalaram.planning.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "plans")
public class PlanResponse {
    @Id
    private String id;
    private int age;
    private double monthlyIncome;
    private double monthlySavings;
    private double targetWealth;
    private int targetYears;
    private String riskLevel;
    private double estimatedCAGR;
    private double requiredMonthlyInvestment;
    private String wealthProjection;
    private String equityPercent;
    private String debtPercent;
    private String goldPercent;
}