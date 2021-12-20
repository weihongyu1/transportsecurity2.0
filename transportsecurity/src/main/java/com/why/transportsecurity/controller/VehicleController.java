package com.why.transportsecurity.controller;

import com.why.transportsecurity.po.AdminLogin;
import com.why.transportsecurity.po.VehicleInfo;
import com.why.transportsecurity.service.impl.VehicleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @ClassName：VehicleController
 * @Description：todo 车辆信息控制器
 * @Author: why
 * @DateTime：2021/12/20 15:24
 */
@Controller
@Slf4j
@RequestMapping("/ts")
@Api("车辆信息管理")
public class VehicleController {
    @Autowired
    VehicleServiceImpl vehicleService;

    /**
     * 展示所有车辆信息
     * @param model
     * @return
     */
    @ApiOperation("车辆信息统计")
    @RequestMapping("/vehicle/vehicles")
    public String vehicles(Model model, @PathParam("pageNum") Integer pageNum){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminLogin userDetails = (AdminLogin) authentication.getPrincipal();
        model.addAttribute("nameLogin",userDetails.getUsername());
        Map<Integer, List<VehicleInfo>> serviceAll = vehicleService.getAll(pageNum);
        Integer count = null;
        List<VehicleInfo> vehicles = null;
        for (Map.Entry entry : serviceAll.entrySet()) {
            count = (Integer) entry.getKey();
            vehicles = (List<VehicleInfo>) entry.getValue();
        }
        model.addAttribute("vehicles",vehicles);
        model.addAttribute("pageNum",pageNum);
        if ((count % 10) == 0){
            count = count / 10;
        }else {
            count = count / 10 +1;
        }
        model.addAttribute("pageCount",count);
        if (count >= pageNum + 1){
            model.addAttribute("next1",true);
        }
        if (count >= pageNum + 2){
            model.addAttribute("next2",true);
        }
        return "vehiclePage/vehicleInfo";
    }

    /**
     * 修改车辆信息
     * @param request
     * @return
     */
    @ApiOperation("修改车辆信息")
    @RequestMapping("/vehicle/updateVehicle")
    public String updateVehicle(HttpServletRequest request){
        String id = request.getParameter("id");
        String vNum = request.getParameter("vNum");
        String vName = request.getParameter("vName");
        String vType = request.getParameter("vType");
        VehicleInfo vehicleInfo = new VehicleInfo(Integer.valueOf(id), vName, vNum, vType);
        boolean b = vehicleService.updateVehicle(vehicleInfo);
        if (!b){
            log.warn(vNum + "信息更新失败");
            ///ts/vehicle/vehicles?pageNum=1
            return "redirect:/ts/vehicle/vehicles?pageNum=1";
        }
        log.info(vNum + "信息更新成功");
        return "redirect:/ts/vehicle/vehicles?pageNum=1";
    }

    /**
     * 删除车辆信息
     * @param request
     * @return
     */
    @ApiOperation("删除车辆信息")
    @RequestMapping("/vehicle/deleteVehicle")
    public String deleteVehicle(HttpServletRequest request){
        String id = request.getParameter("id");
        String vNum = request.getParameter("vNum");
        boolean b = vehicleService.deleteVehicle(Integer.valueOf(id));
        if (!b){
            log.warn(vNum + "车辆信息删除失败");
            return "redirect:/ts/vehicle/vehicles";
        }
        log.warn(vNum + "车辆信息删除成功");
        return "redirect:/ts/vehicle/vehicles?pageNum=1";
    }

    @ApiOperation("添加车辆信息")
    @RequestMapping("/vehicle/insertVehicle")
    public String insertVehicle(HttpServletRequest request){
        String vName = request.getParameter("vName");
        String vNum = request.getParameter("vNum");
        String vType = request.getParameter("vType");
        boolean b = vehicleService.insertVehicle(new VehicleInfo(1, vName, vNum, vType));
        if (!b){
            log.warn(vNum + "车辆信息添加失败");
            return "redirect:/ts/vehicle/vehicles";
        }
        log.warn(vNum + "车辆信息添加成功");
        return "redirect:/ts/vehicle/vehicles?pageNum=1";
    }

    @ApiOperation("搜索车辆信息")
    @RequestMapping("/vehicle/searchVehicle")
    public String searchvehicle(HttpServletRequest request, Model model){
        String search = request.getParameter("search");
        if (search == null || search == ""){
            return "redirect:/ts/vehicle/vehicles?pageNum=1";
        }
        List<VehicleInfo> vehicles = vehicleService.search(search);
        if (vehicles == null){
            log.warn("搜索车辆信息为空");
            return "redirect:/ts/vehicle/vehicles?pageNum=1";
        }
        model.addAttribute("vehicles",vehicles);
        return "vehiclePage/vehicleInfo";
    }
}
