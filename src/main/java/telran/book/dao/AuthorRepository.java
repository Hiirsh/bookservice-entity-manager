package telran.book.dao;

import java.util.Optional;

import telran.book.model.Author;

public interface AuthorRepository{
  
  Optional<Author> findById(String authorName);

  Author save(Author author);

  void delete(Author author);
}
