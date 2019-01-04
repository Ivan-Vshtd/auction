package custom.userportal.controller;

import custom.userportal.domain.Bet;
import custom.userportal.domain.Views;
import custom.userportal.dto.EventType;
import custom.userportal.dto.ObjectType;
import custom.userportal.service.BetService;
import custom.userportal.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiConsumer;

/**
 * @author iveshtard
 * @since 11/20/2018
 */

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("user-portal/bets")
public class BetController {

  private final BetService betService;
  private final BiConsumer<EventType, Mono<Bet>> wsSender;

  @Autowired
  public BetController(BetService betService, WsSender wsSender) {
    this.betService = betService;
    this.wsSender = wsSender.getSender(ObjectType.BET, Views.IdViewName.class);
  }

  @GetMapping
  public Flux<Bet> findAllBets(){
    return betService.findAll();
  }

  @GetMapping(path = "/auctionBets/{auId}")
  public Flux<Bet> findAllAuctionBets(@PathVariable String auId){
    return betService.findAllRequiredBets(auId);
  }

  @GetMapping(path = "/{id}")
  public Mono<Bet> findOneBet(@PathVariable String id){
    return betService.findById(id);
  }

  @PostMapping
  public Mono<Bet> addBet(@RequestBody Bet bet)
  {
    Mono<Bet> betMono = betService.addBet(bet);
    wsSender.accept(EventType.CREATE, betMono);  // currently wsSender is needed only to notify customers (event type and objects will need in a future)
    return betMono;
  }

  @PutMapping(path = "/{id}")
  public Mono<Bet> editBet(@RequestBody Bet bet, @PathVariable String id){
    Mono<Bet> betMono = betService.editBet(id, bet);
    wsSender.accept(EventType.UPDATE, betMono);
    return betMono;
  }

  @DeleteMapping(path = "/{id}")
  public Mono<Bet> deleteBet(@PathVariable String id){
    Mono<Bet> deleteBetMono = betService.delete(id);
    wsSender.accept(EventType.REMOVE, deleteBetMono);
    return deleteBetMono;
  }
}
