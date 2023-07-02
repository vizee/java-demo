package com.example.demo.service;

import com.example.demo.entity.Book;

public interface BookService {

    Iterable<Book> getList(int page, int size);

    Book getByISBN(String isbn);

    Book save(Book book);

    void delete(int id);
}
