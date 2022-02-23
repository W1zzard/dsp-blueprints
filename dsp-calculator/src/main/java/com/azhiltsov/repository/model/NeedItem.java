package com.azhiltsov.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * Link
 */
@Getter
@Setter
@RelationshipProperties
public class NeedItem {
    @RelationshipId
    private Long id;

    @TargetNode
    private Item item;

    @Property("amount")
    private int amount;

    public NeedItem() {
    }

    public NeedItem(final Item item, final int amount) {
        this.item = item;
        this.amount = amount;
    }
}
