package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance());
        //equivalence case- amount is larger than balance
        assertFalse(bankAccount.getBalance()==400);
        assertFalse(bankAccount.getBalance()==201);
        assertFalse(bankAccount.getBalance()==200.01);
        //equivalence case- amount is smaller than balance
        assertFalse(bankAccount.getBalance()==199);
        assertFalse(bankAccount.getBalance()==20.0);
        assertFalse(bankAccount.getBalance()==199.99);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        BankAccount bankAccount2 = new BankAccount("ab@c.com", 400);

        //equivalence case- the amount to withdraw is smaller than the balance
        //boundary case- the amount to withdraw is just under the balance
        bankAccount2.withdraw(399);
        assertEquals(1, bankAccount2.getBalance());
        //boundary case- the amount to withdraw is 0
        bankAccount2.withdraw(0);
        assertEquals(1, bankAccount2.getBalance());

        //equivalence case- the amount to withdraw is larger than the balance

        //boundary case- the amount to withdraw is just over the balance
        BankAccount bankAccount3= new BankAccount ("cb@bc.com", 400);
        assertThrows(InsufficientFundsException.class, () -> bankAccount3.withdraw(401));
        //boundary case- the amount to withdraw is much larger than the balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(1000));

        //equivalence case- the amount ot withdraw is the same as the balance

        bankAccount3.withdraw(400);
        assertEquals(0, bankAccount3.getBalance());

        //equivalence case- the amount to withdraw is negative. Should throw exception
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-10));


    }

    @Test
    void isEmailValidTest(){
        //valid if characters before '@' and between '@' and '.' are 1 or more in number.
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        //invalid if string is empty. This is boundary case
        assertFalse( BankAccount.isEmailValid(""));
        //invalid if '-' is in address
        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));
        //invalid if # of characters after last '.' is 1 or less. This is boundary case.
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.co"));
        //invalid if '#' is before '@'
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com"));
        //invalid if '..' is found. This could be for any 2 symbols and be boundary case depending on implimentation
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));

        assertFalse(BankAccount.isEmailValid("abc.def@.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@m.com"));

        assertFalse(BankAccount.isEmailValid("#*$!@mail.com"));
        assertTrue(BankAccount.isEmailValid("abcde@mail.com"));

        assertFalse(BankAccount.isEmailValid("abc.def@mail"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));

        


    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

       assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.001));
       assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.999));
       assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.0000000005));
       assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));
       assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100.999));
       assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100.99));
       assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.", 100.99));
    }

    @Test
    void isAmountValidTest(){
        //equivalence cases: two or less decimal places, more than two decimal places, positive doubles, negative doubles
        assertTrue(BankAccount.isAmountValid(20));
        assertTrue(BankAccount.isAmountValid(20.0));
        assertTrue(BankAccount.isAmountValid(20.01));
        assertTrue(BankAccount.isAmountValid(20.50));
        assertTrue(BankAccount.isAmountValid(20.99));
        assertFalse(BankAccount.isAmountValid(21.001));
        assertFalse(BankAccount.isAmountValid(21.501));
        assertFalse(BankAccount.isAmountValid(21.999));
        assertFalse(BankAccount.isAmountValid(-20.00));
        assertFalse(BankAccount.isAmountValid(-20.999));
        assertFalse(BankAccount.isAmountValid(20.0000000006));
    }

    @Test
    void depositTest(){
        BankAccount bankAccount= new BankAccount("a@b.com", 200);
        bankAccount.deposit(100);
        assertEquals(bankAccount.getBalance(), 300);
        bankAccount.deposit(0);
        assertEquals(bankAccount.getBalance(), 300);
        bankAccount.deposit(100.01);
        assertEquals(bankAccount.getBalance(), 400.01);
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-100));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(100.001));
        bankAccount.deposit(0.99);
        assertEquals(bankAccount.getBalance(), 401.00);

    }

    @Test
    void transferTest(){
        BankAccount bankAccount1= new BankAccount("a@b.com", 200);
        BankAccount bankAccount2= new BankAccount("b@c.com", 200);
        bankAccount1.transfer(100, bankAccount2);
        assertEquals(bankAccount1.getBalance(), 100);
        assertEquals(bankAccount2.getBalance(), 300);
        assertThrows(InsufficientFundsException.class, ()-> bankAccount1.transfer(200, bankAccount2));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.transfer(-100, bankAccount2));
        bankAccount2.transfer(100.1, bankAccount1);
        assertEquals(bankAccount1.getBalance(), 200.1);
        assertEquals(bankAccount2.getBalance(), 199.9);
        bankAccount2.transfer(.05, bankAccount1);
        assertEquals(bankAccount1.getBalance(), 200.15);
        assertEquals(bankAccount2.getBalance(), 199.85);
        assertThrows(IllegalAccessException.class, ()-> bankAccount1.transfer(0.001, bankAccount2));


    }

}