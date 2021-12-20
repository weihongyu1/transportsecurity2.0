package com.why.transportsecurity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：VehicleInfo
 * @Description：todo 车辆信息
 * @Author: why
 * @DateTime：2021/12/20 14:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfo {
    private Integer id;
    private String vName;
    private String vNum;
    private String vType;
}
