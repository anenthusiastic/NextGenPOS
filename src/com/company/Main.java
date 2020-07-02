package com.company;

import com.company.domain.Register;
import com.company.domain.Store;
import com.company.ui.UserInterface;

public class Main {

    public static void main(String[] args) {
        Store store = new Store();
        Register register = store.getRegister();
        new UserInterface(register);
    }
}
