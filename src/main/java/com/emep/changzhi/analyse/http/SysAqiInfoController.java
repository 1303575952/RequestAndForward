package com.emep.changzhi.analyse.http;

import com.emep.changzhi.analyse.entity.SysAqiInfo;
import com.emep.changzhi.analyse.model.ResultBean;
import com.emep.changzhi.analyse.service.SysAqiInfoService;
import com.emep.changzhi.analyse.utils.Constant;
import com.emep.changzhi.analyse.utils.DataValidator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * @author wz
 * @version V1.0
 * @Description: api  控制器
 * @date 2018/11/23 19:20
 */
@RestController
@RequestMapping("sysaqiinfo")
@Api(value = "aqi controller",tags = "AQI控制器")
public class SysAqiInfoController {
    
    @Autowired
    private SysAqiInfoService sysAqiInfoService;

    /**
     * 查询所有api
     * @return
     */
    @ApiOperation(value="获取api列表", notes="无参数获取所有api信息")
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 0, message = "操作失败，服务器内部异常"),
            @ApiResponse(code = 303, message = "权限不足") })
    @GetMapping(value = "list")
    public ResultBean<Collection<SysAqiInfo>> queryUser(){
        return new ResultBean<Collection<SysAqiInfo>>(sysAqiInfoService.list());
    }

    /**
     * 新增api
     * @param payload
     * @return
     */
    @ApiOperation(value="新增api", notes="新增api")
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 0, message = "操作失败，服务器内部异常"),
            @ApiResponse(code = 303, message = "权限不足") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aqi", value = "aqi数值", required = true, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "co", value = "一氧化碳", required = true, dataType = "String",paramType="query")
    })
    @PostMapping(value = "add")
    public ResultBean<SysAqiInfo> add(@RequestParam Map<String, Object> payload){
        ResultBean result = DataValidator.validateMapData(payload, "aqi","co");
        if (result.getCode() == Constant.RESULT_CODE_SUCCESS){
            return result.boolAdd(sysAqiInfoService.save(payload)) ;
        }
        return  result;
    }

    /**
     * 通过id 查询api
     * @param payload
     * @return
     */
    @ApiOperation(value="根据id 查询api", notes="根据用户的id来获取api信息")
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 0, message = "操作失败，服务器内部异常"),
            @ApiResponse(code = 303, message = "权限不足") })
    @ApiImplicitParam(name = "id", value = "apiID", required = true, dataType = "String", paramType = "query")
    @GetMapping(value = "findapiById")
    public ResultBean<SysAqiInfo> queryUser(@RequestParam Map<String, Object> payload){
        ResultBean result = DataValidator.validateMapData(payload, "id");
        if (result.getCode() == Constant.RESULT_CODE_SUCCESS) {
            return  result.ok(sysAqiInfoService.findSysAqiInfoById(payload.get("id").toString()));
        }
        return  result;
    }

    /**
     * 根据id 删除api
     * @param payload
     */
    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 0, message = "操作失败，服务器内部异常"),
            @ApiResponse(code = 303, message = "权限不足") })
    @ApiImplicitParam(name = "id", value = "apiID", required = true, dataType = "String",paramType = "query")
    @DeleteMapping(value = "/delete")
    public ResultBean<Boolean> delete(@RequestParam Map<String, Object> payload){
        ResultBean result = DataValidator.validateMapData(payload, "id");
        if (result.getCode() == Constant.RESULT_CODE_SUCCESS){
            return result.bool(sysAqiInfoService.delete(payload.get("id").toString()));
        }
        return result;
    }

    /**
     * 分页查询api
     * page，第几页，从0开始，默认为第0页
     * size，每一页的大小，默认为20
     * sort，排序相关的信息，以property,property(,ASC|DESC)的方式组织，例如sort=firstname&sort=lastname,desc表示在按firstname正序排列基础上按lastname倒序排列
     * @param
     * @return
     */
    @ApiOperation(value="分页查询api", notes="分页无参数查询api")
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 0, message = "操作失败，服务器内部异常"),
            @ApiResponse(code = 303, message = "权限不足") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "数量", required = true, dataType = "String",paramType="query"),
    })
    @GetMapping("findSysAqiInfoNoQuery")
    public ResultBean<Page<SysAqiInfo>> showUsers(@RequestParam Map<String, Object> payload) {
        ResultBean result = DataValidator.validateMapData(payload, "pageIndex","pageSize");
        if (result.getCode() == Constant.RESULT_CODE_SUCCESS){
            Page<SysAqiInfo> pageSysAqiInfo = sysAqiInfoService.findAll(Integer.parseInt(payload.get("pageIndex")
                    .toString()),Integer.parseInt(payload.get("pageSize").toString()));
            return result.ok(pageSysAqiInfo);
        }
        return result;
    }

    /**
     * 分页查询api列表
     * page，第几页，从0开始，默认为第0页
     * size，每一页的大小，默认为20
     * sort，排序相关的信息，以property,property(,ASC|DESC)的方式组织，例如sort=firstname&sort=lastname,desc表示在按firstname正序排列基础上按lastname倒序排列
     * @param
     * @return
     */
    @ApiOperation(value="分页带参数查询api", notes="分页带参数查询api")
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 0, message = "操作失败，服务器内部异常"),
            @ApiResponse(code = 303, message = "权限不足") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "数量", required = true, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "aqiName", value = "api名字", required = true, dataType = "String",paramType="query"),
    })
    @GetMapping("findSysAqiInfoQuery")
    public ResultBean<Page<SysAqiInfo>> PageUsers(@RequestParam Map<String, Object> payload) {
        ResultBean result = DataValidator.validateMapData(payload, "pageIndex","pageSize","aqiName");
        if (result.getCode() == Constant.RESULT_CODE_SUCCESS){
            SysAqiInfo SysAqiInfo = new SysAqiInfo();
            SysAqiInfo.setAqi(payload.containsKey("aqiName") ?payload.get("aqiName") != null ? payload.get("aqiName").toString():"":"" );
            Page<SysAqiInfo> pageSysAqiInfo = sysAqiInfoService.findBookCriteria(Integer.parseInt(payload.get("pageIndex") .toString()),Integer.parseInt(payload.get("pageSize").toString()),SysAqiInfo);
            return result.ok(pageSysAqiInfo);
        }
        return result;
    }

    /**
     * 分页查询api列表
     * page，第几页，从0开始，默认为第0页
     * size，每一页的大小，默认为20
     * sort，排序相关的信息，以property,property(,ASC|DESC)的方式组织，例如sort=firstname&sort=lastname,desc表示在按firstname正序排列基础上按lastname倒序排列
     * @param
     * @return
     */
    @ApiOperation(value="updateAA", notes="updateAA")
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 0, message = "操作失败，服务器内部异常"),
            @ApiResponse(code = 303, message = "权限不足") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "aqi", value = "aqi", required = true, dataType = "String",paramType="query"),
    })
    @GetMapping("updateAA")
    public ResultBean<Integer> updateAA(@RequestParam Map<String, Object> payload) {
        ResultBean result = DataValidator.validateMapData(payload, "id","aqi");
        if (result.getCode() == Constant.RESULT_CODE_SUCCESS){
            Integer aa = sysAqiInfoService.updateAqi(payload.get("id") .toString(),payload.get("aqi") .toString());
            return result.ok(aa);
        }
        return result;
    }



}
