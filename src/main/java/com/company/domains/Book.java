package com.company.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private LocalDate publishedAt;
    private Integer pages;
    private Integer documentId;
    private Integer categoryId;
    private Integer coverId;
    private Integer downloads;
    private Integer views;
    private Integer likes;
    private Integer dislikes;
    private boolean deleted;
}
