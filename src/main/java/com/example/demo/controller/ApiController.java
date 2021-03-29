package com.example.demo.controller;

import com.example.demo.dto.JSONResult;
import com.example.demo.dto.AccessDTO;
import com.example.demo.service.InfluxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/access")
public class ApiController {

    private final InfluxService influxService;

    @PostMapping
    public ResponseEntity<JSONResult> createAccess(AccessDTO dto) {
        influxService.write(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(dto,"Success"));
    }

    @GetMapping
    public ResponseEntity<JSONResult> getAccess(String filter) {
        List<AccessDTO> result = influxService.get(filter);
        return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(result,"Success"));
    }

}
