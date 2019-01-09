package custom.userportal.service;

import custom.userportal.domain.Auction;
import custom.userportal.domain.Bet;
import custom.userportal.repo.AuctionRepository;
import custom.userportal.repo.BetRepository;
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

  @Autowired
  BetService betService;

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

  public Mono<Auction> editAuction(String id, Auction auction) {
    auction.setId(id);
    return auctionRepository.save(auction);
  }

  public Mono<Auction> delete(String id) {
    Mono<Auction> auction = findById(id);
    if (auction != null){
      auction
      .subscribe(requiredAu -> {
        betService
          .findAllRequiredBets(requiredAu.getId())
          .toIterable()
          .forEach(
            bet -> betService.delete(bet.getId()).subscribe());
        auctionRepository.delete(requiredAu).subscribe();
      }); //and delete all relative bets (TODO: needs try to find valid way!!)
    }
    return auction;
  }
}
