package com.project.springsecurity.entities.dtos;

import lombok.Data;

@Data
public class ResponseDto {
    private String response;

    public ResponseDto() {
    }

    public ResponseDto(String response) {
        this.response = response;
    }
}
