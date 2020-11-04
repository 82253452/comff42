package com.f4w.dto.req;

import com.f4w.dto.annotation.InjectCompanyId;
import com.f4w.dto.annotation.InjectTransId;
import com.f4w.dto.annotation.InjectUserId;
import com.f4w.utils.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * @author yp
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommonPageReq extends Page {
    private Integer status;
    private String appId;
    private String name;
    private String title;
    private Integer type;
    private Integer id;
    @InjectUserId
    private Integer userId;
    @InjectTransId
    private Integer transId;
    @InjectCompanyId
    private Integer companyId;
}
