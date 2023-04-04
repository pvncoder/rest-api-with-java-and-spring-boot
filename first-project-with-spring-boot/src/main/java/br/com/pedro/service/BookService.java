/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.service;

import br.com.pedro.v1.controller.BookController;
import br.com.pedro.exception.RequiredObjectIsNullException;
import br.com.pedro.exception.ResourceNotFoundException;
import br.com.pedro.mapper.DozerMapper;
import br.com.pedro.model.entity.Book;
import br.com.pedro.model.repository.BookRepository;
import br.com.pedro.model.v1.dto.BookDTO;
import java.util.List;
import static java.util.Objects.isNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@Service // Annotation to warn Spring which is an injectable class
public class BookService {

    private final Logger LOGGER = LogManager.getLogger(BookService.class);
    
    @Autowired
    private BookRepository bookRepository;
    
    // General
    public BookDTO create(BookDTO bookDTO) {
        LOGGER.info("Creating one book");
        if (isNull(bookDTO)) {
            throw new RequiredObjectIsNullException();
        }
        Book bookToSave = DozerMapper.parseObject(bookDTO, Book.class);
        Book savedBook = bookRepository.save(bookToSave);
        BookDTO bookDTOSaved = DozerMapper.parseObject(savedBook, BookDTO.class);
        addCreateIdHATEOS(bookDTOSaved);
        return bookDTOSaved;
    }
    
    public BookDTO update(BookDTO bookDTO) {
        LOGGER.info("Updating one book");
        if (isNull(bookDTO)) {
            throw new RequiredObjectIsNullException();
        }
        Book bookEntity = bookRepository.findById(bookDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        bookEntity.updateValues(bookDTO);
        Book savedBook = bookRepository.save(bookEntity);
        BookDTO bookDTOUpdated = DozerMapper.parseObject(savedBook, BookDTO.class);
        addUpdateHATEOS(bookDTOUpdated);
        return bookDTOUpdated;
    }
    
    public void delete(Long idBook) {
        LOGGER.info("Deleting one book");
        Book bookEntity = bookRepository.findById(idBook).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        bookRepository.delete(bookEntity);
    }
    
    public BookDTO findById(Long idBook) {
        LOGGER.info("Finding one book");
        BookDTO bookDTO = DozerMapper.parseObject(bookRepository.findById(idBook)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID")), BookDTO.class);
        addFindByIdHATEOS(idBook, bookDTO);
        return bookDTO;
    }
    
    public List<BookDTO> findAll() {
        LOGGER.info("Finding all books");
        List<BookDTO> booksDTO = DozerMapper.parseListObjects(bookRepository.findAll(), BookDTO.class);
        addAllHATEOS(booksDTO);
        return booksDTO;
    }
    
    // Links
    private void addUpdateHATEOS(BookDTO bookDTO) {
        addFindByIdHATEOS(bookDTO.getId(), bookDTO);
    }
    
    private void addCreateIdHATEOS(BookDTO bookDTO) {
        addFindByIdHATEOS(bookDTO.getId(), bookDTO);
    }
    
    private void addAllHATEOS(List<BookDTO> booksDTO) {
        if (CollectionUtils.isNotEmpty(booksDTO)) {
            booksDTO.forEach(bookDTO -> addFindByIdHATEOS(bookDTO.getId(), bookDTO));
        }
    }
    
    private void addFindByIdHATEOS(Long id, BookDTO bookDTO) {
        bookDTO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
    }
}
