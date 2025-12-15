package com.example.integradora.service;

import com.example.integradora.model.*;
import com.example.integradora.structures.*;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    private SinglyLinkedList<Book> books = new SinglyLinkedList<>();
    private SinglyLinkedList<User> users = new SinglyLinkedList<>();
    private SinglyLinkedList<Loan> loans = new SinglyLinkedList<>();

    private HistoryService historyService = new HistoryService();

    private int bookIdCounter = 1;
    private int userIdCounter = 1;
    private int loanIdCounter = 1;

    public LibraryService() {

        books.add(new Book(bookIdCounter++, "El nombre del viento", "Patrick Rothfuss", "Fantasía", 3));
        books.add(new Book(bookIdCounter++, "Cien años de soledad", "Gabriel García Márquez", "Novela", 2));
        books.add(new Book(bookIdCounter++, "1984", "George Orwell", "Distopía", 4));
        books.add(new Book(bookIdCounter++, "El principito", "Antoine de Saint-Exupéry", "Clásico", 1));
        books.add(new Book(bookIdCounter++, "Don Quijote de la Mancha", "Miguel de Cervantes", "Clásico", 5));

        users.add(new User(userIdCounter++, "Juan Pérez", "juan@email.com"));
        users.add(new User(userIdCounter++, "María López", "maria@email.com"));
    }

    public Book addBook(Book book) {
        book.setId(bookIdCounter++);
        book.setAvailableCopies(book.getTotalCopies());
        book.setActive(true);
        book.setWaitlist(new ArrayQueue<>(10));
        books.add(book);
        return book;
    }

    public Object getAllBooks() {
        Object[] result = new Object[books.size()];
        Node<Book> current = books.getHead();
        int i = 0;

        while (current != null) {
            result[i++] = current.data;
            current = current.next;
        }
        return result;
    }

    public Book getBookById(int id) {
        Node<Book> current = books.getHead();
        while (current != null) {
            if (current.data.getId() == id) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public String changeBookStatus(int id) {
        Book book = getBookById(id);
        if (book == null) {
            return "Libro no encontrado";
        }
        book.setActive(!book.isActive());
        return "Estado del libro actualizado";
    }

    public User addUser(User user) {
        user.setId(userIdCounter++);
        users.add(user);
        return user;
    }

    public Object getAllUsers() {
        Object[] result = new Object[users.size()];
        Node<User> current = users.getHead();
        int i = 0;

        while (current != null) {
            result[i++] = current.data;
            current = current.next;
        }
        return result;
    }

    public User getUserById(int id) {
        Node<User> current = users.getHead();
        while (current != null) {
            if (current.data.getId() == id) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }
    public String createLoan(int userId, int bookId) {

        User user = getUserById(userId);
        Book book = getBookById(bookId);

        if (user == null || book == null) {
            return "Usuario o libro no encontrado";
        }

        if (!book.isActive()) {
            return "El libro está desactivado";
        }

        if (book.getAvailableCopies() > 0) {

            Loan loan = new Loan(loanIdCounter++, userId, bookId, "HOY");
            loans.add(loan);

            int prev = book.getAvailableCopies();
            book.setAvailableCopies(prev - 1);

            HistoryAction h = new HistoryAction();
            h.setActionType("CREATE_LOAN");
            h.setUserId(userId);
            h.setBookId(bookId);
            h.setLoanId(loan.getId());
            h.setPreviousAvailableCopies(prev);

            historyService.push(h);
            return "Préstamo creado correctamente";
        }

        book.getWaitlist().offer(userId);

        HistoryAction h = new HistoryAction();
        h.setActionType("ADD_TO_WAITLIST");
        h.setUserId(userId);
        h.setBookId(bookId);
        historyService.push(h);

        return "No hay copias disponibles. Usuario agregado a lista de espera";
    }

    public String returnLoan(int loanId) {

        Loan loan = findLoanById(loanId);
        if (loan == null) return "Préstamo no encontrado";
        if (loan.getStatus().equals("RETURNED")) return "Ya fue devuelto";

        loan.setStatus("RETURNED");
        Book book = getBookById(loan.getBookId());

        if (!book.getWaitlist().isEmpty()) {
            int nextUserId = book.getWaitlist().poll();
            loans.add(new Loan(loanIdCounter++, nextUserId, book.getId(), "HOY"));
            return "Libro reasignado a usuario en lista de espera";
        }

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        return "Libro devuelto correctamente";
    }


    public ArrayQueue<Integer> getWaitlistByBook(int bookId) {
        Book book = getBookById(bookId);
        return book == null ? null : book.getWaitlist();
    }

    public String cancelReservation(int userId, int bookId) {

        Book book = getBookById(bookId);
        if (book == null) return "Libro no encontrado";

        ArrayQueue<Integer> temp = new ArrayQueue<>(10);

        while (!book.getWaitlist().isEmpty()) {
            int u = book.getWaitlist().poll();
            if (u != userId) temp.offer(u);
        }

        book.setWaitlist(temp);
        return "Reservación cancelada correctamente";
    }

    public String undoLastAction() {

        if (historyService.isEmpty()) return "No hay acciones";

        HistoryAction h = historyService.pop();

        if (h.getActionType().equals("CREATE_LOAN")) {
            Loan loan = findLoanById(h.getLoanId());
            loans.removeTotalmente(loan);
            Book book = getBookById(h.getBookId());
            book.setAvailableCopies(h.getPreviousAvailableCopies());
            return "Undo de préstamo realizado";
        }

        if (h.getActionType().equals("ADD_TO_WAITLIST")) {
            cancelReservation(h.getUserId(), h.getBookId());
            return "Undo de reserva realizado";
        }

        return "Acción no válida";
    }

    private Loan findLoanById(int id) {
        Node<Loan> current = loans.getHead();
        while (current != null) {
            if (current.data.getId() == id) return current.data;
            current = current.next;
        }
        return null;
    }
}
