package com.futurecollars.accounting.infrastructure.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {

  private final String path;
  private final String endOfTheLine;

  public FileHelper(String path) {
    if (path == null) {
      throw new IllegalArgumentException("File path cannot by empty.");
    }
    if (!Files.isDirectory(Paths.get(path).getParent())) {
      throw new IllegalArgumentException("Directory does not exist.");
    }
    this.path = path;
    this.endOfTheLine = "\r\n";
  }

  public void writeLineToFile(String line) throws IOException {

    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(path, true))) {
      bufferedWriter.append(line).append(endOfTheLine);
    }
  }

  public List<String> readLinesFromFile()
      throws IOException {

    try (BufferedReader lineFromFile = new BufferedReader(
        new FileReader(path))) {
      return lineFromFile.lines().collect(Collectors.toList());
    }
  }

  public void deleteLineFromFile(int lineNumber)
      throws IOException {

    if (new File(path).length() < lineNumber) {
      throw new IllegalArgumentException(
          "Removing the given row is not possible.");
    }

    List<String> linesFromFile = readLinesFromFile();
    FileWriter fileWriter = new FileWriter(path, false);
    linesFromFile.remove(lineNumber);
    for (String s : linesFromFile) {
      fileWriter.write(s + endOfTheLine);
    }
    fileWriter.close();
  }
}