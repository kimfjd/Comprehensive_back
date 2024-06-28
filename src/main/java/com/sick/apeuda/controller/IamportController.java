package com.sick.apeuda.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.sick.apeuda.service.IamportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/api/iamport")
public class IamportController {

    @Autowired
    private IamportService iamportService;

    @PostMapping("/getToken")
    public ResponseEntity<String> getToken() {
        try {
            String token = iamportService.getToken();
            return ResponseEntity.ok("{\"response\": {\"access_token\": \"" + token + "\"}}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/schedulePayment")
    public ResponseEntity<JsonNode> schedulePayment(@RequestBody JsonNode scheduleData) {
        try {
            String token = iamportService.getToken();
            JsonNode response = iamportService.schedulePayment(token, scheduleData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
