package com.futurecollars.accounting.infrastructure.database;

import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {

  private final String path;

  public FileHelper(String path) {
    if (path == null) {
      throw new IllegalArgumentException("File path cannot by empty.");
    }
    this.path = path;
  }

  public void writeLineToFile(String line) throws IOException {

    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(path, true))) {
      bufferedWriter.append(line).append("\r\n");
    }
  }

  public List<String> readLinesFromFile()
      throws IOException {

    List<String> listOfLinesFromFile;

    try (BufferedReader lineFromFile = new BufferedReader(
        new FileReader(path))) {
      listOfLinesFromFile = lineFromFile.lines()
          .collect(Collectors.toList());
    }
    return listOfLinesFromFile;
  }


  public void deleteLineFromFile(int lineNumber)
      throws IOException {

    if (new File(path).length() < lineNumber) {
      throw new IllegalArgumentException(
          "Usunięcie danego wiersza jest niemożliwe.");
    }

    List<String> linesFromFile = readLinesFromFile();
    FileWriter fileWriter = new FileWriter(path, false);
    linesFromFile.remove(lineNumber);
    for (String s : linesFromFile) {
      fileWriter.write(s + "\r\n");
    }
    fileWriter.close();
  }
}