package com.example.mission1.bookmarkgroup;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BookMarkGroup {
    private int id;
    private String bookmarkgroupName;
    private int bookmarkgroupOrder;
    private Timestamp regiDate;
    private Timestamp editDate;

    public BookMarkGroup(int id, String bookmarkgroupName, int bookmarkgroupOrder, Timestamp regiDate, Timestamp editDate) {
        this.id = id;
        this.bookmarkgroupName = bookmarkgroupName;
        this.bookmarkgroupOrder = bookmarkgroupOrder;
        this.regiDate = regiDate;
        this.editDate = editDate;
    }
}
