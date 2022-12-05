package telran.book.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Publisher implements Serializable {

  @Id
  String name;
  @OneToMany(mappedBy = "publisher")
  Set<Book> books;

  public Publisher(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
