package com.zxinlee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ProviderApplication {

    /**
     * 日志对象
     * org.slf4j.Logger
     * org.slf4j.LoggerFactory
     * static final
     */
    private static final Logger logger = LoggerFactory.getLogger(ProviderApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    /**
     * 当配置中心的内容发生变化,重新发布时,用于刷新类中的配置信息
     * @RefreshScope 注解是通过重新创建providerController()对象来完成数据的初始化
     */
    @RefreshScope
    @RestController
    public class ProviderController {

        @Value("${server.port}")
        private String server;

        /**
         * 读取配置中心的日志级别
         * 日志优先级 trace<debug<info<warn<<error
         */
        @Value("${logging.level.com.zxinlee:error}")//":" 后面的是默认值
        private String loglevel;

        @Value("${server.tomcat.threads.max:50}")
        private int maxThread;

        @Value("${page.pageSize:3}")
        private int pageSize;

        @GetMapping("/doGetPageSize")
        public String doGetPageSize(){
            return "pageSize is " + pageSize;
        }

        @GetMapping("/provider/doGetMaxThread")
        public String doGetMaxThread(){
            return "max thread is " + maxThread;
        }

        @GetMapping("/provider/doGetLevel")
        public String doGetLevel() {

            logger.trace("==logger.trace==");//跟踪
            logger.debug("==logger.debug==");//调试
            logger.warn("==logger.warn==");//警告
            logger.info("==logger.info==");//常规信息
            logger.error("==logger.error==");//错误信息
            return "log level is " + loglevel;
        }

        @GetMapping("/provider/echo/{msg}")
        public String doEcho(@PathVariable String msg) {//回显函数
            if (msg == null || msg.length() < 3)
                throw new IllegalArgumentException("参数不合法");
            return server + " say: hello " + msg;
        }
    }
}
