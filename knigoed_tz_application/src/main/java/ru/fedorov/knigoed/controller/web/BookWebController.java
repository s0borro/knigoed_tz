package ru.fedorov.knigoed.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fedorov.knigoed.dto.BookDto;
import ru.fedorov.knigoed.exception.BookNotFoundException;
import ru.fedorov.knigoed.service.BookService;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookWebController {

    private static final String BOOKS_FORM_PATH = "books/form";

    private final BookService bookService;

    @GetMapping
    public String listBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<BookDto> bookPage = bookService.findAllFiltered(
                title, brand, year, PageRequest.of(page, size));

        model.addAttribute("books", bookPage);
        model.addAttribute("titleFilter", title);
        model.addAttribute("brandFilter", brand);
        model.addAttribute("yearFilter", year);

        return BOOKS_FORM_PATH;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new BookDto(null, "", "", null, "", null, null));
        return BOOKS_FORM_PATH;
    }

    @PostMapping
    public String saveBook(@Valid @ModelAttribute("book") BookDto bookDto,
                           BindingResult result) {
        if (result.hasErrors()) {
            return BOOKS_FORM_PATH;
        }
        bookService.save(bookDto);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BookDto bookDto = bookService.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        model.addAttribute("book", bookDto);
        return "books/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }
}