package com.azhiltsov.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Node("Receipt")
public class Receipt {

    @Id
    @GeneratedValue
    private Long id;

    @Property("primary")
    private boolean primary;

    @Property("productionPerRun")
    private int productionPerRun;

    @Property("runTime")
    private int runTime;

    @Relationship(type = "NEED_ITEM", direction = Relationship.Direction.OUTGOING)
    private Set<NeedItem> needItemSet = new HashSet<>();

    public Receipt() {
    }

    public Receipt(final boolean primary, final int productionPerRun, final int runTime) {
        this.primary = primary;
        this.productionPerRun = productionPerRun;
        this.runTime = runTime;
    }
}
