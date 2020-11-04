package com.f4w.mapper;

import com.f4w.dto.TransCompanyUserDto;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.dto.req.TransPageReq;
import com.f4w.entity.TransCompany;
import com.f4w.entity.TransCompanyUser;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 */
public interface TransCompanyUserMapper extends BaseMapper<TransCompanyUser> {

    @Select("select d.*, su.*\n" +
            "from driver d\n" +
            "         left join sys_user su on d.user_id = su.id")
    List<TransCompanyUserDto> getList(CommonPageReq req);

    @Select("<script>\n" +
            "select d.*,nickName,avatarUrl, tc.name transName\n" +
            "from trans_company_user tcu\n" +
            "         left join driver d on tcu.driver_id = d.id\n" +
            "         left join sys_user su on tcu.user_id = su.id\n" +
            "         left join trans_company tc on d.trans_id = tc.id\n" +
            "    where 1=1\n" +
            "    <if test='transId!=null and transId != &quot;&quot; '>\n" +
            "        and tcu.trans_id = #{transId}\n" +
            "    </if>\n" +
            "    <if test='status!=null and status != &quot;&quot; '>\n" +
            "        and d.status = #{status}\n" +
            "    </if>\n" +
            "    <if test='driverStatus!=null and driverStatus != &quot;&quot; '>\n" +
            "        and d.driver_status = #{driverStatus}\n" +
            "    </if>\n" +
            "    <if test='name!=null and name != &quot;&quot; '>\n" +
            "        and d.name like CONCAT('%',#{name},'%')\n" +
            "    </if>\n" +
            "    and d.delete = 0 \n" +
            "    " +
            "order by d.mtime desc \n" +
            "</script>")
    List<TransCompanyUserDto> getAdminList(TransPageReq req);

    @Select("select tc.*\n" +
            "from trans_company_user tcu\n" +
            "         left join trans_company tc on tcu.trans_id = tc.id\n" +
            "where tcu.user_id = #{userId}")
    List<TransCompany> getUserCompanyList(@Param("userId") Integer userId);
}
