package com.hqj.bigproject.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "bp_user")
public class BpUser {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    @Column(name = "pass_word")
    private String passWord;

    /**
     * 身份证号
     */
    @Column(name = "id_crad")
    private String idCrad;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 地址
     */
    private String address;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 邮箱
     */
    @Column(name = "e_mail")
    private String eMail;

    /**
     * 所在地
     */
    private String country;

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取用户密码
     *
     * @return pass_word - 用户密码
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * 设置用户密码
     *
     * @param passWord 用户密码
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    /**
     * 获取身份证号
     *
     * @return id_crad - 身份证号
     */
    public String getIdCrad() {
        return idCrad;
    }

    /**
     * 设置身份证号
     *
     * @param idCrad 身份证号
     */
    public void setIdCrad(String idCrad) {
        this.idCrad = idCrad == null ? null : idCrad.trim();
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取邮箱
     *
     * @return e_mail - 邮箱
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * 设置邮箱
     *
     * @param eMail 邮箱
     */
    public void seteMail(String eMail) {
        this.eMail = eMail == null ? null : eMail.trim();
    }

    /**
     * 获取所在地
     *
     * @return country - 所在地
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置所在地
     *
     * @param country 所在地
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BpUser{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", passWord='").append(passWord).append('\'');
        sb.append(", idCrad='").append(idCrad).append('\'');
        sb.append(", age=").append(age);
        sb.append(", address='").append(address).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", birthday=").append(birthday);
        sb.append(", eMail='").append(eMail).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append('}');
        return sb.toString();
    }
}