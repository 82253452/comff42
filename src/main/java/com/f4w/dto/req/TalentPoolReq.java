package com.f4w.dto.req;

import com.f4w.utils.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TalentPoolReq extends Page {
    private String phone;
    private String sc;
    private String edu;
    private String major;
}
