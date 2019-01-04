package custom.userportal.repo;

import custom.userportal.domain.Bet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * @author iveshtard
 * @since 11/23/2018
 */
public interface BetRepository extends ReactiveMongoRepository<Bet, String> {
  Flux<Bet> findAllByAuctionId(String auId);
}
