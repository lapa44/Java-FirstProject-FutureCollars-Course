import { Component, OnInit } from '@angular/core';
import { InvoiceService } from '../invoice.service';
import { Invoice } from '../invoice';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-invoice',
  templateUrl: './create-invoice.component.html',
  styleUrls: ['./create-invoice.component.css']
})
export class CreateInvoiceComponent implements OnInit {

  invoice: Invoice = new Invoice();
  submitted = false;

  constructor(private invoiceService: InvoiceService, private router: Router) {}

  ngOnInit(): void {
  }

  newInvoice(): void {
    this.submitted = false;
    this.invoice = new Invoice();
  }

  save() {
    this.invoiceService.createInvoice(this.invoice)
      .subscribe(data => console.log(data), error => console.log(error));
    this.invoice = new Invoice();
    this.goToList();
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  goToList() {
    this.router.navigate(['/invoices']);
  }
}
