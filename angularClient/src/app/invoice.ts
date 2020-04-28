import { Company } from './Company';
import { InvoiceEntry } from './InvoiceEntry';

export class Invoice {
    id: string;
    invoiceNumber: string;
    date: string;

    buyer: Company;
    seller: Company;
    entries: InvoiceEntry[] = new Array();

    constructor() {
      this.buyer = new Company();
      this.seller = new Company();
    }
}
