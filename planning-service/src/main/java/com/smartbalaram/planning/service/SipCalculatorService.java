package com.smartbalaram.planning.service;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.smartbalaram.planning.model.PlanRequest;
import com.smartbalaram.planning.model.PlanResponse;
import com.smartbalaram.planning.repository.PlanRepository;
import com.smartbalaram.planning.util.TestConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SipCalculatorService {

    private final PlanRepository repository;
    private final KafkaTemplate<String, PlanResponse> kafkaTemplate;

    public SipCalculatorService(PlanRepository repository, KafkaTemplate<String, PlanResponse> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public PlanResponse calculatePlan(PlanRequest request) {
        double cagr;
        switch (request.getRiskLevel().toLowerCase()) {
            case TestConstant.RISK_LOW: cagr = 8; break;
            case TestConstant.RISK_MODERATE: cagr = 12; break;
            case TestConstant.RISK_HIGH: cagr = 15; break;
            default: cagr = 10;
        }

        double r = cagr / 100 / 12;
        int n = request.getTargetYears() * 12;
        double fv = request.getTargetWealth();

        double sip = (fv * r) / (Math.pow(1 + r, n) - 1);

        String projection = String.format(TestConstant.PROJECTION_FORMAT, fv, request.getTargetYears());

        String equity = request.getRiskLevel().equalsIgnoreCase(TestConstant.RISK_HIGH) ? "80%" :
                        request.getRiskLevel().equalsIgnoreCase(TestConstant.RISK_MODERATE) ? "60%" : "40%";
        String debt = request.getRiskLevel().equalsIgnoreCase(TestConstant.RISK_HIGH) ? "15%" :
                      request.getRiskLevel().equalsIgnoreCase(TestConstant.RISK_MODERATE) ? "30%" : "50%";
        String gold = request.getRiskLevel().equalsIgnoreCase(TestConstant.RISK_HIGH) ? "5%" : "10%";

        PlanResponse response = new PlanResponse(
                UUID.randomUUID().toString(),
                request.getAge(),
                request.getMonthlyIncome(),
                request.getMonthlySavings(),
                request.getTargetWealth(),
                request.getTargetYears(),
                request.getRiskLevel(),
                cagr,
                Math.round(sip),
                projection,
                equity,
                debt,
                gold
        );

        repository.save(response);
        kafkaTemplate.send("plan-created", response);

        log.info(TestConstant.LOG_MONGO_SAVE, response.getId());
        log.info(TestConstant.LOG_KAFKA_SENT, response.getAge());

        return response;
    }
}
