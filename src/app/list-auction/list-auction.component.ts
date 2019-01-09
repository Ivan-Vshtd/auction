import {Component, OnInit} from '@angular/core';
import {AuctionService} from '../service/auctions.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Auction} from '../models/auction.model';

@Component({
  selector: 'app-list-auction',
  templateUrl: './list-auction.component.html',
  styleUrls: ['./list-auction.component.css']
})
export class ListAuctionComponent implements OnInit {

  auctions;
  displayedColumns: string[] = ['viewId', 'authorName', 'name', 'lastBet', 'join', 'delete'];
  valueForm: FormGroup;

  constructor(
    private auctionService: AuctionService,
    private formBuilder: FormBuilder,
    private router: Router) {
  }

  ngOnInit() {
    this.fillTheForm();
    this.auctionService
      .getAuctions()
      .subscribe(data => {
        this.auctions = data;
      });
  }

  fillTheForm(): void {
    this.valueForm = this.formBuilder.group({
      id: [],
      viewId: [],
      authorName: [],
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSubmit() {
    this.auctionService
      .saveAuction(this.valueForm.value)
      .subscribe(data => {
          this.ngOnInit();
        },
        error => {
          alert(error);
        });
  }

  deleteAuction(auction: Auction): void {
    this.auctionService
      .deleteAuction(auction.id)
      .subscribe(data => {
        this.auctions = this.auctions.filter(requiredAuction => requiredAuction !== auction);
      });
  }

  join(id: string) {
    localStorage.setItem('currentAuction', id);
    this.router.navigate(['list-auctions/' + id]);
  }

  crntUser(): string {
    return localStorage.getItem('currentUser');
  }
}
