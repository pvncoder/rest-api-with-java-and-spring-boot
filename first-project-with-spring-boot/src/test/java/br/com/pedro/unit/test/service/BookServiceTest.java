package br.com.pedro.unit.test.service;

import br.com.pedro.exception.RequiredObjectIsNullException;
import br.com.pedro.model.v1.dto.BookDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pedro.model.entity.Book;
import br.com.pedro.model.repository.BookRepository;
import br.com.pedro.service.BookService;
import br.com.pedro.unit.test.mapper.mock.MockBook;
import java.math.BigDecimal;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private MockBook mockBook;

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUpMocks() throws Exception {
        mockBook = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Book entity = mockBook.mockEntity(1);
        entity.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));

        var result = bookService.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/books/1>;rel=\"self\"]"));
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(new BigDecimal(1), result.getPrice());
    }

    @Test
    void testCreate() {
        Book entity = mockBook.mockEntity(1);
        entity.setId(1L);

        Book persisted = entity;
        persisted.setId(1L);

        BookDTO bookDTO = mockBook.mockDTO(1);
        bookDTO.setId(1L);

        when(bookRepository.save(entity)).thenReturn(persisted);

        var result = bookService.create(bookDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/books/1>;rel=\"self\"]"));
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(new BigDecimal(1), result.getPrice());
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> bookService.create(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate() {
        Book entity = mockBook.mockEntity(1);

        Book persisted = entity;
        persisted.setId(1L);

        BookDTO dto = mockBook.mockDTO(1);
        dto.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(bookRepository.save(entity)).thenReturn(persisted);

        var result = bookService.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/books/1>;rel=\"self\"]"));
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(new BigDecimal(1), result.getPrice());
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> bookService.update(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete() {
        Book entity = mockBook.mockEntity(1);
        entity.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));

        bookService.delete(1L);
    }

    @Test
    void testFindAll() {
        List<Book> list = mockBook.mockEntityList();

        when(bookRepository.findAll()).thenReturn(list);

        var people = bookService.findAll();

        assertNotNull(people);
        assertEquals(5, people.size());
        
        for (int i = 0; i < people.size(); i++) {
            BookDTO bookDTO = people.get(i);
            
            assertNotNull(bookDTO);
            assertNotNull(bookDTO.getId());
            assertNotNull(bookDTO.getLinks());

            String link = "links: [</api/v1/books/".concat(String.valueOf(bookDTO.getId())).concat(">;rel=\"self\"]");
            assertTrue(bookDTO.toString().contains(link));
            assertEquals("Title Test".concat(Integer.toString(i)), bookDTO.getTitle());
            assertEquals("Author Test".concat(Integer.toString(i)), bookDTO.getAuthor());
            assertNotNull(bookDTO.getLaunchDate());
            assertEquals(new BigDecimal(i), bookDTO.getPrice());
        }
    }
}
