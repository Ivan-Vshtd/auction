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
 * @since 11/20/2018
 */

@Service
public class BetService {

  @Autowired
  BetRepository betRepository;

  @Autowired
  AuctionRepository auctionRepository;

  public Flux<Bet> findAll() {
    return betRepository.findAll();
  }

  public Mono<Bet> findById(String id) {
    return betRepository.findById(id);
  }

  public Mono<Bet> addBet(Bet bet) {
    bet.setViewId(String.valueOf(lastViewId(bet.getAuctionId()) + 1));
    auctionRepository
      .findById(bet.getAuctionId())
      .subscribe(au -> {
        au.setLastBet(bet.getText());
        auctionRepository.save(au).subscribe();
      });

    return betRepository.save(bet);
  }

  public Mono<Bet> editBet(String id, Bet bet) {
    bet.setId(id);
    return betRepository.save(bet);
  }

  public Mono<Bet> delete(String id) {
    Mono<Bet> bet = findById(id);
    if (bet != null){
      betRepository.delete(bet.block()).subscribe();
    }
    return bet;
  }

  public Flux<Bet> findAllRequiredBets(String auId) {
    return betRepository.findAllByAuctionId(auId);
  }

  private Long lastViewId(String id){
    return findAllRequiredBets(id)
                               .toStream()
                               .map(Bet::getViewId)
                               .map(Long::valueOf)
                               .max(Long::compareTo)
                               .orElse(0L);
  }
}
