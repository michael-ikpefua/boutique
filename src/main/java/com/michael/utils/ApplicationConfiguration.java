package com.michael.utils;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public FilterRegistrationBean<RedirectUnAuthenticatedUser> filterRegistrationBean() {
        FilterRegistrationBean <RedirectUnAuthenticatedUser> registrationBean = new FilterRegistrationBean();
        RedirectUnAuthenticatedUser redirectUnAuthenticatedUser = new RedirectUnAuthenticatedUser();

        registrationBean.setFilter(redirectUnAuthenticatedUser);
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
