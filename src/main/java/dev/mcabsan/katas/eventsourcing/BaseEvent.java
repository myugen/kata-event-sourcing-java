package dev.mcabsan.katas.eventsourcing;

import java.time.Instant;

public sealed interface BaseEvent {
    String id();
    Instant occurredAt();
}

sealed interface DomainEvent extends BaseEvent {}
sealed interface DeprecatedEvent extends BaseEvent {
    DomainEvent toLatestVersion();
}

record AuctionCreated(String id, Instant occurredAt, String itemDescription, int initialPrice) implements DomainEvent {}
record AuctionNewBid(String id, Instant occurredAt, int amount) implements DeprecatedEvent {
    @Override
    public DomainEvent toLatestVersion() {
        return new AuctionNewBidV2(id, occurredAt, amount, "unknown");
    }
}
record AuctionNewBidV2(String id, Instant occurredAt, int amount, String bidder) implements DomainEvent {}
record AuctionClosed(String id, Instant occurredAt) implements DomainEvent {}