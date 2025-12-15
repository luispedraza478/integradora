package com.example.integradora.controller;

import com.example.integradora.model.Book;
import com.example.integradora.service.LibraryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return libraryService.addBook(book);
    }
    @GetMapping
    public Object getAllBooks() {
        return libraryService.getAllBooks();
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return libraryService.getBookById(id);
    }
    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable int id) {
        libraryService.changeBookStatus(id);
        return "Estado del libro actualizado";
    }
}
