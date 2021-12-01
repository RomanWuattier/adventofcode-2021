package com.romanwuattier.adventofcode2021.days;

import static com.romanwuattier.adventofcode2021.helper.Helpers.sliding;

import com.romanwuattier.adventofcode2021.common.Day;
import java.util.List;
import java.util.stream.IntStream;

/** Day 1: Sonar Sweep */
public class Day1 implements Day {
  public static void main(String[] args) {
    new Day1().printParts();
  }

  private final List<Integer> depths = readDay(1).stream().map(Integer::parseInt).toList();

  @Override
  public Object part1() {
    return IntStream.range(0, depths.size() - 1)
        .filter(i -> depths.get(i) < depths.get(i + 1))
        .count();
  }

  @Override
  public Object part2() {
    var windowed = sliding(depths, 3).map(a -> a.stream().mapToInt(i -> i).sum()).toList();

    return IntStream.range(0, windowed.size() - 1)
        .filter(i -> windowed.get(i) < windowed.get(i + 1))
        .count();
  }
}
