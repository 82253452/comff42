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
public class Stream {
    @JsonProperty("creator_user_id")
    private String creatorUserId;
    private List<String> outputs;
    private String description;
    @JsonProperty("created_at")
    private Date createdAt;
    private List<String> rules;
    @JsonProperty("alert_conditions")
    private List<AlertConditions> alertConditions;
    private String title;
    @JsonProperty("is_default_stream")
    private boolean isDefaultStream;
    @JsonProperty("index_set_id")
    private String indexSetId;
    @JsonProperty("matching_type")
    private String matchingType;
    @JsonProperty("remove_matches_from_default_stream")
    private boolean removeMatchesFromDefaultStream;
    private boolean disabled;
    private String id;
}
