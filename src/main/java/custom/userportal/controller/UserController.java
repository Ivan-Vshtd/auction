package custom.userportal.controller;

import custom.userportal.domain.User;
import custom.userportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author iveshtard
 * @since 11/8/2018
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("user-portal/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public Mono<User> create(@RequestBody User user){
    return userService.create(user);
  }

  @GetMapping(path = "/{id}")
  public Mono<User> findOne(@PathVariable("id") String id){
    return userService.findById(id);
  }

  @PutMapping(path = {"/{id}"})
  public Mono<User> update(@PathVariable("id") String id, @RequestBody User user){
    return userService.update(id, user);
  }

  @DeleteMapping(path = "/{id}")
  public Mono<User> delete(@PathVariable("id") String id){
    return userService.delete(id);
  }

  @GetMapping
  public Flux<User> findAll(){
    return userService.findAll();
  }
}
