package com.why.transportsecurity.controller;

import com.why.transportsecurity.po.AdminLogin;
import com.why.transportsecurity.po.VehicleInfo;
import com.why.transportsecurity.service.impl.AccidentServiceImpl;
import com.why.transportsecurity.utils.accidentutils.OutputFileUtils;
import com.why.transportsecurity.vo.Acceleration;
import com.why.transportsecurity.vo.DetailInfo;
import com.why.transportsecurity.vo.InfoExcelModel;
import com.why.transportsecurity.vo.TotalInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName：AccidentController
 * @Description：todo 事故控制
 * @Author: why
 * @DateTime：2021/12/20 15:18
 */
@Controller
@Slf4j
@RequestMapping("/ts")
@Api(tags = "事故管理后台")
public class AccidentController {
    @Autowired
    AccidentServiceImpl accidentService;

    @ApiOperation("事故列表")
    @RequestMapping("/accident/warning")
    public String warning(Model model, Principal principal){
        List<TotalInfo> totalInfos = accidentService.totalInfo();
        if (totalInfos.size() != 0){
            if (!totalInfos.get(0).isStatus()){
                model.addAttribute("flag",true);
            }else {
                model.addAttribute("flag",false);
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminLogin userDetails = (AdminLogin) authentication.getPrincipal();
        model.addAttribute("nameLogin",userDetails.getUsername());
        model.addAttribute("infos",totalInfos);
        return "accidentPage/warning";
    }

    @ApiOperation("事故数据展示，地址追踪")
    @RequestMapping("/accident/info")
    public String detailsInfo(HttpServletRequest request,Model model){
        String aIds = request.getParameter("aId");
        String vIds = request.getParameter("vId");
        if (aIds == null || aIds.equals("") || vIds == null || vIds.equals("")){
            return "error/5xx";
        }
        int aId = Integer.valueOf(aIds);
        int vId = Integer.valueOf(vIds);
        model.addAttribute("aId",aId);
        model.addAttribute("vId",vId);

        DetailInfo detailInfo = accidentService.detailInfo(vId,aId);
        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
        model.addAttribute("vehicle",vehicleInfo);
        model.addAttribute("detailInfo",detailInfo);
        model.addAttribute("lng",detailInfo.getLng());
        model.addAttribute("lat",detailInfo.getLat());
        model.addAttribute("address",detailInfo.getAddress());
        return "accidentPage/detailsPage";
    }

    /**
     * 右侧信息展示
     * @param model
     * @param vId
     * @param aId
     * @return
     */
    @RequestMapping(value = "/accident/infoRight",method = RequestMethod.GET)
    public String showTask(Model model, @RequestParam("vId") Integer vId, @RequestParam("aId") Integer aId){
        DetailInfo detailInfo = accidentService.detailInfo(vId, aId);
        model.addAttribute("detailInfo",detailInfo);
        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
        model.addAttribute("vehicle",vehicleInfo);
        List<Double> ax = accidentService.ax(aId);
        List<Double> ay = accidentService.ay(aId);
        List<Acceleration> axs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            axs.add(new Acceleration(ax.get(i),ay.get(i)));
        }
        model.addAttribute("aId",aId);
        model.addAttribute("vId",vId);
        model.addAttribute("acceleration",axs);
        return "accidentPage/infoRight";
    }

    /**
     * 左侧信息展示
     * @param model
     * @param vId
     * @param aId
     * @return
     */
    @RequestMapping(value = "/accident/infoLeft",method = RequestMethod.GET)
    public String showTaskLeft(Model model, @RequestParam("vId") Integer vId, @RequestParam("aId") Integer aId){
        DetailInfo detailInfo = accidentService.detailInfo(vId, aId);
        model.addAttribute("detailInfo",detailInfo);
        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
        model.addAttribute("vehicle",vehicleInfo);
        List<Double> ax = accidentService.ax(aId);
        List<Double> ay = accidentService.ay(aId);
        List<Acceleration> axs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            axs.add(new Acceleration(ax.get(i),ay.get(i)));
        }
        model.addAttribute("aId",aId);
        model.addAttribute("vId",vId);
        model.addAttribute("acceleration",axs);
        return "accidentPage/infoLeft";
    }

