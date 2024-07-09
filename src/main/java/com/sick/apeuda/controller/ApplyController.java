package com.sick.apeuda.controller;

import com.sick.apeuda.dto.ApplyReqDto;
import com.sick.apeuda.dto.ApplyResDto;


import com.sick.apeuda.service.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/apply")
public class ApplyController {
    private final ApplyService applyService;

    // 조회 기능 select
    @GetMapping("/list/{projectId}")
    public ResponseEntity<List<ApplyResDto>> applyList(@PathVariable Long projectId){
        List<ApplyResDto> list = applyService.getApplyList(projectId);
        return ResponseEntity.ok(list);
    }
    // 요청기능
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertApply(@RequestBody ApplyReqDto applyReqDto){
        boolean isTrue = applyService.saveApply(applyReqDto);
        return ResponseEntity.ok(isTrue);
    }
    // 수락기능
//    @PostMapping("/accept")
//    public ResponseEntity<Boolean> acceptApply(@RequestBody ApplyReqDto applyReqDto){
//        boolean isTrue = applyService.acceptApply(applyReqDto);
//        return ResponseEntity.ok(isTrue);
//    }
    // 거절기능
//    @PostMapping("/refuse")
//    public ResponseEntity<Boolean> refuseApply(@RequestBody ApplyReqDto applyReqDto){
//        boolean isTrue = applyService.saveApply(applyReqDto);
//        return ResponseEntity.ok(isTrue);
//    }
    // 수락 거절 기능
//    @PostMapping("/decision/{}/{result}")
//    public ResponseEntity<Boolean> acceptApply(@PathVariable boolean result){
//        boolean isTrue = applyService.decisionApply(result);
//        return ResponseEntity.ok(isTrue);
//    }
}
