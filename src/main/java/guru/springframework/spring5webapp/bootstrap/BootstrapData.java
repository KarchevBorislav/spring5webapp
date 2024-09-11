package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import guru.springframework.spring5webapp.repositories.AuthorRepository;

import java.util.Set;

@Component
public class BootstrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;


    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {




        System.out.println("Started in Bootstrap");

        Publisher publisher = new Publisher();
        publisher.setName("Siela");
        publisher.setCity("Sofia");
        publisher.setState("BG");

        publisherRepository.save(publisher);

        System.out.println("Publisher Count: " + publisherRepository.count());

        Author vazov = new Author("Ivan", "Vazov");
        Book podIgoto = new Book("123", "Pod Igoto");

        addBookToAuthor(vazov, podIgoto);
        addAuthorToBook(podIgoto, vazov);
        setPublisherToBook(podIgoto, publisher);
        addBookToPublisher(publisher.getBooks(), podIgoto);

        saveFileToRepository(vazov, podIgoto,publisher);

        Author doherty = new Author("Paul", "Doherty");
        Book misterium = new Book("1233", "Misterium");
        addBookToAuthor(doherty, misterium);
        addAuthorToBook(misterium, doherty);
        setPublisherToBook(misterium, publisher);
        addBookToPublisher(publisher.getBooks(), misterium);

        saveFileToRepository(doherty, misterium,publisher);


        System.out.println("Number of Books: " + bookRepository.count());
        System.out.println("Publisher Number of Books: " + publisher.getBooks().size());


    }

    private static void addBookToPublisher(Set<Book> publisher, Book book) {
        publisher.add(book);
    }

    private static void setPublisherToBook(Book book, Publisher publisher) {
        book.setPublisher(publisher);
    }

    private void saveFileToRepository(Author author, Book book,Publisher publisher) {
        authorRepository.save(author);
        bookRepository.save(book);
        publisherRepository.save(publisher);
    }

    private static void addAuthorToBook(Book book, Author author) {
        book.getAuthors().add(author);
    }

    private static void addBookToAuthor(Author author, Book book) {
        addBookToPublisher(author.getBooks(), book);
    }
}
