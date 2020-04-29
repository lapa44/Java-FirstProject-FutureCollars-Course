import { Component, OnInit, Input } from '@angular/core';
import { InvoiceService } from '../invoice.service';
import { Invoice } from '../invoice';
import { Router, ActivatedRoute } from '@angular/router';
import { InvoiceListComponent } from '../invoice-list/invoice-list.component'

@Component({
  selector: 'app-invoice-details',
  templateUrl: './invoice-details.component.html',
  styleUrls: ['./invoice-details.component.css']
})
export class InvoiceDetailsComponent implements OnInit {

  id: string;
  invoice: Invoice;
  displayedColumns: string[] = ['description', 'unit', 'quantity', 'unitPrice', 'price', 'vatValue', 'vatRate'];

  constructor(private route: ActivatedRoute,private router: Router,
                  private invoiceService: InvoiceService) { }

  ngOnInit() {
    this.invoice = new Invoice();

    this.id = this.route.snapshot.params['id'];

    this.invoiceService.getInvoice(this.id)
      .subscribe(data => {
        console.log(data)
        this.invoice = data;
      }, error => console.log(error));
  }

  list() {
    this.router.navigate(['invoices']);
  }

}
