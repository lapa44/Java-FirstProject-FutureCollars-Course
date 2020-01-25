package com.futurecollars.accounting.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FileHelperTest {

  @BeforeEach
  void removeTestFileBeforeTest() {

    File file = new File("src\\main\\resources\\testFileDatabase.json");
    if (file.exists()) {
      file.delete();
    }
  }

  @Test
  void shouldWriteAndReadLineToFile() throws IOException {
    //given
    String line = "One line in file.";
    String path = "src\\main\\resources\\testFileDatabase.json";
    FileHelper fileHelper = new FileHelper(path);

    //when
    fileHelper.writeLineToFile(line);
    List<String> linesFromFile = fileHelper.readLinesFromFile();

    //then
    assertThat(linesFromFile).isNotEmpty();
    assertThat(linesFromFile.get(linesFromFile.size() - 1))
        .contains(line);
    assertThat(linesFromFile).hasSizeGreaterThan(0);
  }

  @Test
  void shouldRemoveLineFromFile() throws IOException {

    //given
    String line = "Sample single line in file.";
    String path = "src\\main\\resources\\testFileDatabase.json";
    int lineNumber = 0;
    FileHelper fileHelper = new FileHelper(path);

    //when
    fileHelper.writeLineToFile(line);
    fileHelper.writeLineToFile(line);
    fileHelper.writeLineToFile(line);
    fileHelper.writeLineToFile(line);
    fileHelper.writeLineToFile(line);
    List linesFromFileBefore = new ArrayList(
        Files.readAllLines(Paths.get(path)));
    fileHelper.deleteLineFromFile(lineNumber);
    List linesFromFileAfter = new ArrayList(
        Files.readAllLines(Paths.get(path)));

    //then
    linesFromFileBefore.remove(lineNumber);
    assertThat(linesFromFileBefore).isEqualTo(linesFromFileAfter);
  }
}