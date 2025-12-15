package com.example.integradora.controller;

import com.example.integradora.service.LibraryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LibraryService libraryService;

    public LoanController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public Object createLoan(@RequestParam int userId, @RequestParam int bookId) {
        return libraryService.createLoan(userId, bookId);
    }

    @PostMapping("/{loanId}/return")
    public String returnLoan(@PathVariable int loanId) {
        libraryService.returnLoan(loanId);
        return "Libro devuelto correctamente";
    }
}
