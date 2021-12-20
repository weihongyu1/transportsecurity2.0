package com.why.transportsecurity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：TotalInfo
 * @Description：todo 封装统计事故信息
 * @Author: why
 * @DateTime：2021/12/20 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalInfo {
    private Integer vId;
    private Integer aId;
    private String vNum;
    private String vName;
    private String vType;
    private String date;
    private boolean status;
}
