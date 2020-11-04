package com.f4w.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class JobException extends Exception {

    public JobException(String message) {
        super(message);
    }
}
