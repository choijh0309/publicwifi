package com.example.mission1.history;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class History {
    private int id;
    private double lat;
    private double lnt;
    private Timestamp inquiryDate;

    public History(int id, double lat, double lnt, Timestamp inquiryDate) {
        this.id = id;
        this.lat = lat;
        this.lnt = lnt;
        this.inquiryDate = inquiryDate;
    }

}
