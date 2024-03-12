package org.example;

import org.example.model.PairOfIntegers;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.pageobjects.BankPage;
import org.example.pageobjects.TransactionDetailPage;
import org.example.pageobjects.NewTransactionPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.text.DecimalFormat;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class ComplexScenarioTest extends BaseTest {
    static final Logger log = getLogger(lookup().lookupClass());


    @Test
    void shouldBeAbleToFilterTransactions() {
        // ARRANGE
        User user = createUser1();

        BankPage bankPage = createUserAndBankAccount(user);

        // ACT
        bankPage.openFilter();
        bankPage.changeValueOnASlider(0.25);
        bankPage.changeValueOnASlider(0.75);

        PairOfIntegers valueOnASlider = bankPage.changeValueOnASlider(0.5);

        bankPage.clickAccountBalance();

        // ASSERT
        List<Double> valuesOfTransactions = bankPage.getValuesOfTransactions();
        double min = valueOnASlider.getMin() * 10;
        double max = valueOnASlider.getMax() * 10;

        for(double value : valuesOfTransactions) {
            Assertions.assertTrue(min <= value && max >= value, "Value "+value+" not in range ["+min+", "+max+"] !");
        }


    }

    @Test
    void acceptPaymentAndCheck() {
        // ARRANGE
        User user1 = createUser1();
        User user2 = createUser2();

        String userTransaction = user1.firstName + " "+user1.lastName;
        String commentUnderTransaction = "comment "+user2.username;
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100.0);
        transaction1.setComment(commentUnderTransaction);
        transaction1.setRequest(true);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(200.0);
        transaction2.setComment(commentUnderTransaction);
        transaction2.setRequest(true);

        // ACT

        BankPage bankPage = createUserAndBankAccount(user1);
        loginPage = bankPage.logout();

        bankPage = createUserAndBankAccount(user2);

        // next transaction

        bankPage.clickMineTransactions();
        bankPage.clickNewTransaction();
        bankPage.clickPersonWithName(userTransaction);
        NewTransactionPage newTransactionPage = bankPage.fillTransactionData(transaction1);

        // validate transaction

        String transactionPerson = newTransactionPage.getTransactionPerson();

        Assertions.assertEquals(userTransaction, transactionPerson);

        String transactionDescription1 = newTransactionPage.getTransactionDescription();
        checkIfDescriptionIsCorrect(transaction1, transactionDescription1);
        bankPage = newTransactionPage.clickReturnToTransaction();

        // next transaction

        bankPage.clickMineTransactions();
        bankPage.clickNewTransaction();
        bankPage.clickPersonWithName(userTransaction);
        newTransactionPage = bankPage.fillTransactionData(transaction2);

        // validate transaction

        String transactionPerson2 = newTransactionPage.getTransactionPerson();

        Assertions.assertEquals(userTransaction, transactionPerson2);

        String transactionDescription2 = newTransactionPage.getTransactionDescription();

        checkIfDescriptionIsCorrect(transaction2, transactionDescription2);

        bankPage = newTransactionPage.clickReturnToTransaction();
        bankPage.clickMineTransactions();

        int numberOfSpecificTransactions = bankPage.findTransactionOnMineTransactionList(user2, transaction1, userTransaction);

        Assertions.assertEquals(2, numberOfSpecificTransactions, "It was not possible to find transaction data on a list");

        numberOfSpecificTransactions = bankPage.findTransactionOnMineTransactionList(user2, transaction2, userTransaction);

        Assertions.assertEquals(2, numberOfSpecificTransactions, "It was not possible to find transaction data on a list");

        loginPage = bankPage.logout();

        // ASSERT

        bankPage = loginPage.with(user1);
        bankPage.clickMineTransactions();

        int transactionsItemSize = bankPage.getTransactionsItemSize();

        Assertions.assertTrue(0 < transactionsItemSize,"index "+0+" shuld be smaller than transactionItem.size(): "+transactionsItemSize);

        TransactionDetailPage transactionDetailPage = bankPage.acceptNthTransactions(0);
        transactionDetailPage.acceptRequest();

        String sender = transactionDetailPage.getSender(0);
        String receiver = transactionDetailPage.getReceiver(0);
        String action = transactionDetailPage.getAction(0);

        checkIfTransactionIsAccepted(user1, user2, transaction1.isRequest(), sender, receiver, action);

        bankPage.clickHome();

        // to avoid staleElementException
        bankPage = new BankPage(bankPage.getDriver());
        bankPage.clickMineTransactions();


        transactionsItemSize = bankPage.getTransactionsItemSize();

        Assertions.assertTrue(1 < transactionsItemSize,"index "+0+" shuld be smaller than transactionItem.size(): "+transactionsItemSize);

        transactionDetailPage = bankPage.acceptNthTransactions(1);
        transactionDetailPage.acceptRequest();

        sender = transactionDetailPage.getSender(0);
        receiver = transactionDetailPage.getReceiver(0);
        action = transactionDetailPage.getAction(0);

        checkIfTransactionIsAccepted(user1, user2, transaction2.isRequest(), sender, receiver, action);

        bankPage.logout();
    }


    private void checkIfTransactionIsAccepted(User user, User user1, boolean request, String sender, String receiver, String action) {
        String receiverBase = user.firstName + " " + user.lastName;
        String senderBase = user1.firstName + " " + user1.lastName;
        String actionBase = "paid";

        if(request) {
            actionBase = "charged";
        }

        Assertions.assertEquals(senderBase,sender);
        Assertions.assertEquals(receiverBase, receiver);
        Assertions.assertEquals(actionBase, action);
    }

    public void checkIfDescriptionIsCorrect(Transaction transaction, String transactionText) {

        DecimalFormat df = new DecimalFormat("#,###.00");
        StringBuilder sb = new StringBuilder();
        if(transaction.isRequest()) {
            sb.append("Requested ");
        }
        else {
            sb.append("Paid ");
        }
        sb.append("$");
        String amountTemp = df.format(transaction.getAmount()).replace(',','.');
        amountTemp = amountTemp.replace(' ', ',');
        amountTemp = removeIncorrectSigns(amountTemp);
        sb.append(amountTemp);
        sb.append(" for ");
        sb.append(transaction.getComment());

        Assertions.assertEquals(sb.toString(), transactionText, "Expected "+sb+" is not equal to "+transactionText);
    }

    private String removeIncorrectSigns(String amount) {
        byte[] b = amount.getBytes();
        for(int i = 0; i < b.length; i++) {
            if(b[i] == -96) {
                b[i] = ",".getBytes()[0];
            }
            else if(b[i] == -62) {
                b[i] = " ".getBytes()[0];
            }
        }
        return new String(b).replaceAll("\\s+","");
    }

}
