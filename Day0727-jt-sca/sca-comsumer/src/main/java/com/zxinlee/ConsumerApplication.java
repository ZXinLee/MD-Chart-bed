package com.zxinlee;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Value("${server.port}")
    private String server;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }

    @RestController
    public class ProviderController {

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private RestTemplate loadBalancedRestTemplate;

        /**
         * 负载均衡客户端对象
         * 基于此对象可以从注册中心获取服务实例
         */
        @Autowired
        private LoadBalancerClient loadBalancerClient;

        //http://ip:port/consumer/doFindById?id=20
        @GetMapping("/consumer/doFindById")
        @SentinelResource("findById")
        public String doFindById(@RequestParam("id") Integer id){
            return "resource id is " + id;
        }

        @GetMapping("/consumer/doEcho")
        public String doEcho() {
            //访问http://localhost:8081/provider/doEcho/{msg}
            String url = "http://localhost:8081/provider/echo/" + server;
            //远程过程调用-RPC
            return restTemplate.getForObject(url, String.class);
        }

        @GetMapping("/consumer/doEcho02")
        public String doEcho02() {
            //基于服务名查找服务实例
            ServiceInstance choose = loadBalancerClient.choose("sca-provider");
            String ip = choose.getHost();
            int port = choose.getPort();//8080,8081
            //构建远程调用的URL
            //String url = "http://" + ip + ":" + port + "/provider/echo/" + server;
            String url = String.format("http://%s:%s/provider/echo/%s", ip, port, server);
            //远程过程调用-RPC
            return restTemplate.getForObject(url, String.class);
        }

        @GetMapping("/consumer/doEcho03")
        public String doEcho03() {
            String url = String.format("http://%s/provider/echo/%s", "sca-provider",server);
            return loadBalancedRestTemplate.getForObject(url,String.class);
        }
    }
}
