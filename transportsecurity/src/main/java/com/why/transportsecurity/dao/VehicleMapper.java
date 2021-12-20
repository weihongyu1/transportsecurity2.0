package com.why.transportsecurity.dao;

import com.why.transportsecurity.po.VehicleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName：VehicleMapper 车辆基本信息mapper
 * @Description：
 * @Author: why
 * @Date：2021/12/20
 **/
@Mapper
public interface VehicleMapper {
    /**
     * 根据车辆id查询车辆信息
     * @param id
     * @return
     */
    public VehicleInfo getVehicle(Integer id);

    /**
     * 根据车牌号查询车辆信息
     * @param vNum
     * @return
     */
    public List<VehicleInfo> getVehicleByVNum(String vNum);

    /**
     * 根据车牌号查出车辆id
     * @param vNum
     * @return
     */
    public Integer getVehicleId(String vNum);

    /**
     * 获取所有车辆信息
     * @return
     */
    public List<VehicleInfo> getAll();

    /**
     * 添加司机信息
     * @param vehicleInfo
     */
    public void insertVehicle(VehicleInfo vehicleInfo);

    /**
     * 根据id删除车辆信息
     * @param id
     */
    public void deleteVehicle(Integer id);

    /**
     * 修改
     * @param vehicleInfo
     */
    public void updateVehicle(VehicleInfo vehicleInfo);


    /**
     * 根据车主姓名检索
     * @param vName
     * @return
     */
    public List<VehicleInfo> getVehicleByVName(String vName);

    /**
     * 返回总记录数
     * @return
     */
    public Integer getCount();
}
