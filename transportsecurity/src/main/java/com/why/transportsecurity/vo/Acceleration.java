package com.why.transportsecurity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：Acceleration
 * @Description：todo 封装加速度信息
 * @Author: why
 * @DateTime：2021/12/20 15:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acceleration {
    private double ax;
    private double ay;
}
