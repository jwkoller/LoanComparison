package koller.practice.loancomparison;

public class LoanDetails 
{
	private final int ARRAY_FIELDS = 3;
	private final int MAX_RECORDS = 50;
	private final int RESET_VALUE = 0;
	private final double INTEREST_INCREMENT = 0.125;
	private double loanAmount = 0.0;
	private double loanYears = 0.0;
	private double[] interestRates = new double[ARRAY_FIELDS];
	private double [][] interestAndPayments = new double[MAX_RECORDS][ARRAY_FIELDS];
	private int recordCount = 0;
	
	public void setLoanAmount(double borrowedLoanAmount)
	{
		loanAmount = borrowedLoanAmount;
	}
	
	public void setLoanYears(double borrowedLoanYears)
	{
		loanYears = borrowedLoanYears;
	}
	
	public void setStartingRate(double borrowedStartingRate)
	{
		interestRates[0] = borrowedStartingRate;	//starting interest rate
	}
	
	public void setEndingRate(double borrowedEndingRate)
	{
		interestRates[2] = borrowedEndingRate;	//ending interest rate
	}
	public void setInterestAndPayment()
	{
		recordCount = RESET_VALUE;
		interestRates[1] = interestRates[0];
		
		for(int localIndex = 0; interestRates[1] <= interestRates[2] ; localIndex++)
		{
			interestAndPayments[localIndex][0] = interestRates[1];
			interestAndPayments[localIndex][1] = loanAmount * (interestRates[1]/1200) / (1 - 1 / Math.pow(1 + (interestRates[1]/1200), loanYears * 12));
			interestAndPayments[localIndex][2] = (interestAndPayments[localIndex][1]) * (loanYears * 12);
			interestRates[1] += INTEREST_INCREMENT;
			recordCount++;
		}
	}
	
	public double getLoanAmount()
	{
		return loanAmount;
	}
	
	public double getLoanYears()
	{
		return loanYears;
	}
	
	public double[] getInterestRates()
	{
		return interestRates;
	}
	
	public double[][] getInterestAndPayments()
	{
		return interestAndPayments;
	}
	
	public int getRecordCount()
	{
		return recordCount;
	}
	
	
	
}//end of LoanDetails class
