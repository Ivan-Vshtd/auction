package custom.userportal.controller;

import custom.userportal.domain.Auction;
import custom.userportal.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author iveshtard
 * @since 1/4/2019
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("user-portal/auctions")
public class AuctionController {

  @Autowired
  AuctionService auctionService;

  @GetMapping
  public Flux<Auction> getAllAuctions(){
    return auctionService.findAll();
  }

  @GetMapping(path = "/{id}")
  public Mono<Auction> findOneAuction(@PathVariable String id){
    return auctionService.findById(id);
  }

  @PostMapping
  public Mono<Auction> addAuction(@RequestBody Auction auction)
  {
    return auctionService.addAuction(auction);
  }
}
