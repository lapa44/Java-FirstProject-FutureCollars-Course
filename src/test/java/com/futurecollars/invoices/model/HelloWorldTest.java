package com.futurecollars.invoices.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HelloWorldTest {

  @Test
  public void testHelloWithEmptyName() {

    //given
    HelloWorld helloWorld = new HelloWorld();

    //then
    assertEquals("Hello!", helloWorld.message());

  }

  @Test
  public void testHelloWithName() {

    //given
    HelloWorld helloWorld = new HelloWorld();

    //when
    helloWorld.setName("Project 16");

    //then
    assertEquals("Hello Project 16!", helloWorld.message());
  }
}