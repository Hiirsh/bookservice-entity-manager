package telran.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.book.dao.AuthorRepository;
import telran.book.dao.BookRepository;
import telran.book.dao.PublisherRepository;
import telran.book.dto.AuthorDto;
import telran.book.dto.BookDto;
import telran.book.dto.exeptions.EntityNotFoundException;
import telran.book.model.Author;
import telran.book.model.Book;
import telran.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  final BookRepository bookRepository;
  final AuthorRepository authorRepository;
  final PublisherRepository publisherRepository;
  final ModelMapper modelMapper;

  @Override
  @Transactional
  public boolean addBook(BookDto bookDto) {
    if (bookRepository.existsById(bookDto.getIsbn())) {
      return false;
    }
    Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
        .orElseGet(() -> publisherRepository.save(new Publisher(bookDto.getPublisher())));
    Set<Author> authors = bookDto.getAuthors().stream()
        .map(a -> authorRepository.findById(a.getName())
            .orElseGet(() -> authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
        .collect(Collectors.toSet());
    Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
    bookRepository.save(book);
    return true;
  }

  @Override
  public BookDto findBookByIsbn(String isbn) {
    Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
    return modelMapper.map(book, BookDto.class);
  }

  @Override
  @Transactional
  public BookDto removeBookByIsbn(String isbn) {
    Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
    BookDto bookDto = modelMapper.map(book, BookDto.class);
    bookRepository.deleteById(isbn);
    return bookDto;
  }

  @Override
  @Transactional
  public BookDto updateBookByIsbn(String isbn, String title) {
    Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
    book.setTitle(title);
    bookRepository.save(book);
    return modelMapper.map(book, BookDto.class);
  }

  @Override
  // @Transactional(readOnly = true)
  public Iterable<BookDto> findBooksByAuthor(String authorName) {
    Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);
    return author.getBooks().stream().map(b -> modelMapper.map(b, BookDto.class)).collect(Collectors.toList());

  }

  @Override
  // @Transactional(readOnly = true)
  public Iterable<BookDto> findBooksByPublisher(String publisherName) {
    Publisher publisher = publisherRepository.findById(publisherName).orElseThrow(EntityNotFoundException::new);
    return publisher.getBooks().stream().map(b -> modelMapper.map(b, BookDto.class)).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<AuthorDto> findBookAuthors(String isbn) {
    Book book = bookRepository.findById(isbn).orElse(null);
    return book.getAuthors().stream()
        .map(a -> modelMapper.map(a, AuthorDto.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<String> findPublishersByAuthor(String authorName) {
    // return publisherRepository.findDistinctByBooksAuthorsName(authorName)
    //     .map(Publisher::getName)
    //     .collect(Collectors.toList());
    return publisherRepository.findPublishersByAuthor(authorName);

  }

  @Override
  @Transactional
  public AuthorDto removeAuthor(String authorName) {
    Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);
    authorRepository.delete(author);
    return modelMapper.map(author, AuthorDto.class);
  }

}
