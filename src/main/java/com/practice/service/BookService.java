package com.practice.service;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.practice.entity.Book;
import com.practice.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.generator.CqlGenerator;
import org.springframework.data.cassandra.core.cql.keyspace.CreateTableSpecification;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CassandraOperations cassandraOperations;

    public void createTable() {
        CreateTableSpecification createTable = CreateTableSpecification.createTable("book_dummy")
                .column("id", DataTypes.TEXT)
                .column("title", DataTypes.TEXT)
                .partitionKeyColumn("publisher", DataTypes.TEXT)
                .column("tags", DataTypes.setOf(DataTypes.TEXT));

        String cql = CqlGenerator.toCql(createTable);
        cassandraOperations.execute(SimpleStatement.builder(cql).build());
    }

    public void insert(){
        List<CompletableFuture<Void>> futures = new LinkedList<>();
        for(int j =0;j<100;j++){
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        for(int i=0;i<1000;i++) {
                            bookRepository.save(Book.builder().id(UUID.randomUUID().toString()).title("Shivendra" + i ).publisher("Publisherr" + getRandomInt()).build());
                        }
                    }
            );
            futures.add(future);
        }
        for(CompletableFuture<Void> future : futures) {
            try {
                future.get();
                System.out.println("Completed a batch");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getRandomInt() {
        return (int) (Math.random() * (99999999 - 0)) + 0;
    }

    public Book findByPublisher(String id){
        return bookRepository.findByPublisher(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

}
