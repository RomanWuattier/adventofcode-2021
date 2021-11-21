package com.romanwuattier.adventofcode2021.common;

public interface Day extends FileOperations {
  Object part1();

  Object part2();

  default void printParts() {
    System.out.printf("""
      Part 1: %s
      Part 2: %s
      """, part1(), part2());
  }
}
