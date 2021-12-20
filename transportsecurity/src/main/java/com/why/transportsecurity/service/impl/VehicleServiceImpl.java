package com.why.transportsecurity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.why.transportsecurity.dao.AccidentMapper;
import com.why.transportsecurity.dao.AxMapper;
import com.why.transportsecurity.dao.AyMapper;
import com.why.transportsecurity.dao.VehicleMapper;
import com.why.transportsecurity.po.AccidentInfo;
import com.why.transportsecurity.po.VehicleInfo;
import com.why.transportsecurity.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName：VehicleServiceImpl
 * @Description：todo 车辆信息管理实现类
 * @Author: why
 * @DateTime：2021/12/20 15:15
 */
@Service
@Slf4j
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    VehicleMapper vehicleInfoMapper;

    @Override
    public boolean insertVehicle(VehicleInfo vehicleInfo) {
        if (vehicleInfo == null){
            log.warn("添加车辆信息，输入非法");
            return false;
        }
        List<VehicleInfo> vehicleByVNum = vehicleInfoMapper.getVehicleByVNum(vehicleInfo.getVNum());
        Iterator<VehicleInfo> iterator = vehicleByVNum.iterator();
        if (vehicleByVNum.size() > 0){
            log.warn("车辆已存在");
            return false;
        }
        vehicleInfoMapper.insertVehicle(vehicleInfo);
        return true;
    }

    @Autowired
    AxMapper axMapper;
    @Autowired
    AyMapper ayMapper;
    @Autowired
    AccidentMapper accidentInfoMapper;

    @Override
    public boolean deleteVehicle(Integer id) {
        if (id == null){
            return false;
        }
        //查找事故aId
        List<AccidentInfo> accidentInfo = accidentInfoMapper.getAccidentInfoByVId(id);
        if (accidentInfo.size() >= 0){
            Iterator<AccidentInfo> iterator = accidentInfo.iterator();
            while (iterator.hasNext()) {
                AccidentInfo next =  iterator.next();
                if (next != null){
                    Integer aId = next.getId();
                    //删除ax，ay
                    axMapper.deleteAx(aId);
                    ayMapper.deleteAy(aId);
                    //删除事故信息
                    accidentInfoMapper.deleteAccident(id);
                }
            }
        }
        //删除车辆信息
        log.warn("删除车辆信息");
        vehicleInfoMapper.deleteVehicle(id);
        return true;
    }

    @Override
    public Map<Integer,List<VehicleInfo>> getAll(Integer pageNum) {
        Map<Integer,List<VehicleInfo>> map = new HashMap<>();
        Page<Object> page = PageHelper.startPage(pageNum, 10);
        List<VehicleInfo> vehicles = vehicleInfoMapper.getAll();
        Integer count = vehicleInfoMapper.getCount();
        map.put(count,vehicles);
        return map;
    }

    @Override
    public boolean updateVehicle(VehicleInfo vehicleInfo) {
        if (vehicleInfo == null){
            return false;
        }
        vehicleInfoMapper.updateVehicle(vehicleInfo);
        return true;
    }

    @Override
    public List<VehicleInfo> search(String search) {
        if (search == null) {
            return null;
        }
        List<VehicleInfo> vehicles = null;
        //分析search是车牌号还是车主姓名
        //车主姓名
        if (search.contains("-"))//车牌号
            vehicles= vehicleInfoMapper.getVehicleByVNum(search);
        else
            vehicles = vehicleInfoMapper.getVehicleByVName(search);
        if (vehicles.size() <= 0){
            return null;
        }
        return vehicles;
    }
}
