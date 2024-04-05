package com.webcurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityErrorResponse {
    private Integer errorCode;
    private String message;
}

