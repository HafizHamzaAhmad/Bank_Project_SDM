import Banking.*;
import Interest.BasicInterest;
import Interest.SpecialInterest;
import MainPackage.MainClass;
import Reporting.Over1000Report;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;
import java.util.List;
//import static org.junit.jupiter.api


public class TestBank extends TestCase {

    //Tests for State Design Pattern
    public void testForInterestCalculationPass() {
        try {
            MainClass.prepareBanks();
            Bank bank = MainClass.getBank(2);
            Double interest = bank.calculateInterest(1);
            assertEquals(20.0, interest);

            interest = bank.calculateInterest(2);
            assertEquals(50.0, interest);
        } catch (Exception e) {
        }
    }

    @Test(expected = Exception.class)
    public void testForInterestCalculationFail() {
        MainClass.prepareBanks();
        Bank bank = MainClass.getBank(2);
        try {
            bank.calculateInterest(8);
        } catch (Exception e) {
        }
    }

    //Tests for Command Design Pattern
    public void testForTransferCmd() {
        MainClass.prepareBanks();
        Bank bank = MainClass.getBank(2);
        IAccount recAcc = bank.getAccounts().get(0);
        Double recBalance = recAcc.getBalance();
        IAccount senderAcc = bank.getAccounts().get(1);
        Double senderBalance = senderAcc.getBalance();
        try {
            TransferCommand transferCommand = new TransferCommand(recAcc, senderAcc, 500.0);
            bank.doOperation(transferCommand);
            assertEquals((recBalance + 500.00), recAcc.getBalance());
            assertEquals((senderBalance - 500.00), senderAcc.getBalance());
        } catch (Exception e) {
        }
    }

    public void testForDeductTaxCmd() {
        MainClass.prepareBanks();
        Bank bank = MainClass.getBank(2);
        IAccount acc = bank.getAccounts().get(0);
        Double balance = acc.getBalance();
        try {
            DeductTaxCommand deductTaxCommand = new DeductTaxCommand(5.0, acc);
            bank.doOperation(deductTaxCommand);
            assertEquals((balance - 50.00), acc.getBalance());
        } catch (Exception e) {
        }
    }

    public void testOpenAccount() {

        Bank bankA = new Bank(1, "WBK");
        bankA.getAccounts().add(new Account(1, "Hamza", new Date(), 8500.00, false, new BasicInterest()));
        bankA.getAccounts().add(new Account(2, "Rohail", new Date(), 1200.00, false, new SpecialInterest()));
        bankA.getAccounts().add(new DebitAccount(new Account(3, "Omer", new Date(), 1500.00, true, new BasicInterest(), 500.00)));

        String name = "Faheem";
        Double balance = 900.0;
        assertTrue(bankA.openAccount(name, balance, false, new BasicInterest()));
    }

    public void testCreditAccount() {

        Bank bankA = new Bank(1, "WBK");
        bankA.getAccounts().add(new Account(1, "Hamza", new Date(), 8500.00, false, new BasicInterest()));
        bankA.getAccounts().add(new Account(2, "Rohail", new Date(), 1200.00, false, new SpecialInterest()));
        bankA.getAccounts().add(new DebitAccount(new Account(3, "Omer", new Date(), 1500.00, true, new BasicInterest(), 500.00)));


        String name = "Hamza";
        Double balance = 900.0;
        bankA.openAccount(name, balance, false, new BasicInterest());
        assertEquals(true, bankA.creditAccount(name, 900.0));
    }

    public void testWithdrawAccount() throws Exception {


        Bank bankA = new Bank(1, "WBK");
        bankA.getAccounts().add(new Account(1, "Hamza", new Date(), 8500.00, false, new BasicInterest()));
        bankA.getAccounts().add(new Account(2, "Rohail", new Date(), 1200.00, false, new SpecialInterest()));
        bankA.getAccounts().add(new DebitAccount(new Account(3, "Omer", new Date(), 1500.00, true, new BasicInterest(), 500.00)));

        Integer accNo = 3;
        Double balance = 900.0;

        bankA.withdraw(accNo, balance);
//		assertEquals(true,bankA.withdraw(3, 900.0));

    }

    //Test for Visitor Design Pattern
    public void testOver1000Report() {
        MainClass.prepareBanks();
        Bank bank = MainClass.getBank(1);
        List<IAccount> resultList = bank.doReport(new Over1000Report());
        for (IAccount acc : resultList ) {
            if(acc.getClass().equals(Account.class)) {
                assertTrue(acc.getBalance() > 1000.00);
            }
            else if (acc.getClass().equals(DebitAccount.class)) {
                assertTrue(acc.getDebitLimit() > 1000);
            }
        }
    }
}