    /**
     * 数据下载
     * @param response
     * @param vId
     * @param aId
     */
    @ApiOperation("碰撞数据下载")
    @RequestMapping("/accident/download")
    public void show(HttpServletResponse response, @RequestParam Integer vId, @RequestParam Integer aId){
        //String vName, String vNumber, String vType, String degreeOfDamage, String date,
        // String time, double lng, double lat, String address, String collisionDirection,
        // String isBounce, double adRAcceleration, double adCAcceleration
        List<InfoExcelModel> list = new ArrayList<>();
        InfoExcelModel infoExcelModel = new InfoExcelModel();

        /**
         * 查出车辆基本信息
         */
        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
        infoExcelModel.setVName(vehicleInfo.getVName());
        infoExcelModel.setVNumber(vehicleInfo.getVNum());
        infoExcelModel.setVType(vehicleInfo.getVType());

        /**
         * 根据aId查询加速度信息
         */
        List<Double> ay = accidentService.ay(aId);
        List<Double> ax = accidentService.ax(aId);
        infoExcelModel.setAdRAcceleration(ax);
        infoExcelModel.setAdCAcceleration(ay);

        /**
         * 封装车辆事故基本信息
         */
        DetailInfo detailInfo = accidentService.detailInfo(vId, aId);
        //String vName, String vNumber, String vType, String degreeOfDamage, String date,
        // String time, double lng, double lat, String address, String collisionDirection,
        // String isBounce, double adRAcceleration, double adCAcceleration
        infoExcelModel.setDriverDegreeOfDamage(detailInfo.getDriverDegreeOfDamage());
        infoExcelModel.setPassengerDegreeOfDamage(detailInfo.getPassengerDegreeOfDamage());

        infoExcelModel.setDate(detailInfo.getDate());
        infoExcelModel.setTime(detailInfo.getTime());
        infoExcelModel.setLng(detailInfo.getLng());
        infoExcelModel.setLat(detailInfo.getLat());
        infoExcelModel.setAddress(detailInfo.getAddress());
        infoExcelModel.setCollisionDirection(detailInfo.getDirection());
        infoExcelModel.setIsBounce(detailInfo.getIsBounce());

        list.add(infoExcelModel);

        OutputFileUtils.outputFile(list,response);
    }

    /**
     * 分装加速度信息 备注：需要修改
     * 将所有数据封装
     * @param model
     * @param aId
     * @return
     */
    @ApiOperation("数据分析，图表展示")
    @RequestMapping(value = "/accident/tu",method = RequestMethod.GET)
    public String axAndAy(Model model,@RequestParam("aId") Integer aId){
        List<Double> ax = accidentService.ax(aId);
        List<Double> ay = accidentService.ay(aId);
        model.addAttribute("ax",ax);
        model.addAttribute("ay",ay);
        return "accidentPage/tu";
    }

    @ApiOperation("处理事故")
    @RequestMapping(value = "/accident/solve")
    public String solveAccident(HttpServletRequest request,Model model){
        String aIds = request.getParameter("aId");
        String vIds = request.getParameter("vId");
        int aId = Integer.valueOf(aIds);
        int vId = Integer.valueOf(vIds);
        model.addAttribute("aId",aId);
        model.addAttribute("vId",vId);
        accidentService.solveAccident(Integer.valueOf(aId));
//        DetailInfo detailInfo = accidentService.detailInfo(vId,aId);
//        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
//        model.addAttribute("vehicle",vehicleInfo);
//        model.addAttribute("detailInfo",detailInfo);
//        model.addAttribute("lng",detailInfo.getLng());
//        model.addAttribute("lat",detailInfo.getLat());
//        model.addAttribute("address",detailInfo.getAddress());
        return "redirect:/ts/accident/warning";
    }

    /**
     * 搜寻事故信息
     * @param request
     * @param model
     * @return
     */
    @ApiOperation("搜索事故信息")
    @RequestMapping("/accident/totalSearch")
    public String search(HttpServletRequest request, Model model){
        String search = request.getParameter("search");
        if (search == null || search == ""){
            log.warn("搜索输入为空");
            return "redirect:/ts/accident/warning";
        }
        List<TotalInfo> totalInfos = accidentService.search(search);
        if (totalInfos != null){
            if (!totalInfos.get(0).isStatus()){
                model.addAttribute("flag",true);
            }else {
                model.addAttribute("flag",false);
            }
        }
        model.addAttribute("infos",totalInfos);
        return "accidentPage/warning";
    }
}
