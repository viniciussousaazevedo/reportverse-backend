package com.es.reportverse.controller;

import com.es.reportverse.service.MonthStatisticsDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/estatisticas")
@AllArgsConstructor
public class StatisticsController {

    private MonthStatisticsDataService monthStatisticsDataService;

    @GetMapping("/download")
    public ResponseEntity<?> downloadStatistics() throws Exception {
        return new ResponseEntity<>(monthStatisticsDataService.getPDF(), HttpStatus.OK);
    }

}
