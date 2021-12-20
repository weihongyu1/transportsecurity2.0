package com.why.transportsecurity.dao;

import com.why.transportsecurity.po.AccidentInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName：AccidentMapper
 * @Description：todo 事故信息mapper
 * @Author: why
 * @DateTime：2021/12/20 14:35
 */
@Mapper
public interface AccidentMapper {
    /**
     * 根据车辆事故信息id查询事故信息
     * @param id 事故信息id
     * @return
     */
    public AccidentInfo getAccidentInfoById(Integer id);

    /**
     * 根据车辆基本信息id，查询车辆事故信息
     * @param vId 车辆基本信息id
     * @return
     */
    public List<AccidentInfo> getAccidentInfoByVId(Integer vId);

    /**
     * 获得所有已处理数据
     * @return
     */
    public List<AccidentInfo> getAllAccidentInfoSolve();

    /**
     * 获得所有未处理数据
     * @return
     */
    public List<AccidentInfo> getAllAccidentInfoUnSolve();

    /**
     * 更新处理状态
     * @param aID
     */
    public void updateState(Integer aID);

    /**
     * 删除事故信息
     * @param vId
     */
    public void deleteAccident(Integer vId);

    /**
     * 添加事故数据
     * @param accidentInfo
     */
    public void insertAccident(AccidentInfo accidentInfo);
}
