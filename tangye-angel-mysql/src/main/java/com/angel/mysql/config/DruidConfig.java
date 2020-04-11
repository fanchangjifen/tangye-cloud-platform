package com.angel.mysql.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Druid 监控配置
 * @date 2020-04-11
 * @author GilbertPan
 */

@Configuration
public class DruidConfig {
    @Bean
    public ServletRegistrationBean druidStatViewServlet(){
        //ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean
                = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //添加初始化参数：initParams
        //白名单：
        //servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow)
        // 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        //servletRegistrationBean.addInitParameter("deny","192.168.1.7");
        //登录查看信息的账号和密码.
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","admin");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }
}
