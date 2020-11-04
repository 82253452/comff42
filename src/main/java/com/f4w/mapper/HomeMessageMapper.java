package com.f4w.mapper;

import com.f4w.dto.TransCompanyDto;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.dto.req.HomeMessagePageReq;
import com.f4w.entity.HomeMessage;
import com.f4w.entity.TransCompany;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 */
public interface HomeMessageMapper extends BaseMapper<HomeMessage> {

    @Select("<script>\n" +
            "select * from home_message where 1=1 \n" +
            " <if test='title!=null and title != &quot;&quot; '>\n" +
            "        and title like CONCAT('%',#{title},'%')\n" +
            "    </if>\n" +
            " <if test='subheading!=null and subheading != &quot;&quot; '>\n" +
            "        and subheading like CONCAT('%',#{subheading},'%')\n" +
            "    </if>\n" +
            "order by ctime desc \n" +
            "</script>")
    List<HomeMessage> getList(HomeMessagePageReq req);

}
