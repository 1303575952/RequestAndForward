package com.emep.changzhi.analyse.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author wz
 * @version V1.0
 * @Description: AQI标准信息表
 * @date 2018/11/23 19:20
 */
@Data
@Table(name = "sys_aqi_info")
@Entity
public class SysAqiInfo implements Serializable {


    private static final long serialVersionUID = -4415880253641972999L;

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;
    @Column(name = "aqi",columnDefinition="varchar(100) COMMENT 'aqi'" )
    private String aqi;
    @Column(name = "co",columnDefinition="varchar(100) COMMENT '一氧化碳'" )
    private  String co;
    @Column(name = "create_date",columnDefinition="varchar(100) COMMENT '录入时间'" )
    private String createDate = String.valueOf(System.currentTimeMillis());
    @Column(name = "is_delete",columnDefinition="varchar(100) COMMENT '是否删除'" )
    private String isDelete = "0";

    @Transient
    private String sss;


}
