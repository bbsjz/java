package com.service.impl;

import com.dao.BookDao;
import com.service.BookService;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;
    @Override
    public void save()
    {
        bookDao.save();
    }
    public void setBookDao(BookDao bookDao)
    {
        this.bookDao=bookDao;
    }
}
