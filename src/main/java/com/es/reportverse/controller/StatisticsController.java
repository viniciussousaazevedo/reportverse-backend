package com.es.reportverse.controller;

import com.es.reportverse.service.MonthStatisticsDataService;
import com.es.reportverse.utils.CustomLogger;
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

    public final String CLASS = "StatisticsController";
    private MonthStatisticsDataService monthStatisticsDataService;
    CustomLogger logger;

    private String setContext(String method) {
        return CLASS + ":" + method;
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadStatistics() throws Exception {
        logger.setContext(this.setContext("downloadStatistics"));
        logger.logMethodStart();
        String pdf = monthStatisticsDataService.getPDF();
        logger.logMethodEnd(pdf);
        return new ResponseEntity<>(pdf, HttpStatus.OK);
    }

}
