package com.f4w.mapper;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Company;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 */
public interface CompanyMapper extends BaseMapper<Company> {

    @Select("<script>\n" +
            "select * from company where 1=1 \n" +
            "    " +
            "<if test='name!=null and name != &quot;&quot; '>\n" +
            "        AND nickname like CONCAT('%',#{name},'%')\n" +
            "    </if>\n" +
            "and `delete` = 0\n" +
            " order by mtime desc \n" +
            "</script>")
    List<Company> getList(CommonPageReq req);
}
