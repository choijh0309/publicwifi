package com.example.mission1.bookmark;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BookMark {
    private int id;
    private String bookmarkName;
    private String wifiName;
    private Timestamp regiDate;

    public BookMark(int id, String bookmarkName, String wifiName, Timestamp regiDate) {
        this.id = id;
        this.bookmarkName = bookmarkName;
        this.wifiName = wifiName;
        this.regiDate = regiDate;
    }
}
