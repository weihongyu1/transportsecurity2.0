package com.why.transportsecurity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：DetailInfo
 * @Description：todo 封装展示细节信息
 * @Author: why
 * @DateTime：2021/12/20 15:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailInfo {
    private String driverDegreeOfDamage;
    private String passengerDegreeOfDamage;
    private String date;
    private String time;
    private double lng;
    private double lat;
    private String address;
    private String direction;
    private String isBounce;
    private boolean state;
}
