package com.why.transportsecurity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：Ay
 * @Description：todo ay加速度信息
 * @Author: why
 * @DateTime：2021/12/20 14:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ay {
    private Integer id;
    private double accelerationY;
    private Integer aId;
}
