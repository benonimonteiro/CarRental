package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import entities.Rental;
import entities.Vehicle;
import services.BrazilTaxService;
import services.RentalService;

public class Main {

	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		System.out.println("Enter rental data");
		System.out.print("Car model: ");
		String carModel = sc.nextLine();
		System.out.print("Pickup (dd/MM/yyyy HH:mm): ");
		Date start = sdf.parse(sc.nextLine());
		System.out.print("Return (dd/MM/yyyy HH:mm): ");
		Date finish = sdf.parse(sc.nextLine());
		
		Rental rental = new Rental(start, finish, new Vehicle(carModel));

		System.out.print("Enter price per hour: ");
		Double pricePerHour = sc.nextDouble();
		System.out.print("Enter price per day: ");
 		Double pricePerDay = sc.nextDouble();
		
		RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());
		
		rentalService.processInvoice(rental);
				
		System.out.println("INVOICE:");
		System.out.println("Basic payment: "
				+ String.format("%.2f", rental.getInvoice().getBasicPayment()));
		System.out.println("Tax: "
				+ String.format("%.2f", rental.getInvoice().getTax()));
		System.out.println("Total payment: "
				+ String.format("%.2f", rental.getInvoice().getTotalPayment()));
		
		sc.close();
	}
}