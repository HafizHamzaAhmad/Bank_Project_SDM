import Banking.Bank;
import Banking.DeductTaxCommand;
import Banking.IAccount;
import Banking.TransferCommand;
import Interest.SpecialInterest;
import MainPackage.MainClass;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;
//import static org.junit.jupiter.api


public class TestBank extends TestCase {

	//Following tests are for testing Interest system implemented with State Design Pattern
	public void testForInterestCalculationPass() {
		try {
			MainClass.prepareBanks();
			Bank bank = MainClass.getBank(2);
			Double interest = bank.calculateInterest(1);
			assertEquals(20.0, interest);

			interest = bank.calculateInterest(2);
			assertEquals(50.0, interest);
		}
		catch (Exception e) {
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

	//Following tests are for testing Interest system implemented with Command Design Pattern
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
}

