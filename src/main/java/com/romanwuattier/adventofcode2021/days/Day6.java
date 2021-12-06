package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.Arrays;
import java.util.List;

/** Day 6: Lanternfish */
public class Day6 implements Day {
  public static void main(String[] args) {
    new Day6().printParts();
  }

  private final List<Integer> fish =
      readDay(6).stream().flatMap(s -> Arrays.stream(s.split(","))).map(Integer::parseInt).toList();

  @Override
  public Object part1() {
    return run(80);
  }

  @Override
  public Object part2() {
    return run(256);
  }

  private long run(int days) {
    var calendar0 = 0L;
    var calendar = new long[9];
    fish.forEach(f -> calendar[f]++);
    for (int day = 0; day < days; day++) {
      calendar0 = Math.max(calendar[0], 0);
      for (int i = 1; i < 9; i++) {
        calendar[i - 1] = calendar[i];
        calendar[i] = 0;
      }
      calendar[8] = calendar0;
      calendar[6] += calendar0;
    }
    return Arrays.stream(calendar).sum();
  }
}
