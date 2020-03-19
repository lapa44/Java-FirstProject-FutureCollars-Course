package com.futurecollars.accounting.infrastructure.database;

import java.io.File;
import java.io.IOException;

class InFileDatabaseTest extends DatabaseTest {

  private String path = "src/main/resources/testFileDatabase.json";

  @Override
  Database getDatabase() throws IOException {
    return new InFileDatabase(path);
  }

  //  @BeforeEach
  void removeTestFileBeforeTest() {

    File file = new File(path);
    System.out.println("Checking if file exist...");
    if (file.exists()) {
      file.delete();
      System.out.println("File founded and deleted.");
    } else {
      System.out.println("File doesn't exist.");
    }
  }
}