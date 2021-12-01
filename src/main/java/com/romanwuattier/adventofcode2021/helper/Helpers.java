package com.romanwuattier.adventofcode2021.helper;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Helpers {
  private Helpers() {}

  public static <T> Stream<List<T>> sliding(List<T> l, int size) {
    if (size > l.size()) {
      return Stream.empty();
    }
    return IntStream.rangeClosed(0, l.size() - size)
        .mapToObj(start -> l.subList(start, start + size));
  }
}
