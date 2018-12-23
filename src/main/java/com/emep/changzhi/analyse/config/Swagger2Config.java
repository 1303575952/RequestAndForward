package com.emep.changzhi.analyse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wz
 * @version V1.0
 * @Description: Swagger2 配置文件
 * @date 2018/11/23 19:20
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket myDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfo(  "空项目",
                                        "如有疑问或者错误请联系下方邮箱：",
                                        "1.0",
                                        "apiDocs",
                                        "307476485@qq.com",
                                        "",
                                        "");
        docket.apiInfo(apiInfo);
        return docket;
    }

}
