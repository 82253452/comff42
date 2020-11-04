package com.f4w.utils;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    private int page = 1;
    private int limit = 10;
}
