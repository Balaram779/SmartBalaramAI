package com.smartbalaram.planning.controller;

import com.smartbalaram.planning.model.PlanRequest;
import com.smartbalaram.planning.model.PlanResponse;
import com.smartbalaram.planning.service.SipCalculatorService;
import com.smartbalaram.planning.util.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/sip-plan")
public class SipPlanController {

    private final SipCalculatorService service;

    public SipPlanController(SipCalculatorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PlanResponse> calculate(@RequestBody PlanRequest request) {
        log.info(TestConstant.LOG_REQUEST_RECEIVED, request.getTargetWealth(), request.getTargetYears());
        PlanResponse response = service.calculatePlan(request);
        log.info(TestConstant.LOG_RESPONSE_READY, response.getAge(), response.getEstimatedCAGR());
        return ResponseEntity.ok(response);
    }
}
