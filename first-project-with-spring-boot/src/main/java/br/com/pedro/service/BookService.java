/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.service;

import br.com.pedro.controller.v1.BookV1Controller;
import br.com.pedro.exception.RequiredObjectIsNullException;
import br.com.pedro.exception.ResourceNotFoundException;
import br.com.pedro.mapper.DozerMapper;
import br.com.pedro.model.entity.Book;
import br.com.pedro.model.repository.BookRepository;
import br.com.pedro.model.dto.v1.BookV1DTO;
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
    public BookV1DTO createV1(BookV1DTO bookV1DTO) {
        LOGGER.info("Creating one book");
        if (isNull(bookV1DTO)) {
            throw new RequiredObjectIsNullException();
        }
        Book bookToSave = DozerMapper.parseObject(bookV1DTO, Book.class);
        Book savedBook = bookRepository.save(bookToSave);
        BookV1DTO bookV1DTOSaved = DozerMapper.parseObject(savedBook, BookV1DTO.class);
        addCreateIdV1HATEOS(bookV1DTOSaved);
        return bookV1DTOSaved;
    }
    
    public BookV1DTO updateV1(BookV1DTO bookV1DTO) {
        LOGGER.info("Updating one book");
        if (isNull(bookV1DTO)) {
            throw new RequiredObjectIsNullException();
        }
        Book bookEntity = bookRepository.findById(bookV1DTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        bookEntity.updateValues(bookV1DTO);
        Book savedBook = bookRepository.save(bookEntity);
        BookV1DTO bookV1DTOUpdated = DozerMapper.parseObject(savedBook, BookV1DTO.class);
        addUpdateV1HATEOS(bookV1DTOUpdated);
        return bookV1DTOUpdated;
    }
    
    public void deleteV1(Long idBook) {
        LOGGER.info("Deleting one book");
        Book bookEntity = bookRepository.findById(idBook).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        bookRepository.delete(bookEntity);
    }
    
    public BookV1DTO findByIdV1(Long idBook) {
        LOGGER.info("Finding one book");
        BookV1DTO bookV1DTO = DozerMapper.parseObject(bookRepository.findById(idBook)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID")), BookV1DTO.class);
        addFindByIdV1HATEOS(idBook, bookV1DTO);
        return bookV1DTO;
    }
    
    public List<BookV1DTO> findAllV1() {
        LOGGER.info("Finding all books");
        List<BookV1DTO> booksV1DTO = DozerMapper.parseListObjects(bookRepository.findAll(), BookV1DTO.class);
        addAllV1HATEOS(booksV1DTO);
        return booksV1DTO;
    }
    
    // Links
    private void addUpdateV1HATEOS(BookV1DTO bookV1DTO) {
        addFindByIdV1HATEOS(bookV1DTO.getId(), bookV1DTO);
    }
    
    private void addCreateIdV1HATEOS(BookV1DTO bookV1DTO) {
        addFindByIdV1HATEOS(bookV1DTO.getId(), bookV1DTO);
    }
    
    private void addAllV1HATEOS(List<BookV1DTO> booksV1DTO) {
        if (CollectionUtils.isNotEmpty(booksV1DTO)) {
            booksV1DTO.forEach(bookV1DTO -> addFindByIdV1HATEOS(bookV1DTO.getId(), bookV1DTO));
        }
    }
    
    private void addFindByIdV1HATEOS(Long id, BookV1DTO bookV1DTO) {
        bookV1DTO.add(linkTo(methodOn(BookV1Controller.class).findById(id)).withSelfRel());
    }
}
