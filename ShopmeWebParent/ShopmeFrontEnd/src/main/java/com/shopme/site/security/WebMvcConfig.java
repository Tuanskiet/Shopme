package com.shopme.site.security;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //config user image
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("../product/images", registry);
    }

    private void exposeDirectory(String patternName, ResourceHandlerRegistry registry){
        Path path = Paths.get(patternName);
        String absolutePath = path.toFile().getAbsolutePath();
        String logicalPath = patternName.replace("../", "") + "/**";

        registry.addResourceHandler(logicalPath)
                .addResourceLocations("file:/" + absolutePath +"/");
    }


    //config thymeleaf layout
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
