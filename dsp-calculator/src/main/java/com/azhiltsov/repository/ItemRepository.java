package com.azhiltsov.repository;

import com.azhiltsov.repository.model.Item;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ItemRepository extends Neo4jRepository<Item, String> {

    Optional<Item> findOneByTitle(String title);
}
