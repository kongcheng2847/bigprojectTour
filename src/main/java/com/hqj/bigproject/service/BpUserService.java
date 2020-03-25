package com.hqj.bigproject.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqj.bigproject.mapper.BpUserMapper;
import com.hqj.bigproject.pojo.BpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class BpUserService {
    private static final Logger LOG = LoggerFactory.getLogger(BpUserService.class);
    @Resource
    private BpUserMapper bpUserMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public PageInfo<BpUser> findAll(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<BpUser>(bpUserMapper.findAll());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertUser(BpUser bpUser){
        return bpUserMapper.insert(bpUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteUser(String userId) {
        return bpUserMapper.deleteByPrimaryKey(userId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public BpUser selectBpUser(BpUser bpUser){
        return bpUserMapper.selectOne(bpUser);
    }
}
