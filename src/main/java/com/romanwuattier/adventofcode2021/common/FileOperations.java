package com.romanwuattier.adventofcode2021.common;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

interface FileOperations {
  default List<String> readDay(int day) {
    return readResource("day" + day + ".txt");
  }

  default List<String> readResource(String resource) {
    return readAllLines(
        new File(
            Objects.requireNonNull(FileOperations.class.getClassLoader().getResource(resource))
                .getFile()));
  }

  default List<String> readAllLines(File file) {
    try {
      return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException("Resource doesn't exist", e);
    }
  }

  default String readDayAsString(int day) {
    return getFileAsString(
        new File(
            Objects.requireNonNull(
                    FileOperations.class.getClassLoader().getResource("day" + day + ".txt"))
                .getFile()));
  }

  default String getFileAsString(File file) {
    try {
      return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
