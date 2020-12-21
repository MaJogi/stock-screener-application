package com.taltech.stockscreenerapplication.util.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {
    private String message;

    public MessageResponse(final String message) {
        this.message = message;
    }
}
