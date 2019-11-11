package com.search.project.backend.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDocument {

    @Id
    private String bookID;
    private String title;
    private String authors;
    private float average_rating;
    private long isbn;
    private long isbn13;
    private String language_code;
    private int num_of_pages;
    private int ratings_count;
    private int text_reviews_count;


}
