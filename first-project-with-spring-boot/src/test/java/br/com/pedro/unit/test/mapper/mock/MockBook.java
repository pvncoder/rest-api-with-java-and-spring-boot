package br.com.pedro.unit.test.mapper.mock;

import java.util.ArrayList;
import java.util.List;

import br.com.pedro.model.entity.Book;
import br.com.pedro.model.dto.v1.BookV1DTO;
import java.math.BigDecimal;
import java.util.Date;

public class MockBook {

    // General
    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookV1DTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 5; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookV1DTO> mockDTOList() {
        List<BookV1DTO> books = new ArrayList<>();
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

    public BookV1DTO mockDTO(Integer number) {
        BookV1DTO book = new BookV1DTO();
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date());
        book.setPrice(new BigDecimal(number));
        return book;
    }

}
