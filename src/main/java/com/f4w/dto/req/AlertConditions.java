package com.f4w.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertConditions {
    @JsonProperty("creator_user_id")
    private String creatorUserId;
    @JsonProperty("created_at")
    private Date createdAt;
    private String id;
    private String type;
    private String title;
    private Parameters parameters;
}
