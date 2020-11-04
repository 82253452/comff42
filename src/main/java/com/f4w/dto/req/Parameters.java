package com.f4w.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parameters {
    private int grace;
    private int backlog;
    @JsonProperty("repeat_notifications")
    private boolean repeatNotifications;
    private String field;
    private String value;
}
