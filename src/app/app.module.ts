import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {UserComponent} from './list-user/user.component';
import {AddUserComponent} from './add-user/add-user.component';
import {HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import {UserService} from './service/user.service';
import {LoginComponent} from './login/login.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {routing} from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CustomMaterialModule} from './material/material.module';
import { BetsComponent } from './bets/bets.component';
import {BetsService} from './service/bets.service';
import { ListAuctionComponent } from './list-auction/list-auction.component';
import {AuctionService} from './service/auctions.service';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    AddUserComponent,
    LoginComponent,
    EditUserComponent,
    BetsComponent,
    ListAuctionComponent,
  ],
  imports: [
    BrowserModule,
    routing,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
  ],
  providers: [UserService, BetsService, AuctionService],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor() {
  }
}

