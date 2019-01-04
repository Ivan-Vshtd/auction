package custom.userportal.service;

import custom.userportal.domain.Bet;
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

  public Flux<Bet> findAll() {
    return betRepository.findAll();
  }

  public Mono<Bet> findById(String id) {
    return betRepository.findById(id);
  }

  public Mono<Bet> addBet(Bet bet) {
    long lastViewId = betRepository                        //just to generate valid numbers of bets
                                .findAllByAuctionId(bet.getAuctionId())
                                .toStream()
                                .map(Bet::getViewId)
                                .map(Long::valueOf)
                                .max(Long::compareTo)
                                .orElse(0L);

    bet.setViewId(String.valueOf(lastViewId + 1));

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
}
