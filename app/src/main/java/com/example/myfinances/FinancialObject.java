package com.example.myfinances;

public class FinancialObject {
    protected String accountNumber;
    protected double initialBalance;
    protected double currentBalance;

    public FinancialObject(String accountNumber, double initialBalance, double currentBalance) {
        this.accountNumber = accountNumber;
        this.initialBalance = initialBalance;
        this.currentBalance = currentBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    // CD Subclass
    public static class CD extends FinancialObject {
        private double interestRate;

        public CD(String accountNumber, double initialBalance, double currentBalance, double interestRate) {
            super(accountNumber, initialBalance, currentBalance);
            this.interestRate = interestRate;
        }

        public double getInterestRate() {
            return interestRate;
        }
    }

    // Loan Subclass
    public static class Loan extends FinancialObject {
        private double paymentAmount;
        private double interestRate;

        public Loan(String accountNumber, double initialBalance, double currentBalance, double paymentAmount, double interestRate) {
            super(accountNumber, initialBalance, currentBalance);
            this.paymentAmount = paymentAmount;
            this.interestRate = interestRate;
        }

        public double getPaymentAmount() {
            return paymentAmount;
        }

        public double getInterestRate() {
            return interestRate;
        }
    }

    // Checking Account Subclass
    public static class CheckingAccount extends FinancialObject {
        public CheckingAccount(String accountNumber, double initialBalance, double currentBalance) {
            super(accountNumber, initialBalance, currentBalance);
        }
    }
}

