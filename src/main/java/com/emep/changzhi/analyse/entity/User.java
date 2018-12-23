package com.emep.changzhi.analyse.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/********************************************************
 * author  : wz
 * version : 2.0
 * date    : 2018/9/5 上午10:12
 * desc    : 用户表
 ********************************************************/

@Data
@Table(name = "user")
@Entity
public class User implements Serializable {


    private static final long serialVersionUID = -4415880253641972999L;

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(name = "user_name",columnDefinition="varchar(100) COMMENT '用户昵称'" )
    private String userName;

    @Column(name = "header_url",columnDefinition="varchar(100) COMMENT '用户头像url'" )
    private  String headerUrl;

    @Column(name = "phone",columnDefinition="varchar(100) COMMENT '用户手机号'" )
    private String phone;

    @Column(name = "verify_code",columnDefinition="varchar(100) COMMENT '验证码'" )
    private String verifyCode;

    @Column(name = "password",columnDefinition="varchar(100) COMMENT '密码'" )
    private String password;

    @Column(name = "member_grade",columnDefinition="varchar(100) COMMENT '用户等级'" )
    private String memberGrade;

    @Column(name = "parent_id",columnDefinition="varchar(100) COMMENT '父id'" )
    private String parentId;

    @Column(name = "price",columnDefinition="double(10,2) COMMENT '钱包金额'" )
    private Double price;

    @Column(name = "profit_price",columnDefinition="double(10,2) COMMENT '盈利金额'" )
    private Double profitPrice;

    @Column(name = "save_date",columnDefinition="varchar(100) COMMENT '保存时间'" )
    private String saveDate = String.valueOf(System.currentTimeMillis());

    @Column(name = "update_date",columnDefinition="varchar(100) COMMENT '更新时间'" )
    private String updateDate;


}