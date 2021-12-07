package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;
import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

/** Day 7: The Treachery of Whales */
public class Day7 implements Day {
  public static void main(String[] args) {
    new Day7().printParts();
  }

  private final int[] crabs =
      readDay(7).stream()
          .flatMap(l -> Arrays.stream(l.split(",")))
          .mapToInt(Integer::parseInt)
          .toArray();

  @Override
  public Object part1() {
    var median = Arrays.stream(crabs).sorted().toArray()[crabs.length / 2];
    return findCost(median, IntUnaryOperator.identity());
  }

  @Override
  public Object part2() {
    var meanFloor = Arrays.stream(crabs).sum() / crabs.length;
    return findCost(meanFloor, meanFloor + 1);
  }

  private long findCost(int median, IntUnaryOperator moveCost) {
    return Arrays.stream(crabs).map(crab -> Math.abs(crab - median)).map(moveCost).sum();
  }

  private long findCost(int meanFloor, int meanCeil) {
    return Stream.of(meanFloor, meanCeil)
        .mapToLong(mean -> findCost(mean, i -> i * (i + 1) / 2))
        .min()
        .getAsLong();
  }
}
