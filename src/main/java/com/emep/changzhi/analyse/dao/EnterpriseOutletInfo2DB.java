package com.emep.changzhi.analyse.dao;

import com.emep.changzhi.analyse.entity.EnterpriseOutletInfo;
import com.emep.changzhi.analyse.model.ResultBean;
import com.emep.changzhi.analyse.service.EnterpriseOutletInfoService;
import com.emep.changzhi.analyse.utils.Constant;
import com.emep.changzhi.analyse.utils.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public class EnterpriseOutletInfo2DB {
    @Autowired
    private EnterpriseOutletInfoService enterpriseOutletInfoService;
    public ResultBean<EnterpriseOutletInfo> add(@RequestParam Map<String, Object> payload){
        ResultBean result = DataValidator.validateMapData(payload, "enterpriseName","outletName");
        if (result.getCode() == Constant.RESULT_CODE_SUCCESS){
            return result.boolAdd(enterpriseOutletInfoService.save(payload)) ;
        }
        return  result;
    }
}
