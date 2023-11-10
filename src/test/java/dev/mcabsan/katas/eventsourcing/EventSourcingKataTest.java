package dev.mcabsan.katas.eventsourcing;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventSourcingKataTest {
    private final InMemoryAuctionEventsRepository repository = new InMemoryAuctionEventsRepository();

    @Test
    void should_create_an_auction() {
        Auction auction = Auction.create("Mario Bros NES", 10000);

        repository.save(auction);
        Auction actual = repository.getById(auction.getId());

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("changes")
                .isEqualTo(auction);
    }

    @Test
    void should_make_a_bid() {
        Auction auction = Auction.create("Mario Bros NES", 10000);

        auction.makeBid(20000);
        auction.makeBid(30000);

        repository.save(auction);
        Auction actual = repository.getById(auction.getId());

        assertThat(actual.getCurrentBid()).isEqualTo(30000);
    }

    @Test
    void should_close_an_auction() {
        Auction auction = Auction.create("Mario Bros NES", 10000);

        auction.makeBid(20000);
        auction.makeBid(30000);

        auction.close();

        repository.save(auction);
        Auction actual = repository.getById(auction.getId());

        assertThat(actual.isClosed()).isTrue();
    }
}