package com.futurecollars.accounting;

public class HelloWorld {

  private String name = "";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String message() {
    if (name.equals("")) {
      return "Hello!";
    } else {
      return "Hello " + name + "!";
    }
  }
}
