import { Component, OnInit } from '@angular/core';
import { InvoiceService } from '../invoice.service';
import { Invoice } from '../invoice';
import { InvoiceEntry } from '../invoiceEntry';
import { Router, ActivatedRoute } from '@angular/router';
import { FormArray, FormControl, FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-create-invoice',
  templateUrl: './create-invoice.component.html',
  styleUrls: ['./create-invoice.component.css']
})
export class CreateInvoiceComponent implements OnInit {

  constructor(private invoiceService: InvoiceService, private router: Router, private fb: FormBuilder,
              private _route: ActivatedRoute) {}

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

  id = null;
  submitted = false;
  vatRates: String[] = [
    'VAT_0', 'VAT_5', 'VAT_8', 'VAT_23'
  ];

  ngOnInit(): void {
     this._route.paramMap.subscribe(parameterMap => {
        this.id = parameterMap.get('id');
        if (this.id != null) {
          this.patchInvoiceForm(this.id);
        }
     });
  }

  private patchInvoiceForm(id: string) {
      this.invoiceService.getInvoice(id).subscribe(invoice => {
        this.invoiceForm.patchValue({
                  invoiceNumber: invoice.invoiceNumber,
                  date: invoice.date,
                  buyer: {
                    taxIdentificationNumber: invoice.buyer.taxIdentificationNumber,
                    address: invoice.buyer.address,
                    name: invoice.buyer.name
                  },
                  seller: {
                    taxIdentificationNumber: invoice.seller.taxIdentificationNumber,
                    address: invoice.seller.address,
                    name: invoice.seller.name
                  }
              });
        this.patchEntries(invoice.entries);
      })
  }

  private patchEntries(entries: InvoiceEntry[]) {
      this.entries.clear();
      for (let i = 0; i < entries.length; i++) {
        this.entries.push(
          this.fb.group({
              description: entries[i].description,
              unit: entries[i].unit,
              quantity: entries[i].quantity,
              unitPrice: entries[i].unitPrice,
              vatRate: entries[i].vatRate
          }));
      }
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
    invoice.id = this.id;
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
