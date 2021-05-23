package com.group76pos;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Memento {
  String state;

  Memento(String state) {
    this.state = state;
  }

  public void saveToFile(String path) throws IOException {
    FileWriter writer = new FileWriter(path);
    writer.write(this.state);
    writer.close();
  }

  public static Memento loadFromFile(String path) throws IOException {
    String state = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    return new Memento(state);
  }
}