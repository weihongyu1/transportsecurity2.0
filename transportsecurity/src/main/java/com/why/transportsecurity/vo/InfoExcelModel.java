package com.why.transportsecurity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName：InfoExcelModel
 * @Description：todo 数据下载实体类
 * @Author: why
 * @DateTime：2021/12/20 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoExcelModel {
    private String vName;

    private String vNumber;

    private String vType;

    private String driverDegreeOfDamage;

    private String passengerDegreeOfDamage;

    private String date;

    private String time;

    private double lng;

    private double lat;

    private String address;

    private String collisionDirection;

    private String isBounce;

    private List<Double> adRAcceleration;

    private List<Double> adCAcceleration;
}
