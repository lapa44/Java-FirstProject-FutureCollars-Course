package com.futurecollars.accounting.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    int numberOfLine = 0;
    FileHelper fileHelper = new FileHelper(path);

    //when
    List linesFromFileBefore = new ArrayList(
        Files.readAllLines(Paths.get(path)));
    fileHelper.deleteLineFromFile(path, numberOfLine);
    List linesFromFileAfter = new ArrayList(
        Files.readAllLines(Paths.get(path)));

    //then
    assertThat(linesFromFileBefore).isNotEqualTo(linesFromFileAfter);
  }
}