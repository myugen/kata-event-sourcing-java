package dev.mcabsan.katas.eventsourcing;

import java.time.Instant;

public sealed interface BaseEvent {
    String id();
    Instant occurredAt();
}

record AuctionCreated(String id, Instant occurredAt, String itemDescription, int initialPrice) implements BaseEvent {}