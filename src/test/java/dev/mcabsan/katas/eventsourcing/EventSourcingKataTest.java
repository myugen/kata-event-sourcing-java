package dev.mcabsan.katas.eventsourcing;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventSourcingKataTest {
    private final InMemoryAuctionEventsRepository repository = new InMemoryAuctionEventsRepository();

    @Test
    void should_create_an_auction() {
        Auction auction = Auction.create("Mario Bros NES", 10000);

        repository.save(auction);

        assertThat(repository.getById(auction.getId()))
                .usingRecursiveComparison()
                .ignoringFields("changes")
                .isEqualTo(auction);
    }

    @Test
    void should_make_a_bid() {
        // TODO: implement this test
    }
}