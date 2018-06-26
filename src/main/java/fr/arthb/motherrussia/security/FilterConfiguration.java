//package fr.arthb.motherrussia.security;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//
//public class FilterConfiguration {
//
//    @Bean
//    public FilterRegistrationBean<BasicAuthFilter> loggingFilter(){
//        FilterRegistrationBean<BasicAuthFilter> registrationBean
//                = new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(new BasicAuthFilter());
//        registrationBean.addUrlPatterns("/mrussia/*");
//
//        return registrationBean;
//    }
//}
