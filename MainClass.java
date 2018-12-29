/*AUTHOR: Jon Koller
 *TITLE: Loan Interest Payment Comparison
 *PURPOSE: Allows user to enter a loan amount, number of years, and range of interest rates. Then displays monthly payment
 *and total payment for each interest rate in the designated range in increments of 1/8.
 *DATE: December 27, 2018 
 */

package koller.practice.loancomparison;

import java.util.Scanner;

public class MainClass 
{
	public static final String[] MENU_OPTIONS = {"Main Menu", ".........", "Run Loan Comparison", "Quit Program"};

	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);
		LoanDetails newLoan = new LoanDetails();
		
		displayWelcomeBanner();
		
		while(validateMainMenu(input) == 'A')	//run-while not quit
		{
			newLoan.setLoanAmount(validateLoanAmount(input));
			newLoan.setLoanYears(validateLoanYears(input));
			newLoan.setStartingRate(validateStartingRate(input));
			newLoan.setEndingRate(validateEndingRate(input, newLoan.getInterestRates()));
			newLoan.setInterestAndPayment();
			displayReport(newLoan.getInterestAndPayments(), newLoan.getRecordCount(), newLoan.getLoanYears(), newLoan.getLoanAmount());	
		}	//end of run-while not quit
		
		displayFarewellBanner();

	}

	public static void displayWelcomeBanner()
	{
		System.out.println("************************************************************");
		System.out.printf("%1s%17s%-41s%1s", "*", "", "Loan Comparison Program","*");
		System.out.printf("\n%1s%3s%-55s%1s", "*", "", "This program will compare interest rates and monthly", "*");
		System.out.printf("\n%1s%11s%-47s%1s", "*", "", "payments for different loan amounts.", "*");
		System.out.println("\n************************************************************");
	}
	
	public static void displayFarewellBanner()
	{
		System.out.println("\n************************************************************");
		System.out.printf("%1s%5s%-53s%1s", "*", "", "Thank you for using the Loan Comparison Program.","*");
		System.out.printf("\n%1s%15s%-43s%1s", "*", "", "Enjoy the rest of your day!", "*");
		System.out.println("\n************************************************************");
	}
	
	public static void displayInvalidEntry()
	{
		System.out.println("\n............................................................");
		System.out.printf("%13s%-47s", "", "Invalid entry.  Please try again.");
		System.out.println("\n............................................................");
	}
	
	public static void displayMainMenu()
	{
		System.out.printf("\n%1s%-59s", "", "__________________________________________________________");
		System.out.printf("\n%1s%59s", "|", "|");
		System.out.printf("\n%1s%24s%-34s%1s", "|", "", MENU_OPTIONS[0], "|");
		System.out.printf("\n%1s%24s%-34s%1s", "|", "", MENU_OPTIONS[1], "|");
		System.out.printf("\n%1s%17s%-4s%-37s%1s", "|", "", "[A]", MENU_OPTIONS[2], "|");
		System.out.printf("\n%1s%17s%-4s%-37s%1s", "|", "", "[Q]", MENU_OPTIONS[3], "|");
		System.out.printf("\n%60s","|__________________________________________________________|\n");
		System.out.print("\nPlease enter your selection here: ");
	}
	
	public static char validateMainMenu(Scanner borrowedInput)
	{
		char localMenuSelect = ' ';
		
		displayMainMenu();
		localMenuSelect = borrowedInput.next().toUpperCase().charAt(0);
		
		while(localMenuSelect != 'A' && localMenuSelect != 'Q')
		{
			displayInvalidEntry();
			displayMainMenu();
			localMenuSelect = borrowedInput.next().toUpperCase().charAt(0);
		}
		
		return localMenuSelect;
	}
	
	public static void displayLoanAmountPrompt()
	{
		System.out.println("\nEnter the amount of the loan you would like to calculate");
		System.out.println("the monthly payments for.");
		System.out.print("\nAmount $: ");
	}
	
	public static double validateLoanAmount(Scanner borrowedInput)
	{
		int localCharValidate = 0;
		String localAmount = "";
		
		displayLoanAmountPrompt();
		localAmount = borrowedInput.next().trim();
		
		while(localCharValidate < localAmount.length())
		{
			if(!Character.isDigit(localAmount.charAt(localCharValidate)) && !Character.toString(localAmount.charAt(localCharValidate)).equals("."))
			{
				displayInvalidEntry();
				displayLoanAmountPrompt();
				localAmount = borrowedInput.next().trim();
				localCharValidate = 0;
			}
			else
			{
				localCharValidate++;
			}
		}
		
		return Double.parseDouble(localAmount);
	}
	
	public static void displayLoanYearsPrompt()
	{
		System.out.println("\nNow enter the number of years for the loan amount. Please");
		System.out.println("enter whole or half years.");
		System.out.print("\nYears: ");
	}
	
	public static double validateLoanYears(Scanner borrowedInput)
	{
		boolean localValidEntry = false;
		int localCharValidate = 0;
		String localYears = "";
		
		displayLoanYearsPrompt();
		localYears = borrowedInput.next().trim();
		
		while(localValidEntry == false)
		{
			while(localCharValidate < localYears.length())
			{
				if(!Character.isDigit(localYears.charAt(localCharValidate)) && !Character.toString(localYears.charAt(localCharValidate)).equals("."))
				{
					displayInvalidEntry();
					displayLoanYearsPrompt();
					localYears = borrowedInput.next().trim();
					localCharValidate = 0;
				}
				else
				{
					localCharValidate++;
				}
			}
			
			if(Double.parseDouble(localYears) % 0.5 > 0)
			{
				displayInvalidEntry();
				displayLoanYearsPrompt();
				localYears = borrowedInput.next().trim();
				localCharValidate = 0;
			}
			else
			{
				localValidEntry = true;
			}
		}
		
		return Double.parseDouble(localYears);
	}
	
	public static void displayStartingRatePrompt()
	{
		System.out.println("\nEnter the starting interest rate for the range of rates");
		System.out.println("you would like to see. The rate should be in 1/8 of a");
		System.out.println("point (0.125) increments.");
		System.out.print("\nStarting Rate %: ");
	}
	
	public static void displayEndingRatePrompt()
	{
		System.out.println("\nNow enter the last interest rate in the range of rates");
		System.out.println("you would like to calculate.  This should also be in 1/8");
		System.out.println("of a point (0.125) increments. For display purposes, the");
		System.out.println("ending rate should be no more than 5 points higher than");
		System.out.println("the Starting Rate.");
		System.out.print("\nEnding Rate %: ");
	}
	
	public static double validateStartingRate(Scanner borrowedInput)
	{
		int localCharValidate = 0;
		boolean localValidEntry = false;
		
		String localRates = "";
		
		displayStartingRatePrompt();
		localRates = borrowedInput.next().trim();
		
		while(localValidEntry == false)
		{
			while(localCharValidate < localRates.length())
			{
				if(!Character.isDigit(localRates.charAt(localCharValidate)) && !Character.toString(localRates.charAt(localCharValidate)).equals("."))
				{
					displayInvalidEntry();
					displayStartingRatePrompt();
					localRates = borrowedInput.next().trim();
					localCharValidate = 0;
				}
				else
				{
					localCharValidate++;
				}
			}
			if(Double.parseDouble(localRates) % 0.125 > 0)
			{
				displayInvalidEntry();
				displayStartingRatePrompt();
				localRates = borrowedInput.next().trim();
				localCharValidate = 0;
			}
			else
			{
				localValidEntry = true;
			}
		}
		
		return Double.parseDouble(localRates);
	}
	
	public static double validateEndingRate(Scanner borrowedInput, double[] borrowedRates)
	{
		int localCharValidate = 0;
		boolean localValidEntry = false;
		
		String localRates = "";
		
		displayEndingRatePrompt();
		localRates = borrowedInput.next().trim();
		
		while(localValidEntry == false)
		{
			while(localCharValidate < localRates.length())
			{
				if(!Character.isDigit(localRates.charAt(localCharValidate)) && !Character.toString(localRates.charAt(localCharValidate)).equals("."))
				{
					displayInvalidEntry();
					displayEndingRatePrompt();
					localRates = borrowedInput.next().trim();
					localCharValidate = 0;
				}
				else
				{
					localCharValidate++;
				}
			}
			if(Double.parseDouble(localRates) % 0.125 > 0 || (Double.parseDouble(localRates) - borrowedRates[0]) > 5)
			{
				displayInvalidEntry();
				displayEndingRatePrompt();
				localRates = borrowedInput.next().trim();
				localCharValidate = 0;
			}
			else
			{
				localValidEntry = true;
			}
		}
		
		return Double.parseDouble(localRates);
	}
	
	public static void displayReport(double[][] borrowedPayments, int borrowedRecordCount, double borrowedLoanYears, double borrowedLoanAmount)
	{

		System.out.println("____________________________________________________________");
		System.out.printf("%17s%-43s", "", "Interest and Payments Report");
		System.out.printf("\n%17s%-43s", "", "............................");
		System.out.printf("\n%5s%-34s%-21s", "", "Principle Loan Amount" , "Length of Loan");
		System.out.println("\n------------------------------------------------------------");
		System.out.printf("\n%10s%11.2f%24.1f%6s%9s", "$", borrowedLoanAmount, borrowedLoanYears, "years", "");
		System.out.println("\n____________________________________________________________");
		System.out.printf("\n%-27s%-13s%20s", "Interest Rate", "Monthly", "Total Payment");
		System.out.println("\n------------------------------------------------------------");
		for(int localIndex = 0; localIndex < borrowedRecordCount; localIndex++)
		{
			System.out.printf("\n%6.3f%-16s%3s%9.2f%15s%11.2f", borrowedPayments[localIndex][0], "%", "$", borrowedPayments[localIndex][1], "$", borrowedPayments[localIndex][2]);
		}
		System.out.println("\n____________________________________________________________");
		System.out.println("\n............................................................");
	}
	

}//End of Main class


