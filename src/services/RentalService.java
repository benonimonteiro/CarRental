package services;

import entities.Invoice;
import entities.Rental;

public class RentalService {
	private Double pricePerHour;
	private Double pricePerDay;
	private TaxService taxService;	

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}
	
	public void processInvoice(Rental rental) {
		long t1 = rental.getStart().getTime();
		long t2 = rental.getFinish().getTime() ;
		double hours = (double) (t2 - t1) / 1000 / 60 / 60;
		
		double basicPayment;		
		if (hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		} else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment);
		
		rental.setInvoice(new Invoice(basicPayment ,tax));
	}
}
