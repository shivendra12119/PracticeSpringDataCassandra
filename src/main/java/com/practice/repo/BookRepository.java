package com.practice.repo;

import com.practice.entity.Book;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CassandraRepository<Book, String> {
    public Optional<Book> findByPublisher(String publisher);
}
