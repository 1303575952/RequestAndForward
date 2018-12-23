package com.emep.changzhi.analyse.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @author wz
* @Description: 阿里durid 配置
* @date 2018/11/23 19:20
* @version V1.0
*/
@Configuration
public class DruidConfiguraton {

     @Bean
     public ServletRegistrationBean statViewServle(){
         ServletRegistrationBean reg = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
         reg.addInitParameter("allow","127.0.0.1");
         reg.addInitParameter("deny","192.168.1.100");
         reg.addInitParameter("loginUsername", "root");
         reg.addInitParameter("loginPassword", "root");
         reg.addInitParameter("resetEnable", "false");
         return reg;
     }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

}
