import {AfterContentInit, Component, OnInit} from '@angular/core';
import {Bet} from '../models/bet.model';
import {BetsService} from '../service/bets.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {addHandler, connect} from '../../app/util/ws.js';

@Component({
  selector: 'app-bets',
  templateUrl: './bets.component.html',
  styleUrls: ['./bets.component.css']
})
export class BetsComponent implements OnInit, AfterContentInit {

  bets;
  displayedColumns: string[] = ['viewId', 'authorName', 'text', 'edit', 'delete'];
  valueForm: FormGroup;
  betId;

  constructor(private betsService: BetsService, private formBuilder: FormBuilder) {
    connect();
  }

  ngOnInit() {
    this.fillTheForm();
    this.betsService
      .getRequiredBets(localStorage.getItem('currentAuction'))
      .subscribe(data => {
        this.bets = data;
      });
  }

  ngAfterContentInit(): void {
    this.wsHandling();
  }

  deleteBet(bet: Bet): void {
    this.betsService
      .deleteBet(bet.id)
      .subscribe(data => {
        this.bets = this.bets.filter(requiredBet => requiredBet !== bet);
      });
  }

  editBet(bet: Bet): void {
    this.betId = bet.id;
    this.fillTheForm();
    if (this.betId) {
      this.betsService
        .getBetById(bet.id)
        .subscribe(data => {
          this.valueForm.setValue(data);
        });
    }
  }

  onSubmit() {
    if (this.betId) {
      this.betsService
        .updateBet(this.valueForm.value)
        .pipe(first())
        .subscribe(data => {
            this.bets.push(data);
          },
          error => {
            alert(error);
          });
      this.betId = undefined;
    } else {
      this.betsService
        .saveBet(this.valueForm.value)
        .subscribe(data => {
            this.bets.push(data);
          },
          error => {
            alert(error);
          });
    }
  }

  crntUser(): string {
    return localStorage.getItem('currentUser');
  }

  wsHandling(): void {
    addHandler(data => {
      if (data.objectType === 'BET') {
        switch (data.eventType) {
          case 'CREATE':
            console.log(data.eventType);
            this.ngOnInit();
            break;
          case 'UPDATE':
            this.ngOnInit();
            console.log(data.eventType);
            break;
          case 'REMOVE':
            this.ngOnInit();
            console.log(data.eventType);
            break;
          default:
            console.error(`Looks like the event type if unknown "${data.eventType}"`);
        }
      } else {
        console.error(`Looks like the object type is unknown "${data.objectType}"`);
      }
    });
  }

  fillTheForm(): void {
    this.valueForm = this.formBuilder.group({
      id: [],
      viewId: [],
      authorName: [],
      text: ['', Validators.required],
      auctionId: ['', Validators.required]
    });
  }

}
