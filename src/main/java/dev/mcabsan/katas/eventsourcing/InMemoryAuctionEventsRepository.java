package dev.mcabsan.katas.eventsourcing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAuctionEventsRepository {
    private final Map<String, List<BaseEvent>> eventsStore = new HashMap<>();

    public void save(Auction auction) {
        eventsStore.put(auction.getId(), auction.getChanges());
    }

    public Auction getById(String id) {
        return Auction.loadFromHistory(aggregateEvents(id));
    }

    private List<DomainEvent> aggregateEvents(String id) {
        List<BaseEvent> events = eventsStore.get(id);
        return events.stream()
                .map(event -> switch (event) {
                    case DeprecatedEvent deprecatedEvent -> deprecatedEvent.toLatestVersion();
                    case DomainEvent domainEvent -> domainEvent;
                }).toList();
    }
}
