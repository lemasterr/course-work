package ua.opnu.labwork2.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class ActuatorController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("service", "labwork-2");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/metrics")
    public ResponseEntity<String> metrics() {
        return ResponseEntity.ok("Basic custom metrics endpoint is available");
    }

    @GetMapping("/prometheus")
    public ResponseEntity<String> prometheus() {
        return ResponseEntity.ok("Prometheus endpoint placeholder");
    }
}
