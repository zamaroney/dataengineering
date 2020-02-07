package com.github.zamaroney;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParse {

  private List fileRows = new ArrayList();

  public CsvParse(String infile) throws IOException, CsvValidationException {

    if (checkFile(infile)) {
      readCsv(infile);
    }
  }

  public void readCsv(String csvinfile) throws IOException, CsvValidationException {

    FileInputStream csvStream = new FileInputStream(csvinfile);
    InputStreamReader inputStream = new InputStreamReader(csvStream, StandardCharsets.UTF_8);
    CSVReader reader = new CSVReader(inputStream);

    String[] nextLine;
    while ((nextLine = reader.readNext()) != null) {
      fileRows.add(nextLine);
    }
  }

  protected void printCsv() {

    for (Object row : fileRows) {
      for (String fields : (String[]) row) {
        System.out.print(fields + ", ");
      }
      System.out.println("\b\b\n-----------------------");
    }
  }

  public boolean checkFile(String csvFile) {
    if (!Files.exists(Paths.get(csvFile), LinkOption.NOFOLLOW_LINKS)) {
      System.out.println("File does not exist.");
      return false;
    }
    return true;
  }
}
