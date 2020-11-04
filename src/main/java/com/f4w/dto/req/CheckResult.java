package com.f4w.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckResult {
    @JsonProperty("result_description")
    private String resultDescription;
    @JsonProperty("triggered_condition")
    private TriggeredCondition triggeredCondition;
    @JsonProperty("triggered_at")
    private Date triggeredAt;
    private boolean triggered;
    @JsonProperty("matching_messages")
    private List<String> matchingMessages;
}
