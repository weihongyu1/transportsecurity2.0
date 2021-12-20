package com.why.transportsecurity.service;

import com.why.transportsecurity.po.VehicleInfo;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName：VehicleService
 * @Description：车辆信息管理
 * @Author: why
 * @Date：2021/12/20
 **/
public interface VehicleService {
    /**
     * 添加车辆信息
     * @param vehicleInfo
     */
    public boolean insertVehicle(VehicleInfo vehicleInfo);

    /**
     * 删除车辆信息
     * @param id
     * @return
     */
    public boolean deleteVehicle(Integer id);

    /**
     * 获取所有车辆信息
     * @return
     */
    public Map<Integer, List<VehicleInfo>> getAll(Integer pageNum);

    /**
     * 修改车辆信息
     * @param vehicleInfo
     * @return
     */
    public boolean updateVehicle(VehicleInfo vehicleInfo);

    /**
     * 搜索
     * @param search
     * @return
     */
    public List<VehicleInfo> search(String search);
}
