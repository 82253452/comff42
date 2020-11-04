package com.f4w.dto.req;

import com.f4w.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author admin
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserReq extends SysUser {
    private Integer roleId;
}
