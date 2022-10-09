package com.dao.impl;

import com.dao.BookDao;

public class BookDaoImpl implements BookDao {
    private String db;
    @Override
    public void save()
    {
        System.out.println("book is saving in "+db);
    }
    public void setDb(String db)
    {
        this.db=db;
    }
}
