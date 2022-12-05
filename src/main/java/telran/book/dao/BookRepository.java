package telran.book.dao;

import java.util.Optional;

import telran.book.model.Book;

public interface BookRepository {

  boolean existsById(String isbn);

  Optional<Book> findById(String isbn);

  Book save(Book book);

	Book deleteById(String isbn);

}
