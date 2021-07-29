package com.zxinlee.service;

import com.zxinlee.service.factory.RemoteProviderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sca-provider",
        contextId = "remoteProviderService",
        fallbackFactory = RemoteProviderFallbackFactory.class)
public interface RemoteProviderService {
    @GetMapping("/provider/echo/{msg}")
    String echoMsg(@PathVariable String msg);
}
