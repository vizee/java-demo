package com.example.demo.service.impl;

import com.example.demo.entity.Book;
import com.example.demo.repo.BookRepository;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Iterable<Book> getList(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Book getByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(int id) {
        bookRepository.deleteById(id);
    }
}
