import { Component, OnInit } from '@angular/core';
import { InvoiceService } from '../invoice.service';
import { Invoice } from '../invoice';
import { Router } from '@angular/router';
import { FormArray, FormControl, FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-create-invoice',
  templateUrl: './create-invoice.component.html',
  styleUrls: ['./create-invoice.component.css']
})
export class CreateInvoiceComponent implements OnInit {

  constructor(private invoiceService: InvoiceService, private router: Router, private fb: FormBuilder) {}

  invoiceForm = this.fb.group({
                      invoiceNumber: [''],
                      date: [''],
      buyer: this.fb.group({
                      taxIdentificationNumber: [''],
                      address: [''],
                      name: ['']
      }),
      seller: this.fb.group({
                      taxIdentificationNumber: [''],
                      address: [''],
                      name: ['']
      }),
      entries: this.fb.array([
                  this.fb.group({
                      description: [''],
                      unit: [''],
                      quantity: [''],
                      unitPrice: [''],
                      vatRate: ['']
                  })
      ])
  });

  submitted = false;
  vatRates: String[] = [
    'VAT_0', 'VAT_5', 'VAT_8', 'VAT_23'
  ];

  ngOnInit(): void {
  }

  newInvoice(): void {
    this.submitted = false;
  }

  save(invoice: Invoice) {
    this.invoiceService.createInvoice(invoice)
      .subscribe(data => console.log(data), error => console.log(error));
    this.goToList();
  }

  onSubmit() {
    let invoice = new Invoice();
    invoice = this.invoiceForm.value;
    this.submitted = true;
    this.save(invoice);
  }

  goToList() {
    console.log('Going to invoices');
    this.router.navigate(['/invoices']);
  }

  get entries() {
      return this.invoiceForm.get('entries') as FormArray;
    }

  addEntry() {
    console.log('trying to add entry');
    this.entries.push( this.fb.group({
                          description: [''],
                          unit: [''],
                          quantity: [''],
                          unitPrice: [''],
                          vatRate: ['']
    }));
  }

  removeEntry(index: number) {
    this.entries.removeAt(index);
  }
}
