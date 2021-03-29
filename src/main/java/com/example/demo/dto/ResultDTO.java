package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDTO {

    private long total;

    private long android;

    private long ios;

    private String op;
}
