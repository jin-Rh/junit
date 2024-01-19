package com.example.unittesting5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account;
    private static int count;

    //executes before every test
    @BeforeEach
    void setUp(){
        account = new Account("Harry",
                "Potter",
                100d,
                Account.CURRENT);
        count = 0;
    }

    //executes after every test
    @AfterEach
    void tearDown(){
        System.out.println("Count = " + count++);
    }

    @Test
    void deposit(){
        assertEquals(200.00,
                account.deposit(100, true));
    }

    @Test
    void testWithdraw(){
        int amount = 20;
        //add assumption to check whether the balance is grater than the amount
        Assumptions.assumeTrue(account.getBalance() >= amount);
        assertEquals(80,
                account.withdraw(amount, true));
    }

    @Test
    void validateCalcInterest(){
        //add assumption to check whether the balance is grater than zero
        Assumptions.assumeTrue(account.getBalance() > 0);
        assertEquals(2, account.calculateInterest());
    }

    @Test
    void nullCheck(){
        assertNotNull(account);
    }


    //Testing expected exceptions get thrown
    @Test
    void withdrawNotInBranch(){
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(3000, false));
    }

    @Test
    void testGetBalance(){
        //add assumption to check whether the instance is not null
        Assumptions.assumeTrue(account!=null);

        account.withdraw(50, true);
        assertEquals(50, account.getBalance());
    }

    //checking whether the identity of instances are the same
    @Test
    void checkInstances(){

        Account another = account;
        assertSame(account, another);
    }


    @Test
    void compareAnotherInstance(){
        Account another = new Account("Bob", "Smith",
                200d, Account.CURRENT);

        assertSame(account.getClass(), another.getClass());
    }


    @Test
    void isChecking(){
        assertTrue(account.isCurrentAccount());
    }


    //evaluate multiple values using Stream object, reduce repetitive testing
    static Stream<Arguments> simulateMultipleConditions(){
        return Stream.of(Arguments.of(Arrays.asList(
                new Object[]{150d, true, 250d},
                new Object[]{220d, true, 320d},
                new Object[]{394d, true, 494d}
        )));

    }

    //evaluate one method using an object contains multiple values
    @ParameterizedTest
    @MethodSource("simulateMultipleConditions")
    void getBalanceDepositParams(List<Object[]> input){
        for (Object[] objects : input){
            account = new Account("Jon",
                    "Snow",
                    100d,
                    Account.CURRENT);
            double amount = (Double) objects[0];
            boolean branch = (Boolean) objects[1];
            double expectedAmt = (Double) objects[2];
            account.deposit(amount, branch);
            assertEquals(expectedAmt, account.getBalance(), 0.1);
            System.out.println("Balance = " + account.getBalance());
        }
    }


    //with comma seperated text as input
    @ParameterizedTest
    @CsvSource({"100d, true, 200d", "200d, true, 300d", " 325.14, true, 425.14"
            , "489.33, true, 589.33", "1000d, true, 1100d"})
    void getBalance_deposit_param(double amount, boolean branch, double expected) {
        account.deposit(amount, branch);
        Assertions.assertEquals(expected, account.getBalance(), 0.1);
        System.out.println("balance = " + account.getBalance());
    }

    @AfterAll
    static void afterAll() {
        System.out.println("This executes after any test cases. Count = " + count);

    }


}