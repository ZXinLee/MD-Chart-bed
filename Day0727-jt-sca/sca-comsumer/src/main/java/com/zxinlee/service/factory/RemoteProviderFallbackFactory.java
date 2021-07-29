package com.zxinlee.service.factory;

import com.zxinlee.service.RemoteProviderService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 基于此对象处理RemoteProviderService接口调用 时出现异常的问题
 */
@Component
public class RemoteProviderFallbackFactory implements FallbackFactory<RemoteProviderService> {
    @Override
    public RemoteProviderService create(Throwable throwable) {
        //return msg-> "call exception : msg is "+ throwable.getMessage();
        return msg -> "服务维护中,稍等片刻再访问";
    }
}
