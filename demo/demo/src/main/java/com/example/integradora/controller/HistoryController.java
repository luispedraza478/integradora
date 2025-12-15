package com.example.integradora.controller;

import com.example.integradora.service.LibraryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final LibraryService libraryService;

    public HistoryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/undo")
    public String undo() {
        return libraryService.undoLastAction();
    }
}
