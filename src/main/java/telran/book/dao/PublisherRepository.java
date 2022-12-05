package telran.book.dao;

import java.util.List;
import java.util.Optional;

import telran.book.model.Publisher;

public interface PublisherRepository{

  Optional<Publisher> findById(String publisher);

  Publisher save(Publisher publisher);

  // @Query("select distinct p.name from Book b join b.authors a join b.publisher p where a.name=?1")
  List<String> findPublishersByAuthor(String authorName);
}
