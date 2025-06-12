package com.smartbalaram.emi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartbalaram.emi.model.EmiRequest;
import com.smartbalaram.emi.model.EmiResponse;
import com.smartbalaram.emi.service.EmiWarningService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(EmiWarningController.class)
class EmiWarningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmiWarningService emiWarningService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateEmiWarning() throws Exception {
    	EmiRequest request = EmiRequest.builder()
    		    .monthlyIncome(30000.0)
    		    .totalEmiAmount(18000.0)
    		    .userId("Bala")
    		    .warning(true)
    		    .build();


        EmiResponse response = EmiResponse.builder()
                .emiPercentage(60.0)
                .riskLevel("HIGH")
                .warningMessage("Warning: EMI exceeds 50% of income")
                .suggestedMaxEmi(15000.0)
                .build();

        Mockito.when(emiWarningService.evaluateEmi(request)).thenReturn(response);

        mockMvc.perform(post("/api/emi/warn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel").value("HIGH"))
                .andExpect(jsonPath("$.emiPercentage").value(60.0))
                .andExpect(jsonPath("$.suggestedMaxEmi").value(15000.0));
    }

    @Test
    void testGetRecommendedEmiCap() throws Exception {
        Mockito.when(emiWarningService.getRecommendedCap(40000.0)).thenReturn(20000.0);

        mockMvc.perform(get("/api/emi/cap/40000.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("20000.0"));
    }

    @Test
    void testGetRiskLevel() throws Exception {
        Mockito.when(emiWarningService.getRiskLevel(70.0)).thenReturn("HIGH");

        mockMvc.perform(get("/api/emi/risk/70.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("HIGH"));
    }

    @Test
    void testGetThresholdDefinitions() throws Exception {
        List<String> thresholds = List.of("LOW: <30%", "MEDIUM: 30–50%", "HIGH: >50%");
        Mockito.when(emiWarningService.getThresholdDetails()).thenReturn(thresholds);

        mockMvc.perform(get("/api/emi/thresholds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]").value("LOW: <30%"));
    }

    @Test
    void testPing() throws Exception {
        mockMvc.perform(get("/api/emi/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("✅ EMI service is up!"));
    }
}
