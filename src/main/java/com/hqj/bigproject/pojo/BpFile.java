package com.hqj.bigproject.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "bp_file")
public class BpFile {
    /**
     * 文件ID
     */
    @Id
    @Column(name = "file_id")
    private String fileId;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    private Integer fileSize;

    /**
     * 问价类型
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 备注
     */
    private String memo;

    /**
     * 上传时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 上传人
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 下载时间
     */
    @Column(name = "download_date")
    private Date downloadDate;

    /**
     * 下载人
     */
    @Column(name = "download_user")
    private String downloadUser;

    /**
     * 获取文件ID
     *
     * @return file_id - 文件ID
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置文件ID
     *
     * @param fileId 文件ID
     */
    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    /**
     * 获取文件名称
     *
     * @return file_name - 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     *
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 获取文件路径
     *
     * @return file_path - 文件路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件路径
     *
     * @param filePath 文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    /**
     * 获取文件大小
     *
     * @return file_size - 文件大小
     */
    public Integer getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小
     *
     * @param fileSize 文件大小
     */
    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取问价类型
     *
     * @return file_type - 问价类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置问价类型
     *
     * @param fileType 问价类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    /**
     * 获取备注
     *
     * @return memo - 备注
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置备注
     *
     * @param memo 备注
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * 获取上传时间
     *
     * @return create_date - 上传时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置上传时间
     *
     * @param createDate 上传时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取上传人
     *
     * @return create_user - 上传人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置上传人
     *
     * @param createUser 上传人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 获取下载时间
     *
     * @return download_date - 下载时间
     */
    public Date getDownloadDate() {
        return downloadDate;
    }

    /**
     * 设置下载时间
     *
     * @param downloadDate 下载时间
     */
    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }

    /**
     * 获取下载人
     *
     * @return download_user - 下载人
     */
    public String getDownloadUser() {
        return downloadUser;
    }

    /**
     * 设置下载人
     *
     * @param downloadUser 下载人
     */
    public void setDownloadUser(String downloadUser) {
        this.downloadUser = downloadUser == null ? null : downloadUser.trim();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BpFile{");
        sb.append("fileId='").append(fileId).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", filePath='").append(filePath).append('\'');
        sb.append(", fileSize=").append(fileSize);
        sb.append(", fileType='").append(fileType).append('\'');
        sb.append(", memo='").append(memo).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser='").append(createUser).append('\'');
        sb.append(", downloadDate=").append(downloadDate);
        sb.append(", downloadUser='").append(downloadUser).append('\'');
        sb.append('}');
        return sb.toString();
    }
}