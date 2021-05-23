package com.group76pos;

public class Customer {
  String name, phoneNumber, email;

  Customer(String name, String phoneNumber, String email) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
    new Customer("Justin Brandt", "0812345678", "justinb@email.com");
    new Customer("Craig Van Niekerk", "08198765432", "craig123@email.com");
    new Customer("Sipho Mateke", "0818527419", "siphom987@email.com");
  }
}