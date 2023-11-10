package dev.mcabsan.katas.eventsourcing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Auction {
    private String id;
    private String itemDescription;
    private int initialPrice;
    private int currentBid = 0;
    private String currentBidder = "";
    private boolean closed = false;
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

    public int getCurrentBid() {
        return currentBid;
    }

    public String getCurrentBidder() {
        return currentBidder;
    }

    public boolean isClosed() {
        return closed;
    }

    public List<BaseEvent> getChanges() {
        return changes;
    }

    public void makeBid(int amount, String bidder) {
        AuctionNewBidV2 auctionNewBid = new AuctionNewBidV2(id, Instant.now(), amount, bidder);
        changes.add(auctionNewBid);
        applyEvent(auctionNewBid);
    }

    public void close() {
        AuctionClosed auctionClosed = new AuctionClosed(id, Instant.now());
        changes.add(auctionClosed);
        apply(auctionClosed);
    }

    private void applyEvent(DomainEvent event) {
        switch (event) {
            case AuctionCreated auctionCreated -> apply(auctionCreated);
            case AuctionClosed auctionClosed -> apply(auctionClosed);
            case AuctionNewBidV2 auctionNewBidV2 -> apply(auctionNewBidV2);
        }
    }

    private void apply(AuctionCreated event) {
        this.id = event.id();
        this.itemDescription = event.itemDescription();
        this.initialPrice = event.initialPrice();
    }

    private void apply(AuctionNewBidV2 event) {
        this.currentBid = event.amount();
        this.currentBidder = event.bidder();
    }

    private void apply(AuctionClosed event) {
        this.closed = true;
    }

    private static Auction empty() {
        return new Auction("", "", 0);
    }

    public static Auction loadFromHistory(List<DomainEvent> events) {
        Auction auction = empty();
        events.forEach(auction::applyEvent);
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
