package com.hqj.bigproject.mapper;

import com.hqj.bigproject.pojo.BpUser;
import com.hqj.bigproject.utils.MyMapper;
import java.util.List;

public interface BpUserMapper extends MyMapper<BpUser> {

    List<BpUser> findAll();
}