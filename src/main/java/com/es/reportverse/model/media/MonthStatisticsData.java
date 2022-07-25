package com.es.reportverse.model.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class MonthStatisticsData extends Media{

    private int year;
    private int month;

    public MonthStatisticsData(String pdfCode, int year, int month) {
        this(year, month);
        this.setCode(pdfCode);
    }

}
