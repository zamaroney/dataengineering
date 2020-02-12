package com.github.zamaroney;

import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.Data;
// just to check if these are found

public class BookStoreDB {

  private Connection connect() {
    // SQLite connection string
    String url = "jdbc:sqlite:./src/data/BookStore.db";
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }

  /** isnsert an array of authors into the author table in the database. */
  public void insertIntoAuthor(AuthorParse[] authors) {
    String sql = "INSERT INTO author(author_name, author_email, author_url) VALUES(?,?,?)";

    try (Connection conn = this.connect();
        Statement stmt = this.connect().createStatement();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery("SELECT author.author_name FROM author");
      ArrayList<String> currentAuthors = new ArrayList<>();
      while (rs.next()) {
        currentAuthors.add(rs.getString(1));
      }
      for (var element : authors) {
        if (!currentAuthors.contains(element.getName())) {
          pstmt.setString(1, element.getName());
          pstmt.setString(2, element.getEmail());
          pstmt.setString(3, element.getUrl());
          pstmt.execute();
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void insertIntoBooks(String booksFile) throws IOException, CsvValidationException {

    String sql =
        "INSERT INTO book(isbn, publisher_name, author_name, book_title) VALUES(?,?,?,?)";

    try (Connection conn = this.connect();
        Statement stmt = this.connect().createStatement();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery("SELECT  * from book");
      ArrayList<String> currentBooks = new ArrayList<>();
      while (rs.next()) {
        currentBooks.add(rs.getString(1));
      }
      try (BufferedReader read = new BufferedReader(new FileReader(booksFile))) {
        String line;
        while ((line = read.readLine()) != null) {
          String[] lineList = line.replace("\"", "").split(",");

          if(!currentBooks.contains(lineList[0]) && !lineList[0].equals("isbn")){
          pstmt.setString(1, lineList[0]);
          pstmt.setString(2, lineList[3]);
          pstmt.setString(3, lineList[2]);
          pstmt.setString(4,lineList[1]);
          pstmt.execute();
          }
        }
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
