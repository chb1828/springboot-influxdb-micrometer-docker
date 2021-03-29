package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessDTO {

    private String os;

    private String op;

    private String time;

}
