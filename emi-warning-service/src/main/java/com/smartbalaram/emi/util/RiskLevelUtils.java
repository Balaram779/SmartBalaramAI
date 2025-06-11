package com.smartbalaram.emi.util;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.smartbalaram.emi.util.AppConstants.*;
/**
 * RiskLevelUtils: Calculates EMI risk using AI-style scoring (weighted formula)
 * - Inputs: EMI%, missed EMIs, loan tenure
 * - Config-driven weights & thresholds
 */


@Component
@Getter
public class RiskLevelUtils {
	

    // 🔧 AI Scoring Weights (loaded from application.yml)
    //@Value("${risk.weight-emi-percent}")
    //private double weightEmiPercent;

   // @Value("${risk.weight-missed-emi}")
   // private double weightMissedEmi;

   // @Value("${risk.weight-tenure}")
   // private double weightTenure;

  //  @Value("${risk.score-threshold-low}")
   // private double scoreThresholdLow;

   // @Value("${risk.score-threshold-medium}")
    //private double scoreThresholdMedium;

    
    // =======================
    // 🔧 Configurable Weights
    // =======================
    private static double weightEmiPercent;
    private static double weightMissedEmi;
    private static double weightTenure;

    // =========================
    // 🔧 Risk Score Thresholds
    // =========================
    private static double scoreThresholdLow;
    private static double scoreThresholdMedium;

    // ==============================
    // ✅ Public Configuration Setup
    // ==============================

    /**
     * Set weights for EMI %, Missed EMI Count, and Tenure.
     */
    public static void setWeights(double emiWeight, double missedEmiWeight, double tenureWeight) {
        weightEmiPercent = emiWeight;
        weightMissedEmi = missedEmiWeight;
        weightTenure = tenureWeight;
    }

    /**
     * Set thresholds for risk scoring (LOW and MEDIUM).
     */
    public static void setThresholds(double low, double medium) {
        scoreThresholdLow = low;
        scoreThresholdMedium = medium;
    }

    
    /**
     * 🔍 AI Risk Scoring Logic
     * Calculates weighted score and assigns risk level
     */
    public static String determineRiskLevel(double emiPercent, int missedEmis, int tenureMonths) {
        double score = (emiPercent * weightEmiPercent)
                     + (missedEmis * weightMissedEmi)
                     + (tenureMonths * weightTenure);

        if (score <= scoreThresholdLow) return RISK_LOW;
        else if (score <= scoreThresholdMedium) return RISK_MODERATE;
        else return RISK_HIGH;
    }

    /**
     * 📢 Return Warning Message based on Risk Level
     */
    public static String getWarningMessage(String riskLevel) {
        return switch (riskLevel) {
            case RISK_LOW      -> MESSAGE_SAFE;
            case RISK_MODERATE -> MESSAGE_CAUTION;
            case RISK_HIGH     -> MESSAGE_RISK;
            default            -> MESSAGE_INVALID;
        };
    }
 /*   public static void setWeights(double emiWeight, double missedEmiWeight, double tenureWeight) {
        weightEmiPercent = emiWeight;
        weightMissedEmi = missedEmiWeight;
        weightTenure = tenureWeight;
    }

    public static void setThresholds(double low, double medium) {
        scoreThresholdLow = low;
        scoreThresholdMedium = medium;
    }
*/
    public static double getScoreThresholdLow() {
        return scoreThresholdLow;
    }

    public static double getScoreThresholdMedium() {
        return scoreThresholdMedium;
    }

}