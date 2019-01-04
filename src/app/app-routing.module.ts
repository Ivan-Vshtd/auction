import {RouterModule, Routes} from '@angular/router';
import {UserComponent} from './list-user/user.component';
import {AddUserComponent} from './add-user/add-user.component';
import {LoginComponent} from './login/login.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {BetsComponent} from './bets/bets.component';
import {ListAuctionComponent} from './list-auction/list-auction.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', component: LoginComponent},
  {path: 'list-user', component: UserComponent},
  {path: 'add-user', component: AddUserComponent},
  {path: 'edit-user', component: EditUserComponent},
  {path: 'list-auctions', component: ListAuctionComponent},
  {path: 'list-auctions/:id', component: BetsComponent},
];

export const  routing = RouterModule.forRoot(routes);
