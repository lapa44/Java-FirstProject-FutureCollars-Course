package com.futurecollars.accounting.infrastructure.database;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

class InFileDatabaseTest extends DatabaseTest {

  @Override
  Database getDatabase() {
    return new InFileDatabase();
  }

  @BeforeEach
  void removeTestFileBeforeTest() {

    File file = new File("src\\main\\resources\\testFileDatabase.json");
    System.out.println("Checking if file exist...");
    if (file.exists()) {
      file.delete();
      System.out.println("File founded and deleted.");
    } else {
      System.out.println("File doesn't exist.");
    }
  }
}