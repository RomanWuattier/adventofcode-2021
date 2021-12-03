package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/** Day 3: Binary Diagnostic */
public class Day3 implements Day {
  public static void main(String[] args) {
    new Day3().printParts();
  }

  private final List<String> report = readDay(3).stream().toList();

  @Override
  public Object part1() {
    long gammaRate = 0, epsilonRate = 0;
    for (var j = 0; j < report.get(0).length(); j++) {
      long zeroes = 0, ones = 0;
      for (var bits : report) {
        if (bits.charAt(j) == '0') {
          zeroes++;
        } else {
          ones++;
        }
      }
      gammaRate = gammaRate << 1 | (zeroes >= ones ? 0 : 1);
      epsilonRate = epsilonRate << 1 | (zeroes >= ones ? 1 : 0);
    }
    return gammaRate * epsilonRate;
  }

  @Override
  public Object part2() {
    return run((ones, zeroes) -> ones >= zeroes ? '1' : '0')
        * run((ones, zeroes) -> ones >= zeroes ? '0' : '1');
  }

  private long run(BiFunction<Long, Long, Character> charFilter) {
    var toKeep = new ArrayList<>(report);
    for (var j = 0; toKeep.size() > 1 && j < toKeep.get(0).length(); j++) {
      long zeroes = 0, ones = 0;
      for (var bits : toKeep) {
        if (bits.charAt(j) == '0') {
          zeroes++;
        } else {
          ones++;
        }
      }
      for (var it = toKeep.iterator(); it.hasNext(); ) {
        if (it.next().charAt(j) != charFilter.apply(ones, zeroes)) {
          it.remove();
        }
      }
    }

    long output = 0;
    for (var c : toKeep.get(0).toCharArray()) {
      output = output << 1 | Character.getNumericValue(c);
    }
    return output;
  }
}
