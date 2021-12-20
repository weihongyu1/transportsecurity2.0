package com.why.transportsecurity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：Admininfo
 * @Description：todo 管理员信息实体类
 * @Author: why
 * @DateTime：2021/12/12 15:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminInfo {
    /**
     * id
     */
    private Integer id;
    /**
     * 电话号
     */
    private String uPhone;
    /**
     * 邮箱号
     */
    private String uEmail;
    /**
     * 地址
     */
    private String uAddress;
    /**
     * 出生日期
     */
    private String uBirth;
    /**
     * 入职日期
     */
    private String uDate;
    /**
     * 外键uId
     */
    private Integer uId;

    public AdminInfo(String uPhone, Integer uId) {
        this.uPhone = uPhone;
        this.uId = uId;
    }
}
