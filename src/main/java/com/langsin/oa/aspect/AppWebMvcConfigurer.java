package com.langsin.oa.aspect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @Description:
 * @author: wyy
 * @date: 2019/1/23 10:18
 */
@Configuration
public class AppWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${filePath}")
    public String filePath;
    @Value("${fileUrl}")
    public String fileUrl;
    @Value("${fileMap}")
    public String fileMap;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //setUseSuffixPatternMatch 后缀模式匹配
        configurer.setUseSuffixPatternMatch(true);
        //setUseTrailingSlashMatch 自动后缀路径模式匹配
        configurer.setUseTrailingSlashMatch(true);
    }

    /**
     * 资源映射配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileMap)
                .addResourceLocations("file:" + filePath);

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods("*").maxAge(3600);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }
}
