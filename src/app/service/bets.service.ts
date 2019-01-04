import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Bet} from '../models/bet.model';

@Injectable()
export class BetsService {

  constructor(private http: HttpClient) {
  }

  private baseUrl = 'http://localhost:8080/user-portal/bets';

  public getBets() {
    return this.http
      .get<Bet[]>(this.baseUrl);
  }

  public getRequiredBets(auId: string) {
    return this.http
      .get<Bet[]>(this.baseUrl + '/auctionBets/' + auId);
  }

  public saveBet(bet: Bet) {
    bet.authorName = localStorage.getItem('currentUser');
    bet.auctionId = localStorage.getItem('currentAuction');
    return this.http
      .post<Bet>(this.baseUrl, bet);
  }

  public deleteBet(id: string) {
    return this.http
      .delete(this.baseUrl + '/' + id);
  }

  public updateBet(bet: Bet) {
    return this.http
      .put<Bet>(this.baseUrl + '/' + bet.id, bet);
  }

  public getBetById(id: string) {
    return this.http
      .get<Bet>(this.baseUrl + '/' + id);
  }
}
