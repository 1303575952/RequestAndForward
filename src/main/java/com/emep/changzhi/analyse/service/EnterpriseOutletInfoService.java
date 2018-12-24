package com.emep.changzhi.analyse.service;

import com.emep.changzhi.analyse.entity.EnterpriseOutletInfo;
import com.emep.changzhi.analyse.entity.SysAqiInfo;
import com.emep.changzhi.analyse.repository.EnterpriseOutletInfoRepository;
import com.emep.changzhi.analyse.repository.SysAqiInfoRepository;
import com.emep.changzhi.analyse.utils.MapUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version V1.0
 * @Description: api 业务层
 * @date
 */
@Slf4j
@Data
@Service
@Transactional
public class EnterpriseOutletInfoService {
    
    @Resource
    private EnterpriseOutletInfoRepository enterpriseOutletInfoRepository;
    

    /**
     *
     * 不含查询条件的分页获取数据
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public Page<EnterpriseOutletInfo> findAll(Integer pageNo, Integer pageSize){
        try {
            Pageable pageable = new PageRequest(pageNo-1, pageSize);
            return enterpriseOutletInfoRepository.findAll(pageable);
        } catch (Exception e) {
            log.error(e.getClass().getName()+":"+ e.getMessage());
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     *
     * 新增
     *
     * @param payload
     * @return
     * @throws Exception
     */
    public EnterpriseOutletInfo save(Map<String, Object> payload) {
        EnterpriseOutletInfo enterpriseOutletInfo = null;
        try {
            EnterpriseOutletInfo enterpriseOutletInfo1 = (EnterpriseOutletInfo) MapUtils.convertMap(EnterpriseOutletInfo.class,payload);
            enterpriseOutletInfo = enterpriseOutletInfoRepository.save(enterpriseOutletInfo1);
        }catch (Exception e){
            log.error(e.getClass().getName()+":"+ e.getMessage());
            log.error(e.getLocalizedMessage());
        }
        return enterpriseOutletInfo;
    }


    /**
     *
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    public EnterpriseOutletInfo findEnterpriseOutletInfoById(String id){
        EnterpriseOutletInfo enterpriseOutletInfo = null;
        try {
            enterpriseOutletInfo = enterpriseOutletInfoRepository.findEnterpriseOutletInfoById(id);
        }catch (Exception e){
            log.error(e.getClass().getName()+":"+ e.getMessage());
            log.error(e.getLocalizedMessage());
        }
        return enterpriseOutletInfo;
    }

    /**
     *
     * 根据id 删除
     *
     * @param id
     */
    public Boolean delete(String id) {
        Boolean result = false;
        try {
            Integer  result1 = enterpriseOutletInfoRepository.deleteEnterpriseOutletInfoById(id);
            if(result1 == 1){
                result = true;
            }
        }catch (Exception e){
            log.error(e.getClass().getName()+":"+ e.getMessage());
            log.error(e.getLocalizedMessage());
        }
        return result;
    }

    /**
     *查询所有
     *
     * @return
     */
    public List<EnterpriseOutletInfo> list() {
        List<EnterpriseOutletInfo> enterpriseOutletInfos = null;
        try {
            enterpriseOutletInfos =  enterpriseOutletInfoRepository.findAll();
        }catch (Exception e){
            log.error(e.getClass().getName()+":"+ e.getMessage());
            log.error(e.getLocalizedMessage());
        }
        return enterpriseOutletInfos;
    }


    /**
     *
     * @param page 页数
     * @param size 数量
     * @param enterpriseOutletInfo 参数
     * @return
     */
    public Page<EnterpriseOutletInfo> findBookCriteria(Integer page, Integer size, EnterpriseOutletInfo enterpriseOutletInfo) {
        //规格定义
        Pageable pageable = new PageRequest(page-1,size , Sort.Direction.ASC, "createDate"); //页码：前端从1开始，jpa从0开始，做个转换
        Page<EnterpriseOutletInfo> enterpriseOutletInfoPage = enterpriseOutletInfoRepository.findAll(new Specification<EnterpriseOutletInfo>(){
            @Override
            public Predicate toPredicate(Root<EnterpriseOutletInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.equal(root.get("aqi").as(String.class), enterpriseOutletInfo.getOutletName());
                query.where(criteriaBuilder.or(p1));
                return query.getRestriction();
            }
        },pageable);
        return enterpriseOutletInfoPage;
    }

    public Integer updateOutletName(String id,String outletName){
        Integer aa = enterpriseOutletInfoRepository.updateOutletName(id,outletName);
        return aa;
    }
}
