package com.f4w.service;

import com.f4w.dto.enums.UserTypeEnum;
import com.f4w.entity.HomeMessage;
import com.f4w.entity.SysUser;
import com.f4w.entity.TransCompany;
import com.f4w.entity.TransCompanyUser;
import com.f4w.mapper.HomeMessageMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.mapper.TransCompanyMapper;
import com.f4w.utils.ShowException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;
import java.util.Optional;

/**
 * @author admin
 */
@Service
public class HomeMessageService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private HomeMessageMapper homeMessageMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addMessage(HomeMessage homeMessage) {
    	HomeMessage build = HomeMessage.builder()
                .title(homeMessage.getTitle())
                .subheading(homeMessage.getSubheading())
                .content(homeMessage.getContent())
                .build();
        homeMessageMapper.insertSelective(build);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMessage(Integer id) {
        Optional<HomeMessage> homeMessage = Optional.ofNullable(homeMessageMapper.selectByPrimaryKey(id));
        homeMessage.ifPresent(e -> {
        	homeMessageMapper.deleteByPrimaryKey(id);
        });
    }

}
