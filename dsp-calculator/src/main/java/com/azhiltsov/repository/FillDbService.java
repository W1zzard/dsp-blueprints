package com.azhiltsov.repository;

import com.azhiltsov.repository.model.Item;
import com.azhiltsov.repository.model.ItemType;
import com.azhiltsov.repository.model.NeedItem;
import com.azhiltsov.repository.model.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FillDbService {
    private final ItemRepository itemRepository;

    private Map<String, Item> itemMap = new HashMap<>();

    @PostConstruct
    public void initItemRepo() {
        /*if (!itemRepository.findAll().isEmpty()) {
            log.warn("items already exist no fill");
            return;
        }*/

        fillSet();
        fillLinks();
        log.info("Set with items filled: {}", itemMap.size());

        itemRepository.saveAll(itemMap.values());
    }

    private void fillLinks() {
        final Item ironSheet = itemMap.get("ironSheet");

        final Receipt receipt = new Receipt();
        receipt.setPrimary(true);
        receipt.setRunTime(1);
        receipt.setProductionPerRun(1);
        final NeedItem needItem = new NeedItem();
        needItem.setAmount(1);
        needItem.setItem(itemMap.get("ironOre"));
        receipt.getNeedItemSet().add(needItem);
        ironSheet.getReceiptSet().add(receipt);
    }

    private void fillSet() {
        addItem("copperSheet", "Copper sheet", ItemType.ITEM);
        addItem("copperOre", "Copper ore", ItemType.ITEM);
        addItem("ironSheet", "Iron sheet", ItemType.ITEM);
        addItem("ironOre", "Iron ore", ItemType.ITEM);
        addItem("mRing", "Magnetic ring", ItemType.ITEM);
    }

    private void addItem(final String title, final String label, final ItemType itemType) {
        final Item item = new Item();
        item.setTitle(title);
        item.setLabel(label);
        item.setItemType(itemType);
        itemMap.put(title, item);
    }
}
