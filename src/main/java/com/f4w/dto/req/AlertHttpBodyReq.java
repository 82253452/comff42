package com.f4w.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertHttpBodyReq {
    @JsonProperty("event_definition_id")
    private String eventDefinitionId;
    @JsonProperty("event_definition_type")
    private String eventDefinitionType;
    @JsonProperty("event_definition_title")
    private String eventDefinitionTitle;
    @JsonProperty("event_definition_description")
    private String eventDefinitionDescription;
    @JsonProperty("job_definition_id")
    private String jobDefinitionId;
    @JsonProperty("job_trigger_id")
    private String jobTriggerId;
    private Event event;
    private List<String> backlog;
}


