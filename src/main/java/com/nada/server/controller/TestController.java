package com.nada.server.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @ApiOperation(value = "스웨거 테스트1", notes = "data reponse가 없는 API")
    @GetMapping("/api/test/without")
    public Result withoutData(){
        return new Result("AWS 배포까지 통신 성공입니다!", true);
    }

    @ApiOperation(value = "스웨거 테스트2", notes = "data reponse가 있는 API")
    @GetMapping("/api/test/with")
    public ResultData withData(){
        TestResultDTO testResultDTO = new TestResultDTO("yaewon", 23);
        return new ResultData(testResultDTO,"AWS 배포까지 통신 성공입니다!", true);
    }

    @Data
    @AllArgsConstructor
    static class ResultData{
        private TestResultDTO data;
        private String msg;
        private Boolean success;
    }

    @Data
    @AllArgsConstructor
    static class Result{

        private String msg;
        private Boolean success;
    }

    @Data
    @AllArgsConstructor
    static class TestResultDTO{

        private String name;
        private int age;
    }
}
