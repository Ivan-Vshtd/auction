import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Auction} from '../models/auction.model';

@Injectable()
export class AuctionService {

  constructor(private http: HttpClient) {
  }

  private baseUrl = 'http://localhost:8080/user-portal/auctions';

  public getAuctions() {
    return this.http
      .get<Auction[]>(this.baseUrl);
  }

  public saveAuction(auction: Auction) {
    auction.authorName = localStorage.getItem('currentUser');
    return this.http
      .post<Auction>(this.baseUrl, auction);
  }

  public deleteAuction(id: string) {
    return this.http
      .delete(this.baseUrl + '/' + id);
  }

  public updateAuction(auction: Auction) {
    return this.http
      .put<Auction>(this.baseUrl + '/' + auction.id, auction);
  }

  public getAuctionById(id: string) {
    return this.http
      .get<Auction>(this.baseUrl + '/' + id);
  }
}
