package com.example.authjwt.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDto {

    private Integer httpCode;

    private String message;

    private List<String> errors;

}
