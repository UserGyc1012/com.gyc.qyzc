package com.offcn.project.config;

import com.offcn.util.OssTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AppProjectConfig
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/3 16:58
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Configuration
public class AppProjectConfig {
    @ConfigurationProperties(prefix = "oss")
    @Bean
    public OssTemplate ossTemplate(){
        return new OssTemplate();
    }
}
