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
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This is the BookParser class that parse input data into the Book data that
 * students can use.
 * learn how Scanner instances that are connected to the keyboard work.
 * 
 * @author CS 400 Team
 */
public class BookParser {

    /**
     * This class parse the csv file into a list of book object
     * @param booksfilename - a csv file with book database information
     * @return ArrayList that contains all the Book object data
     * @throws FileNotFoundException
     */
    public static ArrayList<Book> parse(String booksfilename) throws FileNotFoundException{
        ArrayList<Book> bookList = new ArrayList<Book>(); 
        try {
            Scanner scanner = new Scanner(new File(booksfilename));

            Scanner valueScanner = null;
            int idx = 0; 

            // try different methods of the Scanner STDIN
            while ( scanner.hasNext() ) {
                valueScanner = new  Scanner(scanner.nextLine());
                valueScanner.useDelimiter(","); 
                ArrayList<String> book = new ArrayList<String>(); 
                while (valueScanner.hasNext()) {
                    String data = valueScanner.next();
                    book.add(data); 
                } 

                Book bookobj = new Book(book.get(0),book.get(1),book.get(2),
                        book.get(3),book.get(4),book.get(5),book.get(6), book.get(7)); 
                //System.out.println(bookobj.toString()); 
                bookList.add(bookobj);
            }
            bookList.remove(0);
            scanner.close();

        }catch(FileNotFoundException e) {
        }
        return bookList;
    }

}
