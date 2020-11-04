package com.f4w.service;

import com.f4w.dto.enums.UserTypeEnum;
import com.f4w.entity.SysUser;
import com.f4w.entity.TransCompany;
import com.f4w.entity.TransCompanyUser;
import com.f4w.mapper.SysUserMapper;
import com.f4w.mapper.TransCompanyMapper;
import com.f4w.utils.ShowException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author admin
 */
@Service
public class TransCompanyService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private TransCompanyMapper transCompanyMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addCompany(TransCompany transCompany) {
        SysUser build = SysUser.builder()
                .userName(transCompany.getName())
                .nickname(transCompany.getName())
                .password(DigestUtils.md5Hex(transCompany.getContactsPhone()))
                .phone(transCompany.getContactsPhone())
                .type(UserTypeEnum.TRANS_COMPANY.getCode())
                .build();
        sysUserMapper.insertSelective(build);
        transCompany.setUserId(build.getId().intValue());
        transCompanyMapper.insertSelective(transCompany);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCompany(Integer id) {
        Optional<TransCompany> transCompany = Optional.ofNullable(transCompanyMapper.selectByPrimaryKey(id));
        transCompany.ifPresent(e -> {
            transCompanyMapper.deleteByPrimaryKey(id);
            sysUserMapper.deleteByPrimaryKey(e.getUserId());
        });
    }

}
