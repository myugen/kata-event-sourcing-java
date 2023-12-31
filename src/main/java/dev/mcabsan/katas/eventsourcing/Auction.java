package dev.mcabsan.katas.eventsourcing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Auction {
    private String id;
    private String itemDescription;
    private int initialPrice;

    private final List<BaseEvent> changes = new ArrayList<>();

    private Auction(String id, String itemDescription, int initialPrice) {
        this.id = id;
        this.itemDescription = itemDescription;
        this.initialPrice = initialPrice;
    }

    public String getId() {
        return id;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public List<BaseEvent> getChanges() {
        return changes;
    }

    private void apply(BaseEvent event) {
        switch (event) {
            case AuctionCreated auctionCreated -> apply(auctionCreated);
        }
    }

    private void apply(AuctionCreated event) {
        this.id = event.id();
        this.itemDescription = event.itemDescription();
        this.initialPrice = event.initialPrice();
    }

    private static Auction empty() {
        return new Auction("", "", 0);
    }

    public static Auction loadFromHistory(List<BaseEvent> events) {
        Auction auction = empty();
        events.forEach(auction::apply);
        return auction;
    }

    public static Auction create(String itemDescription, int initialPrice) {
        String id = UUID.randomUUID().toString();
        Instant eventOccurredAt = Instant.now();
        Auction auction = new Auction(id, itemDescription, initialPrice);
        AuctionCreated auctionCreated = new AuctionCreated(id, eventOccurredAt, itemDescription, initialPrice);
        auction.changes.add(auctionCreated);
        return auction;
    }
}
