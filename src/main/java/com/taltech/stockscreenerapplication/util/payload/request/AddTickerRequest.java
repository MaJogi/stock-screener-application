package com.taltech.stockscreenerapplication.util.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class AddTickerRequest {
    @NotBlank
    private String tickerId;

    public AddTickerRequest() {
        super();
    }
}
