package com.futurecollars.invoices.model;

public class HelloWorld {

  private String name = "";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String message() {
    if (name == "") {
      return "Hello!";
    } else {
      return "Hello " + name + "!";
    }
  }
}
