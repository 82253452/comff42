package com.f4w.mapper;

import com.f4w.dto.TransCompanyDto;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.TransCompany;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 */
public interface TransCompanyMapper extends BaseMapper<TransCompany> {

    @Select("<script>\n" +
            "select * from trans_company where 1=1 \n" +
            " <if test='name!=null and name != &quot;&quot; '>\n" +
            "        and name like CONCAT('%',#{name},'%')\n" +
            "    </if>\n" +
            "and `delete` = 0 \n" +
            "order by mtime desc \n" +
            "</script>")
    List<TransCompany> getList(CommonPageReq req);

    @Select("select tcu.status,tc.*\n" +
            "from trans_company tc\n" +
            "         left join (select tcu.trans_id, tcu.status\n" +
            "                    from trans_company_user tcu\n" +
            "                    where tcu.user_id = #{userId}) tcu on tc.id = tcu.trans_id\n" +
            "where tc.`delete` = 0\n" +
            "order by tc.mtime desc")
    List<TransCompanyDto> getUserList(CommonPageReq req);
}
