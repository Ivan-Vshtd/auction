package custom.userportal.repo;

import custom.userportal.domain.Auction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author iveshtard
 * @since 1/4/2019
 */
public interface AuctionRepository extends ReactiveMongoRepository<Auction, String> {
}
