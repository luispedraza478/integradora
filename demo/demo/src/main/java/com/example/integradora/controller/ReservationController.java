package com.example.integradora.controller;

import com.example.integradora.service.LibraryService;
import com.example.integradora.structures.ArrayQueue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final LibraryService libraryService;

    public ReservationController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/book/{bookId}")
    public ArrayQueue<Integer> getWaitlistByBook(@PathVariable int bookId) {
        return libraryService.getWaitlistByBook(bookId);
    }
    @DeleteMapping
    public String cancelReservation(@RequestParam int userId,
                                     @RequestParam int bookId) {
        return libraryService.cancelReservation(userId, bookId);
    }
}
