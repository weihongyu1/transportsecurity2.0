package com.why.transportsecurity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：Ax
 * @Description：todo ax横向加速度
 * @Author: why
 * @DateTime：2021/12/20 14:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ax {
    private Integer id;
    private double accelerationX;
    private Integer aId;
}
