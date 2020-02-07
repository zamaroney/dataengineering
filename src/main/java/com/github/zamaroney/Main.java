package com.github.zamaroney;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException, CsvValidationException{

    CsvParse csvP = new CsvParse("src/data/SEOExample-1.csv");
    csvP.printCsv();
  }
}
