package com.practice.api;

import com.practice.entity.Book;
import com.practice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/insert")
    public void insert() {
        bookService.insert();
    }

    @PostMapping("/table")
    public void createTable() {
        bookService.createTable();
    }

    @GetMapping("/{publisher}")
    public Book getBook(@PathVariable("publisher") String publisher) {
        return bookService.findByPublisher(publisher);
    }
}
