package com.emep.changzhi.analyse.repository;

import com.emep.changzhi.analyse.entity.SysAqiInfo;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wz
 * @version V1.0
 * @Description: Api 持久层
 * @date 2018/11/23 19:20
 */
public interface SysAqiInfoRepository extends JpaRepository<SysAqiInfo,Long>{
    @Query(value = "select * from sys_aqi_info where id = :id",nativeQuery = true) //逗号后为使用原生sql，不然不支持*号
    SysAqiInfo findSysAqiInfoById(@Param("id") String id);

    @Modifying //提醒jpa这是一个update操作
    @Transactional  //事物管理
    Integer deleteSysAqiInfoById(String id);

    Page<SysAqiInfo> findAll(Specification<SysAqiInfo> spec, Pageable pageable);

    List<SysAqiInfo> findByIsDelete(String is_delete);



    @Query(value = "update sys_aqi_info set aqi =:aqi where id =:id",nativeQuery = true)
    @Modifying
    Integer updateApi(@Param("id")String id,@Param("aqi")String aqi);


    /**
     * 通过前面的配置可以看出，Spring 对 JPA 的支持已经非常强大，开发者无需过多关注 EntityManager 的创建、事务处理等 JPA 相关的处理
     * ***********************************************************************
     * 可使用的接口有：                                                            **********
     *　   Repository：是 Spring Data的一个核心接口，它不提供任何方法，开发者需要在自己定义的接口中声明需要的方法。**
     *     CrudRepository：继承Repository，提供增删改查方法，可以直接调用。                            **
     *     PagingAndSortingRepository：继承CrudRepository，具有分页查询和排序功能（本类实例）        **
     *     JpaRepository：                         继承PagingAndSortingRepository，针对JPA技术提供的接口            **
     *     JpaSpecificationExecutor：          可以执行原生SQL查询                                    **
     *    继承不同的接口，有两个不同的泛型参数，他们是该持久层操作的类对象和主键类型。                            **
     *********************************************************************************
     *
     * //spring data jpa 提供了多个接口并封装了部分数据库操作方法，具体可参见下面代码
     * List<T> findAll();
     * List<T> findAll(Sort sort);
     * List<T> findAll(Iterable<ID> iterable);
     * <S extends T> List<S> save(Iterable<S> iterable);
     * void flush();
     * <S extends T> S saveAndFlush(S s);
     * void deleteInBatch(Iterable<T> iterable);
     * void deleteAllInBatch();
     * T getOne(ID id);
     * <S extends T> List<S> findAll(Example<S> example);
     * <S extends T> List<S> findAll(Example<S> example, Sort sort);
     * Page<T> findAll(Pageable pageable);
     * <S extends T> S save(S s);
     * T findOne(ID id);
     * boolean exists(ID id);
     * long count();
     * void delete(ID id);
     * void delete(T t);
     * void delete(Iterable<? extends T> iterable);
     * void deleteAll();
     * <S extends T> S findOne(Example<S> example);
     * <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);
     * <S extends T> long count(Example<S> example);
     * <S extends T> boolean exists(Example<S> example);
     */
}
