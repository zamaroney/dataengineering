package com.github.zamaroney;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException, CsvValidationException {

    CsvParse csvP = new CsvParse("src/data/SEOExample-1.csv");
    csvP.printCsv();

    Gson gson = new Gson();
    JsonReader jread = new JsonReader(new FileReader("src/Data/authors.json"));
    AuthorParse[] authors = gson.fromJson(jread, AuthorParse[].class);

    for (var element : authors) {
      System.out.println(element.getName());
    }
  }
}
