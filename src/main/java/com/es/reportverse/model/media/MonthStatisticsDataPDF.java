package com.es.reportverse.model.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.YearMonth;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class MonthStatisticsDataPDF extends Media{

    @Transient
    private YearMonth yearMonth;

}
