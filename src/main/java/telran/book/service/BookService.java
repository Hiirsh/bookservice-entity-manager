package telran.book.service;

import telran.book.dto.AuthorDto;
import telran.book.dto.BookDto;

public interface BookService {

  boolean addBook(BookDto book);

  BookDto findBookByIsbn(String isbn);

  BookDto removeBookByIsbn(String isbn);

  BookDto updateBookByIsbn(String isbn, String title);

  Iterable<BookDto> findBooksByAuthor(String authorName);

  Iterable<BookDto> findBooksByPublisher(String publisherName);

  Iterable<AuthorDto> findBookAuthors(String isbn);

  Iterable<String> findPublishersByAuthor(String authorName);

  AuthorDto removeAuthor(String authorName);
}
