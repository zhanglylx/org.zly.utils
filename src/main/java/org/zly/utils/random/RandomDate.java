package org.zly.utils.random;

import lombok.Data;
import org.zly.utils.date.ZlyDateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<String> toListString(){
        List<String> list = new ArrayList<>(2);
        list.add(getStartDateStr());
        list.add(getEndDateStr());
        return list;
    }

    public List<Date> toListDate(){
        List<Date> list = new ArrayList<>(2);
        list.add(getStartDate());
        list.add(getEndDate());
        return list;
    }

}

