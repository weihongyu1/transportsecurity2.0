package com.why.transportsecurity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：AccidentInfo
 * @Description：todo 事故信息
 * @Author: why
 * @DateTime：2021/12/20 14:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccidentInfo {
    private Integer id;
    private long aDate;
    private double lng;
    private double lat;
    private Integer state;
    private Integer vId;
}
