package br.com.pedro.unit.test.mapper.mock;

import java.util.ArrayList;
import java.util.List;

import br.com.pedro.model.entity.Book;
import br.com.pedro.model.v1.dto.BookDTO;
import java.math.BigDecimal;
import java.util.Date;

public class MockBook {

    // General
    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 5; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date());
        book.setPrice(new BigDecimal(number));
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date());
        book.setPrice(new BigDecimal(number));
        return book;
    }

}
