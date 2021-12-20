package com.why.transportsecurity.dao;

import com.why.transportsecurity.po.Ay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName：AyMapper ay加速度mapper
 * @Description：
 * @Author: why
 * @Date：2021/12/20
 **/
@Mapper
public interface AyMapper {
    /**
     * 根据aID查询ay
     * @param aID
     * @return
     */
    public List<Double> getAy(Integer aID);

    /**
     * 插入ay
     * @param ay
     */
    public void insertAy(Ay ay);

    /**
     * 删除ax
     * @param aId
     */
    public void deleteAy(Integer aId);
}
