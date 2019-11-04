package com.lzc.books.config;



import com.lzc.books.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer
{

    @Bean //将组件注册在容器
    public WebMvcConfigurer webMvcConfigurerAdapter(){
        WebMvcConfigurer adapter = new WebMvcConfigurer()
        {
            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry)
            {
              /*  registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/login.html","/","/api/loginCheck","/asserts/**","/webjars/**");*/
            }
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry)
            {
            registry.addResourceHandler("/mm/**").addResourceLocations("file:\\project\\books\\src\\main\\resources\\static\\asserts\\");

           /*     String path = System.getProperty("user.dir")+"\\books\\src\\main\\resources\\static\\asserts\\mm\\";
                String os = System.getProperty("os.name");
                    registry.addResourceHandler("/mm/**").
                            addResourceLocations("file:"+path);*/
            }

        };
        return adapter;
    }

}
