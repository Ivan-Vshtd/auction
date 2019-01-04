package custom.userportal.service;

import custom.userportal.domain.Auction;
import custom.userportal.repo.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author iveshtard
 * @since 1/4/2019
 */

@Service
public class AuctionService {

  @Autowired
  AuctionRepository auctionRepository;

  public Flux<Auction> findAll()
  {
    return auctionRepository.findAll();
  }

  public Mono<Auction> findById(String id) {
    return auctionRepository.findById(id);
  }

  public Mono<Auction> addAuction(Auction auction) {
    long lastViewId = auctionRepository                        //just to generate valid numbers of auctions
                                    .findAll()
                                    .toStream()
                                    .map(Auction::getViewId)
                                    .map(Long::valueOf)
                                    .max(Long::compareTo)
                                    .orElse(0L);

    auction.setViewId(String.valueOf(lastViewId + 1));

    return auctionRepository.save(auction);
  }

  public Mono<Auction> editBet(String id, Auction auction) {
    auction.setId(id);
    return auctionRepository.save(auction);
  }

  public Mono<Auction> delete(String id) {
    Mono<Auction> auction = findById(id);
    if (auction != null){
      auctionRepository.delete(auction.block()).subscribe();
    }
    return auction;
  }

}
