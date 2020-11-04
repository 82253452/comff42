package com.f4w.dto.req;

import com.f4w.dto.annotation.InjectTransId;
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
public class TransPageReq extends CommonPageReq {
    private Integer driverStatus;
}
