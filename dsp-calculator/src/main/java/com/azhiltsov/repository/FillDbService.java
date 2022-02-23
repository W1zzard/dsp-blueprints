package com.azhiltsov.repository;

import com.azhiltsov.repository.model.Item;
import com.azhiltsov.repository.model.ItemType;
import com.azhiltsov.repository.model.NeedItem;
import com.azhiltsov.repository.model.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FillDbService {
    private final ItemRepository itemRepository;
    private final Neo4jClient neo4jClient;

    private Map<String, Item> itemMap = new HashMap<>();


    @PostConstruct
    public void initItemRepo() {
        /*if (!itemRepository.findAll().isEmpty()) {
            log.warn("items already exist no fill");
            return;
        }*/

        neo4jClient.query("match (a) -[r] -> () delete a, r").run();
        neo4jClient.query("match (a) delete a").run();

        fillSet();
        fillLinks();
        log.info("Set with items filled: {}", itemMap.size());

        itemRepository.saveAll(itemMap.values());

    }

    private void fillLinks() {
        addReceipt(
                itemMap.get("ironIngot"), new Receipt(true, 1, 1),
                new NeedItem(itemMap.get("ironOre"), 1)
        );
        addReceipt(
                itemMap.get("copperIngot"), new Receipt(true, 1, 1),
                new NeedItem(itemMap.get("copperOre"), 1)
        );
        addReceipt(
                itemMap.get("energeticGraphite"), new Receipt(true, 1, 2),
                new NeedItem(itemMap.get("coal"), 2)
        );
        addReceipt(
                itemMap.get("siliconIngot"), new Receipt(true, 1, 2),
                new NeedItem(itemMap.get("siliconOre"), 2)
        );
        addReceipt(
                itemMap.get("glass"), new Receipt(true, 1, 2),
                new NeedItem(itemMap.get("stone"), 2)
        );
        addReceipt(
                itemMap.get("refinedOil"), new Receipt(true, 2, 4),
                new NeedItem(itemMap.get("crudeOil"), 2)
        );
        addReceipt(
                itemMap.get("titaniumIngot"), new Receipt(true, 1, 2),
                new NeedItem(itemMap.get("titaniumOre"), 2)
        );
        //todo check time
        addReceipt(
                itemMap.get("deuterium"), new Receipt(true, 5, 3),
                new NeedItem(itemMap.get("hydrogen"), 10)
        );
        addReceipt(
                itemMap.get("microcrystallineComponent"), new Receipt(true, 1, 2),
                new NeedItem(itemMap.get("siliconIngot"), 2), new NeedItem(itemMap.get("copperIngot"), 1)
        );
        addReceipt(
                itemMap.get("circuitBoard"), new Receipt(true, 2, 1),
                new NeedItem(itemMap.get("ironIngot"), 2), new NeedItem(itemMap.get("copperIngot"), 1)
        );
        addReceipt(
                itemMap.get("processor"), new Receipt(true, 1, 4),
                new NeedItem(itemMap.get("circuitBoard"), 2), new NeedItem(itemMap.get("microcrystallineComponent"), 2)
        );
        addReceipt(
                itemMap.get("prism"), new Receipt(true, 2, 2),
                new NeedItem(itemMap.get("glass"), 3)
        );

        addReceipt(
                itemMap.get("photonCombiner"), new Receipt(true, 1, 3),
                new NeedItem(itemMap.get("prism"), 2), new NeedItem(itemMap.get("circuitBoard"), 1)
        );
        addReceipt(
                itemMap.get("steel"), new Receipt(true, 1, 3),
                new NeedItem(itemMap.get("ironIngot"), 3)
        );
        addReceipt(
                itemMap.get("acid"), new Receipt(true, 4, 6),
                new NeedItem(itemMap.get("refinedOil"), 6), new NeedItem(itemMap.get("stone"), 8), new NeedItem(itemMap.get("water"), 4)
        );
        addReceipt(
                itemMap.get("graphene"), new Receipt(true, 2, 3),
                new NeedItem(itemMap.get("energeticGraphite"), 3), new NeedItem(itemMap.get("acid"), 1)
        );
        addReceipt(
                itemMap.get("solarSail"), new Receipt(true, 2, 4),
                new NeedItem(itemMap.get("graphene"), 1), new NeedItem(itemMap.get("photonCombiner"), 1)
        );
    }

    private void fillSet() {
        addItem("copperOre", "Copper ore", ItemType.ITEM);
        addItem("ironOre", "Iron ore", ItemType.ITEM);
        addItem("siliconOre", "Silicon ore", ItemType.ITEM);
        addItem("stone", "Stone", ItemType.ITEM);
        addItem("coal", "Coal", ItemType.ITEM);
        addItem("crudeOil", "Crude oil", ItemType.ITEM);
        addItem("water", "Water", ItemType.ITEM);
        addItem("titaniumOre", "Titanium ore", ItemType.ITEM);
        addItem("hydrogen", "Hydrogen", ItemType.ITEM);

        addItem("ironIngot", "Iron ingot", ItemType.ITEM);
        addItem("copperIngot", "Copper ingot", ItemType.ITEM);
        addItem("energeticGraphite", "Energetic graphite", ItemType.ITEM);
        addItem("siliconIngot", "Silicon ingot", ItemType.ITEM);
        addItem("glass", "Glass", ItemType.ITEM);
        addItem("refinedOil", "Refined oil", ItemType.ITEM);
        addItem("titaniumIngot", "Titanium ingot", ItemType.ITEM);
        addItem("deuterium", "Deuterium", ItemType.ITEM);
        addItem("microcrystallineComponent", "Microcrystalline component", ItemType.ITEM);
        addItem("circuitBoard", "Circuit board", ItemType.ITEM);
        addItem("processor", "Processor", ItemType.ITEM);
        addItem("prism", "Prism", ItemType.ITEM);

        addItem("photonCombiner", "Photon combiner", ItemType.ITEM);
        addItem("steel", "Steel", ItemType.ITEM);
        addItem("acid", "Acid", ItemType.ITEM);
        addItem("graphene", "Graphene", ItemType.ITEM);
        addItem("solarSail", "Solar sail", ItemType.ITEM);




    }

    private void addItem(final String title, final String label, final ItemType itemType) {
        final Item item = new Item();
        item.setTitle(title);
        item.setLabel(label);
        item.setItemType(itemType);
        itemMap.put(title, item);
    }

    private void addReceipt(final Item item, final Receipt receipt, final NeedItem... needItems) {
        Arrays.stream(needItems).forEach(receipt.getNeedItemSet()::add);
        item.getReceiptSet().add(receipt);
    }
}
