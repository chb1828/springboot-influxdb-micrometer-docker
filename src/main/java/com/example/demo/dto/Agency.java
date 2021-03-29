package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Agency {

    SKT("45005","SKT"),KT("45008","KT"),LG("45006","LG"),ETC("","ETC");

    private final String key;
    private final String value;

}
