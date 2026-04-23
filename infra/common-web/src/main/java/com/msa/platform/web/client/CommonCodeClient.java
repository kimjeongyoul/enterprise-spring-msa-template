package com.msa.platform.web.client;

import com.msa.platform.core.dto.CommonCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "common-service", url = "${app.common-service.url:http://common-service}")
public interface CommonCodeClient {

    @GetMapping("/api/v1/codes/groups/{groupCode}")
    List<CommonCodeResponse> getCodesByGroup(@PathVariable("groupCode") String groupCode);

    @GetMapping("/api/v1/codes/groups/{groupCode}/codes/{code}")
    CommonCodeResponse getCode(@PathVariable("groupCode") String groupCode, @PathVariable("code") String code);
}
