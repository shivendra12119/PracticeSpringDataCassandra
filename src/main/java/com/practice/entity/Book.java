package com.practice.entity;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("book_dummy")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Column
    private String id;
    @Column
    private String title;
    @PrimaryKeyColumn(name = "publisher", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String publisher;
    @Column
    private Set<String> tags = new HashSet<>();
}
