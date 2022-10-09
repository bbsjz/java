package com.dao.impl;

import com.dao.BookDao;

public class BookDaoImpl3 implements BookDao {
    int bookNum;
    @Override
    public void save()
    {
        System.out.println("book is saving... current book number is"+bookNum);
    }
}
