/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.model.dto.v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import org.springframework.hateoas.RepresentationModel;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
public class BookV1DTO extends RepresentationModel<BookV1DTO> implements Serializable {

    // Variables
    private Long id;
    private String title;
    private String author;
    private Date launchDate;
    private BigDecimal price;

    // Constructors
    public BookV1DTO() {
    }

    public BookV1DTO(Long id, String title, String author, Date launchDate, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.launchDate = launchDate;
        this.price = price;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Equals and hashcode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BookV1DTO other = (BookV1DTO) obj;
        return Objects.equals(this.id, other.id);
    }
}
