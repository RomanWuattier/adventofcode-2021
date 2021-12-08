package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Day 8: Seven Segment Search */
public class Day8 implements Day {
  public static void main(String[] args) {
    new Day8().printParts();
  }

  private final List<Pair> input =
      readDay(8).stream()
          .map(l -> l.split(" \\| "))
          .map(
              a ->
                  new Pair(
                      Arrays.stream(a[0].split(" ")).toArray(String[]::new),
                      Arrays.stream(a[1].split(" ")).toArray(String[]::new)))
          .toList();

  @Override
  public Object part1() {
    return input.stream()
        .flatMap(p -> Arrays.stream(p.segments))
        .filter(seg -> Set.of(2, 3, 4, 7).contains(seg.length()))
        .count();
  }

  @Override
  public Object part2() {
    return input.stream()
        .map(
            p -> {
              var identifiableNumber =
                  Arrays.stream(p.signals)
                      .filter(sig -> Set.of(2, 4).contains(sig.length()))
                      .collect(
                          Collectors.toMap(sig -> sig.length() == 2 ? 1 : 4, Function.identity()));

              var one = identifiableNumber.get(1);
              var four = identifiableNumber.get(4);
              var fourMinusOne = minusExcept(four, one);
              var segLengthToNumber =
                  Map.<Integer, Function<String, String>>of(
                      2, __ -> "1",
                      4, __ -> "4",
                      3, __ -> "7",
                      7, __ -> "8",
                      5,
                          s -> {
                            if (containAll(s, one)) {
                              return "3";
                            } else if (containAll(s, fourMinusOne)) {
                              return "5";
                            }
                            return "2";
                          },
                      6,
                          s -> {
                            if (containAll(s, four)) {
                              return "9";
                            } else if (containAll(s, one)) {
                              return "0";
                            }
                            return "6";
                          });

              return Integer.parseInt(
                  Arrays.stream(p.segments)
                      .map(seg -> segLengthToNumber.get(seg.length()).apply(seg))
                      .collect(Collectors.joining()));
            })
        .mapToLong(i -> i)
        .sum();
  }

  private boolean containAll(String source, String pattern) {
    for (char c : pattern.toCharArray()) {
      if (source.indexOf(c) == -1) {
        return false;
      }
    }
    return true;
  }

  private String minusExcept(String source, String toRemove) {
    var sb = new StringBuilder();
    for (char c : source.toCharArray()) {
      if (toRemove.indexOf(c) == -1) {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  private record Pair(String[] signals, String[] segments) {}
}
