package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") int page) {
        log.info("list book {}", page);

        return new ResponseEntity<>(bookService.getList(page, 20), HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<?> get(@PathVariable String isbn) {
        log.info("get book {}", isbn);

        return new ResponseEntity<>(bookService.getByISBN(isbn), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Book book) {
        log.info("create book {}", book);

        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody Book book) {
        log.info("update book {}", book);

        return new ResponseEntity<>(bookService.save(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.info("delete book {}", id);

        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
