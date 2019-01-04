import {Component, OnInit} from '@angular/core';

;
import {AuctionService} from '../service/auctions.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-list-auction',
  templateUrl: './list-auction.component.html',
  styleUrls: ['./list-auction.component.css']
})
export class ListAuctionComponent implements OnInit {

  auctions;
  displayedColumns: string[] = ['viewId', 'authorName', 'name', 'join'];
  valueForm: FormGroup;

  constructor(private auctionService: AuctionService, private formBuilder: FormBuilder, private router: Router) {
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
      name: ['', Validators.required]
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

  join(id: string) {
    localStorage.setItem('currentAuction', id);
    this.router.navigate(['list-auctions/' + id]);
  }
}
