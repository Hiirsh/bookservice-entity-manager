package telran.book.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
// import javax.transaction.Transactional;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import telran.book.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {
  @PersistenceContext
  EntityManager em;

  @Override
  public boolean existsById(String isbn) {
    return em.find(Book.class, isbn) != null;
  }

  @Override
  public Optional<Book> findById(String isbn) {
    return Optional.ofNullable(em.find(Book.class, isbn));
  }

  @Override
  @Transactional //опционально
  public Book save(Book book) {
    em.persist(book);
    // em.merge(book);
    return book;
  }

  @Override
  @Transactional
  public Book deleteById(String isbn) {
    Book book = em.find(Book.class, isbn);
    em.remove(book);
    return book;
  }

}
