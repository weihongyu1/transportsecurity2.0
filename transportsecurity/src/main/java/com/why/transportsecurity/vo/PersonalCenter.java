package com.why.transportsecurity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：PersonalCenter
 * @Description：todo 个人中心实体
 * @Author: why
 * @DateTime：2021/12/12 17:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCenter {
    /**
     * 姓名
     */
    private String username;
    /**
     * 电话
     */
    private String uPhone;
    /**
     * 邮箱
     */
    private String uEmail;
    /**
     * 住址
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
     * 用户信息uId
     */
    private Integer uId;
}
