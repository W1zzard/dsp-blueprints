package com.azhiltsov.repository.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Item")
@Getter
@Setter
public class Item {
    @Id
    private String title;

    @Property("label")
    private String label;

    @Property("itemType")
    private ItemType itemType;

    @Property("pictureId")
    private String pictureId;

    @Relationship(type = "HAS_RECEIPT", direction = Relationship.Direction.OUTGOING)
    private Set<Receipt> receiptSet = new HashSet<>();
}
