package com.dao.impl;

import com.dao.BookDao;

public class BookDaoImpl2 implements BookDao {
    int bookNum;
    @Override
    public void save()
    {
        System.out.println("book is saving... current book number is"+bookNum);
    }

    public void setBookNum(int bookNum)
    {
        this.bookNum=bookNum;
    }
}
