package com.petproject.common.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }

}
