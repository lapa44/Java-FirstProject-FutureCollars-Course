import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InvoiceDetailsComponent } from './invoice-details/invoice-details.component';
import { CreateInvoiceComponent } from './create-invoice/create-invoice.component';
import { InvoiceListComponent } from './invoice-list/invoice-list.component'



const routes: Routes = [
  { path: '', redirectTo: 'invoice', pathMatch: 'full'},
  { path: 'invoices', component: InvoiceListComponent},
  { path: 'add', component: CreateInvoiceComponent},
  { path: 'details/:id', component: InvoiceDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
