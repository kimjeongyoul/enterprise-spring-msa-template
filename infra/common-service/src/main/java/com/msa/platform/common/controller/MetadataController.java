package com.msa.platform.common.controller;

import com.msa.platform.core.dto.CommonCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/codes")
@RequiredArgsConstructor
public class MetadataController {

    @GetMapping("/groups/{groupCode}")
    public List<CommonCodeResponse> getCodesByGroup(@PathVariable String groupCode) {
        // DB 조회 시뮬레이션
        List<CommonCodeResponse> codes = new ArrayList<>();
        codes.add(CommonCodeResponse.builder()
                .groupCode(groupCode)
                .code("01")
                .name("Sample Code")
                .isUsed(true)
                .build());
        return codes;
    }
}
