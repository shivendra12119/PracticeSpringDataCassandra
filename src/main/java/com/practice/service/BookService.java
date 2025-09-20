package com.practice.service;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.practice.entity.Book;
import com.practice.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.generator.CqlGenerator;
import org.springframework.data.cassandra.core.cql.keyspace.CreateTableSpecification;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CassandraOperations cassandraOperations;

    public void createTable() {
        CreateTableSpecification createTable = CreateTableSpecification.createTable("book")
                .partitionKeyColumn("id", DataTypes.TEXT)
                .partitionKeyColumn("title", DataTypes.TEXT)
                .partitionKeyColumn("publisher", DataTypes.TEXT)
                .column("tags", DataTypes.setOf(DataTypes.TEXT));

        String cql = CqlGenerator.toCql(createTable);
        cassandraOperations.execute(SimpleStatement.builder(cql).build());
    }

    public void insert(){
        for(int j =0;j<100;j++){
            CompletableFuture.runAsync(
                    () -> {
                        for(int i=0;i<1000;i++) {
                            bookRepository.save(Book.builder().id(UUID.randomUUID().toString()).title("Shivendra" + i ).publisher("Publisherr" + i).build());
                        }
                    }
            );
        }
    }

    public Book getById(String id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

}
