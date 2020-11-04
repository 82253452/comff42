package com.f4w.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class JobInfoReq {
    @NotBlank
    private String appId;
    @NotNull
    private Integer column;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Date time;
    @NotBlank
    private String types;
    private Boolean comment;
    private Boolean isPush;
    private Integer topNum;
    private String contentSourceUrl;
    private String miniAppId;
    private String miniAppPath;
}
