import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { InvoiceService } from '../invoice.service';
import { Invoice } from '../invoice';
import { InvoiceDetailsComponent } from '../invoice-details/invoice-details.component';
import { saveAs } from 'file-saver';


@Component({
  selector: 'app-invoice-list',
  templateUrl: './invoice-list.component.html',
  styleUrls: ['./invoice-list.component.css']
})
export class InvoiceListComponent implements OnInit {
  invoices: Observable<Invoice[]>;

  constructor(private invoiceService: InvoiceService, private router: Router) { }

  ngOnInit(): void {
    this.reloadData();
  }

  reloadData() {
    this.invoices = this.invoiceService.getInvoices();
  }

  deleteInvoice(id: string) {
    this.invoiceService.deleteInvoice(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  invoiceDetails(id: string) {
    this.router.navigate(['details', id]);
  }

  downloadAsPdf(id: string) {
    this.invoiceService.getInvoiceAsPdf(id).subscribe(response => {
    console.log(response);
      let blob = new Blob([response], { type: 'application/pdf'});
      var url = URL.createObjectURL(blob);
      window.open(url);
    }), error => console.log(error),
    () => console.info('Invoice downloaded successfully.')
  }

  downloadAsZip() {
    this.invoiceService.getInvoicesAsZip().subscribe(response => {
    console.log(response);
      let  blob = new Blob([response], { type: 'application/zip'});
      saveAs(blob, "Invoices.zip");
    }), error => console.log(error),
    () => console.info('Invoices downloaded successfully.')
  }

  editInvoice(id: string) {
    this.router.navigate(['/edit', id]);
  }
}
