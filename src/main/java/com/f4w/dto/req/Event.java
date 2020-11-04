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
public class Event {
    private String id;
    @JsonProperty("event_definition_type")
    private String eventDefinitionType;
    @JsonProperty("event_definition_id")
    private String eventDefinitionId;
    @JsonProperty("origin_context")
    private String originContext;
    private Date timestamp;
    @JsonProperty("timestamp_processing")
    private Date timestampProcessing;
    @JsonProperty("timerange_start")
    private String timerangeStart;
    @JsonProperty("timerange_end")
    private String timerangeEnd;
    private List<String> streams;
    @JsonProperty("source_streams")
    private List<String> sourceStreams;
    private String message;
    private String source;
    @JsonProperty("key_tuple")
    private List<String> keyTuple;
    private String key;
    private int priority;
    private boolean alert;
    private Fields fields;
}
