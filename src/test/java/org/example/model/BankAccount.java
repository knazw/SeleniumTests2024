package org.example.model;

import java.util.Random;

public class BankAccount {

    public String bank;

    public String accountNr;

    public String routingNumber;

    public static BankAccount createRandom() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.bank = "santander";
        bankAccount.accountNr = randomString(9);
        bankAccount.routingNumber = randomString(9);
        return bankAccount;
    }

    private static String randomString(int len) {
        Random rnd = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int randomInt = 0;
        while(i < len) {
            randomInt = rnd.nextInt(10);
            sb.append(randomInt);
            i++;
        }

        return sb.toString();
    }
}
