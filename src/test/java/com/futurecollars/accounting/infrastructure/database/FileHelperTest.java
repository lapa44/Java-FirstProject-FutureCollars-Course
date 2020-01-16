package com.futurecollars.accounting.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

class FileHelperTest {

  @Test
  void shouldWriteAndReadLineToFile() throws FileNotFoundException {
    //given
    String line = "One line in file.";
    String path = "src\\main\\resources\\testFileDatabase.json";
    FileHelper fileHelper = new FileHelper(path);

    //when
    fileHelper.writeLineToFile(line);
    List<String> linesFromFile = fileHelper.readLinesFromFile(path);

    //then
    assertThat(linesFromFile).isNotEmpty();
    assertThat(linesFromFile.get(linesFromFile.size() - 1)).contains(line);
    assertThat(linesFromFile).hasSizeGreaterThan(0);
  }

  @Test
  void shouldRemoveLineFromFile() throws IOException {
    //given
    String path = "src\\main\\resources\\testFileDatabase.json";
    int numberOfLine = 1;
    FileHelper fileHelper = new FileHelper(path);

    //when
    fileHelper.deleteLineFromFile(path, numberOfLine);

    //then
  }
}