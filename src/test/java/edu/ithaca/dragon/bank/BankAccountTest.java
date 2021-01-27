package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        //valid if characters before '@' and between '@' and '.' are 1 or more in number.
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        //invalid if string is empty. This is boundary case
        assertFalse( BankAccount.isEmailValid(""));
        //invalid if '-' is before '@'
        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));
        //invalid if # of characters after last '.' is 1 or less. This is boundary case.
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));
        //invalid if '#' is before '@'
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com"));
        //invalid if '..' is found. This could be for any 2 symbols and be boundary case depending on implimentation
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com"));

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}