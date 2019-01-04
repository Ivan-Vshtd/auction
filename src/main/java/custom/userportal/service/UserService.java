package custom.userportal.service;

import custom.userportal.domain.User;
import custom.userportal.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author iveshtard
 * @since 11/8/2018
 */

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public Mono<User> create(User user) {
    return userRepository.save(user);
  }

  public Mono<User> findById(String id) {
    return userRepository.findById(id);
  }

  public Flux<User> findAll() {
    return userRepository.findAll();
  }

  public Mono<User> delete(String id) {
    Mono<User> user = findById(id);
    if(user != null){
      userRepository.delete(user.block()).subscribe();
    }
    return user;
  }

  public Mono<User> update(String id, User user) {
    user.setId(id);
    return userRepository.save(user);
  }
}
