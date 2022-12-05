package telran.book.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "name")
@Entity
public class Author implements Serializable {
  @Id
  String name;
  LocalDate birthDate;

  public Author(String name, LocalDate birthDate) {
    this.name = name;
    this.birthDate = birthDate;
  }

  @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
  Set<Book> books;
}
