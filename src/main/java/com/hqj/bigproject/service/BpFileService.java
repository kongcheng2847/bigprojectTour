package com.hqj.bigproject.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqj.bigproject.mapper.BpFileMapper;
import com.hqj.bigproject.pojo.BpFile;
import com.hqj.bigproject.utils.UserJSONResult;
import com.hqj.bigproject.utils.UtilUUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BpFileService {

    @Resource
    private BpFileMapper bpFileMapper;

    @Transactional(propagation = Propagation.REQUIRED)//开启事务，不成功则回退
    public void insertBpFile(String fileName,String memo,String filePath,String userName,Integer fileSize){
        BpFile bpFile = new BpFile();
        bpFile.setFileId(UtilUUID.newShortUUID());
        bpFile.setCreateDate(new Date());
        bpFile.setFileName(fileName);
        bpFile.setMemo(memo);
        bpFile.setCreateUser(userName);
        bpFile.setDownloadDate(new Date());
        bpFile.setDownloadUser("");
        bpFile.setFileSize(fileSize);
        bpFile.setFilePath(filePath);
        bpFileMapper.insert(bpFile);
        UserJSONResult.ok(bpFile);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateBpFile(String fileId,String userName){
        BpFile bpFile = bpFileMapper.selectByPrimaryKey(fileId);
        bpFile.setDownloadUser(userName);
        bpFile.setDownloadDate(new Date());
        return bpFileMapper.updateByPrimaryKeySelective(bpFile);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public BpFile selectBpFile(String fileId){
        return bpFileMapper.selectByPrimaryKey(fileId);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteFile(String fileId){
        int num = bpFileMapper.deleteByPrimaryKey(fileId);
        return num;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public PageInfo<BpFile> queryFilsAll(BpFile bpFile){
        PageHelper.startPage(1, 3);
        return new PageInfo<BpFile>(bpFileMapper.selectAll());
    }
}
