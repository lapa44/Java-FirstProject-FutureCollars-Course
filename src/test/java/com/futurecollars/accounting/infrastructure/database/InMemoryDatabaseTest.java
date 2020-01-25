package com.futurecollars.accounting.infrastructure.database;

class InMemoryDatabaseTest extends DatabaseTest {

  @Override
  Database getDatabase() {
    return new InMemoryDatabase();
  }
}
