package com.emep.changzhi.analyse.service;

import com.emep.changzhi.analyse.entity.SysAqiInfo;
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
 * @author wz
 * @version V1.0
 * @Description: api 业务层
 * @date 2018/11/23 19:20
 */
@Slf4j
@Data
@Service
@Transactional
public class SysAqiInfoService {
    
    @Resource
    private SysAqiInfoRepository sysAqiInfoRepository;
    

    /**
     *
     * 不含查询条件的分页获取数据
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public Page<SysAqiInfo> findAll(Integer pageNo, Integer pageSize){
        try {
            Pageable pageable = new PageRequest(pageNo-1, pageSize);
            return sysAqiInfoRepository.findAll(pageable);
        } catch (Exception e) {
            log.error(e.getClass().getName()+":"+ e.getMessage());
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     *
     * 新增用户
     *
     * @param payload
     * @return
     * @throws Exception
     */
    public SysAqiInfo save(Map<String, Object> payload) {
        SysAqiInfo SysAqiInfo = null;
        try {
            SysAqiInfo SysAqiInfo1 = (SysAqiInfo) MapUtils.convertMap(SysAqiInfo.class,payload);
            SysAqiInfo = sysAqiInfoRepository.save(SysAqiInfo1);
        }catch (Exception e){
            log.error(e.getClass().getName()+":"+ e.getMessage());
            log.error(e.getLocalizedMessage());
        }
        return SysAqiInfo;
    }


    /**
     *
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    public SysAqiInfo findSysAqiInfoById(String id){
        SysAqiInfo SysAqiInfo = null;
        try {
            SysAqiInfo = sysAqiInfoRepository.findSysAqiInfoById(id);
        }catch (Exception e){
            log.error(e.getClass().getName()+":"+ e.getMessage());
            log.error(e.getLocalizedMessage());
        }
        return SysAqiInfo;
    }

    /**
     *
     * 根据id 删除用户
     *
     * @param id
     */
    public Boolean delete(String id) {
        Boolean result = false;
        try {
            Integer  result1 = sysAqiInfoRepository.deleteSysAqiInfoById(id);
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
     *查询所有用户
     *
     * @return
     */
    public List<SysAqiInfo> list() {
        List<SysAqiInfo> SysAqiInfos = null;
        try {
            SysAqiInfos =  sysAqiInfoRepository.findAll();
        }catch (Exception e){
            log.error(e.getClass().getName()+":"+ e.getMessage());
            log.error(e.getLocalizedMessage());
        }
        return SysAqiInfos;
    }


    /**
     *
     * @param page 页数
     * @param size 数量
     * @param sysAqiInfo 参数
     * @return
     */
    public Page<SysAqiInfo> findBookCriteria(Integer page, Integer size, SysAqiInfo sysAqiInfo) {
        //规格定义
        Pageable pageable = new PageRequest(page-1,size , Sort.Direction.ASC, "createDate"); //页码：前端从1开始，jpa从0开始，做个转换
        Page<SysAqiInfo> studentPage = sysAqiInfoRepository.findAll(new Specification<SysAqiInfo>(){
            @Override
            public Predicate toPredicate(Root<SysAqiInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.equal(root.get("aqi").as(String.class), sysAqiInfo.getAqi());
                query.where(criteriaBuilder.or(p1));
                return query.getRestriction();
            }
        },pageable);
        return studentPage;
    }

    public Integer updateAqi(String id,String aqi){
        Integer aa = sysAqiInfoRepository.updateApi(id,aqi);
        return aa;
    }
}
