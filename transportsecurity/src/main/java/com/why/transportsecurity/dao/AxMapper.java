package com.why.transportsecurity.dao;

import com.why.transportsecurity.po.Ax;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName：AxMapper ax加速度mapper
 * @Description：
 * @Author: why
 * @Date：2021/12/20
 **/
@Mapper
public interface AxMapper {
    /**
     * 根据a_id查询加速度ax
     * @param aId
     * @return
     */
    public List<Double> getAx(Integer aId);

    /**
     * 插入ax加速度
     * @param ax
     */
    public void insertAx(Ax ax);

    /**
     * 删除ax
     * @param aId
     */
    public void deleteAx(Integer aId);
}
