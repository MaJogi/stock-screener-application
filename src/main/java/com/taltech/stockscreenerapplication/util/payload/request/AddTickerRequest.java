package com.taltech.stockscreenerapplication.util.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AddTickerRequest {
    @NotBlank
    private String tickerId;
}
