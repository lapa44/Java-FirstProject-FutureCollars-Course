import { Component, OnInit } from '@angular/core';
import { InvoiceService } from '../invoice.service';
import { Invoice } from '../invoice';
import { Router } from '@angular/router';
import { FormArray, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-create-invoice',
  templateUrl: './create-invoice.component.html',
  styleUrls: ['./create-invoice.component.css']
})
export class CreateInvoiceComponent implements OnInit {

  invoice: Invoice = new Invoice();
  submitted = false;
  vatRates: String[] = [
    'VAT_0', 'VAT_5', 'VAT_8', 'VAT_23'
  ];

  constructor(private invoiceService: InvoiceService, private router: Router) {
    //this.invoice.entries = new FormArray([]);
    this.invoice.entries.push(new FormGroup({
      description: new FormControl(''),
      unit: new FormControl(''),
      quantity: new FormControl(''),
      unitPrice: new FormControl(''),
      vatRate: new FormControl('')
    }));
  }

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

  addEntry() {
    //this.invoice.entries.push(new FormControl(''));
  }

  removeEntry(index: number) {
    this.invoice.entries.removeAt(index);
  }
}
