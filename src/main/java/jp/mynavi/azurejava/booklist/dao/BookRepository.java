package jp.mynavi.azurejava.booklist.dao;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import jp.mynavi.azurejava.booklist.model.Book;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveCosmosRepository<Book, String> {
    Flux<Book> findByTitleContaining(String title);
}
