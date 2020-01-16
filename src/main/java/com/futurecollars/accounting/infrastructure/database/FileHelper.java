package com.futurecollars.accounting.infrastructure.database;

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

public class FileHelper {

  String path;
  String line;

  public FileHelper(String path) {
    this.path = path;
//    this.line = line;
  }

  public void writeLineToFile(String line) {

    try {
      BufferedWriter writer = new BufferedWriter(
          new FileWriter(path, true));
      writer.append(line).append("\r\n");
      writer.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public List<String> readLinesFromFile(String path)
      throws FileNotFoundException {

    if (path == null) {
      throw new IllegalArgumentException("File path cannot by empty.");
    }

    List<String> listOfLines = new LinkedList<>();

    try (BufferedReader lineFromFile = new BufferedReader(
        new FileReader(path))) {
      while (lineFromFile.ready()) {
        listOfLines.add(lineFromFile.readLine());
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return listOfLines;
  }

  public void deleteLineFromFile(String path, int numberOfLine)
      throws IOException {

    if (path == null) {
      throw new IllegalArgumentException("File path cannot by empty.");
    }
    if (new File(path).length() == 0) {
      throw new IllegalArgumentException(
          "File is empty, nothing to remove.");
    }

    FileHelper fileHelper = new FileHelper(path);
    LinkedList<String> linesFromFile = (LinkedList<String>) fileHelper
        .readLinesFromFile(path);
    new FileWriter(path, false).close();
    linesFromFile.forEach(fileHelper::writeLineToFile);

    //    LinkedList<String> linesFromFile = (LinkedList<String>) fileHelper
//        .readLinesFromFile(path);
//    int sizeListBeforeRemoveLine = linesFromFile.size();
//    linesFromFile.forEach(System.out::println);
//    new FileWriter(path, false).close();
//    LinkedList<String> linesFromFile2 = (LinkedList<String>) fileHelper
//        .readLinesFromFile(path);
//    int sizeListAfterRemovingLine = linesFromFile2.size();
//    linesFromFile.forEach(System.out::println);

  }
}