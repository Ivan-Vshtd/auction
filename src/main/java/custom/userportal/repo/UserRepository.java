package custom.userportal.repo;

import custom.userportal.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author iveshtard
 * @since 11/8/2018
 */

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
