package com.smartbalaram.planning.service;

import com.smartbalaram.planning.model.PlanRequest;
import com.smartbalaram.planning.model.PlanResponse;
import com.smartbalaram.planning.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SipCalculatorServiceTest {

    @Test
    void testCalculatePlanModerateRisk() {
        PlanRepository mockRepo = Mockito.mock(PlanRepository.class);
        KafkaTemplate<String, PlanResponse> mockKafka = Mockito.mock(KafkaTemplate.class);
        SipCalculatorService service = new SipCalculatorService(mockRepo, mockKafka);

        PlanRequest request = new PlanRequest(30, 60000, 10000, 10000000, 20, "moderate");
        PlanResponse response = service.calculatePlan(request);

        assertEquals(30, response.getAge());
        assertEquals(60000, response.getMonthlyIncome());
        assertEquals(10000, response.getMonthlySavings());
        assertEquals(10000000, response.getTargetWealth());
        assertEquals(20, response.getTargetYears());
        assertEquals("moderate", response.getRiskLevel());
        assertEquals(12, response.getEstimatedCAGR());
        assertEquals("60%", response.getEquityPercent());
        assertEquals("30%", response.getDebtPercent());
        assertEquals("10%", response.getGoldPercent());
    }
}
