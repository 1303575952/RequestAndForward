package com.emep.changzhi.analyse.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author lifei
 * @version
 * @Description:
 * @date
 */
@Data
@Table(name = "enterprise_outlet_info")
@Entity
public class EnterpriseOutletInfo implements Serializable {


    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;
    @Column(name = "enterprise_name",columnDefinition="varchar(100) COMMENT '企业名称'" )
    private String enterpriseName;
    @Column(name = "outlet_name",columnDefinition="varchar(100) COMMENT '监控点名称'" )
    private  String outletName;
    @Column(name = "create_date",columnDefinition="varchar(100) COMMENT '录入时间'" )
    private String createDate = String.valueOf(System.currentTimeMillis());
    @Column(name = "is_delete",columnDefinition="varchar(100) COMMENT '是否删除'" )
    private String isDelete = "0";

    @Transient
    private String sss;


}
