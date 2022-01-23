package org.zly.utils.random;

import lombok.Data;
import org.zly.utils.date.ZlyDateUtils;

import java.util.Date;

@Data
public class RandomDate {
    private String dateFormat = ZlyDateUtils.GENERAL_TIME_FORMAT;
    private Date startDate = new Date();
    private Date endDate = new Date();

    public String getStartDateStr() {
        return ZlyDateUtils.getStrDate(this.getStartDate(), dateFormat);
    }

    public String getEndDateStr() {
        return ZlyDateUtils.getStrDate(this.getEndDate(), dateFormat);
    }

}

