//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project name: Book Hash Table
// Files: Book.javaBookHashTable.java, BookHashTableTest.java, BookParser.java
// Course: CS 400 Fall 2019
//
// Author: Ye Ji Kim
// Email: ykim762@wisc.edu
// Lecture number: 001
// Lecturer's Name: Debra Deppeler
//
//////////////////////////// 80 columns wide ///////////////////////////////////
import java.util.Objects;

/**
 * This is the Book class that stores the information of Book object.
 * The Key of the Book will be the isbn13.
 * 
 * @author CS 400 Team
 */
public class Book {
    // information that one Book object should have
    private String isbn13;
    private String authors; 
    private String original_publication_year;
    private String title; 
    private String language_code;
    private String average_rating;
    private String cover_type; 
    private String pages;
    
    /**
     * This is the constructor of Book class.
     * 
     * @param isbn13 - isbn13 of the book
     * @param authors - authors of the book
     * @param original_publication_year - year that the book published
     * @param title - title of the book
     * @param language_code - the code that implies which language it is
     * @param average_rating - average rate of the book
     * @param cover_type - cover type of the book
     * @param pages - number of pages of the book
     */
    public Book(String isbn13, String authors, 
            String original_publication_year, String title,
            String language_code, String average_rating, 
            String cover_type, String pages){
        this.isbn13 = isbn13; 
        this.title = title;
        this.authors = authors; 
        this.original_publication_year = original_publication_year;
        this.language_code = language_code; 
        this.average_rating = average_rating;
        this.cover_type = cover_type; 
        this.pages = pages; 
    }

    /**
     * This is the getter method to get the key of the Book
     * @return isbn13 of the book
     */
    public String getKey() {
        return this.isbn13;
    }
    
    /**
     * This is the setter method to set the key of the Book
     * @param isbn13 - isbn13 to set the key
     */
    public void setKey(String isbn13) {
        this.isbn13 = isbn13;
    }    
    
    /**
     * This is the toString method that helps to print the contains
     * of book as String
     */
    @Override
    public String toString() {
        return "ISBN13: "+this.isbn13+"; Book: "+ 
               this.title+", Author: "+this.authors+
               ", Original Publication Year: "+
               this.original_publication_year+
               ", Language: "+this.language_code+", Average Rating: "+
               this.average_rating+", Cover Type: "+this.cover_type+ 
               ", Pages: "+ this.pages;                
    }
}

