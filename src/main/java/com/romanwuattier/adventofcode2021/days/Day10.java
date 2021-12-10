package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.IntStream;

/** Day 10: Syntax Scoring */
public class Day10 implements Day {
  public static void main(String[] args) {
    new Day10().printParts();
  }

  private static final Map<Character, Integer> OPEN =
      Map.of(
          '(', 1,
          '[', 2,
          '{', 3,
          '<', 4);

  private static final Map<Character, Character> OPEN_MATCHING_CLOSE =
      Map.of(
          ')', '(',
          ']', '[',
          '}', '{',
          '>', '<');

  private static final Map<Character, Long> ERROR_SCORE =
      Map.of(
          ')', 3L,
          ']', 57L,
          '}', 1197L,
          '>', 25137L);

  private final List<String> lines = readDay(10);

  @Override
  public Object part1() {
    var errors = new int[256];
    lines.forEach(
        line -> {
          var opens = new Stack<Character>();
          line.chars()
              .mapToObj(i -> (char) i)
              .takeWhile(
                  c -> {
                    if (OPEN.containsKey(c)) {
                      opens.push(c);
                    } else if (!opens.pop().equals(OPEN_MATCHING_CLOSE.get(c))) {
                      errors[c]++;
                      return false;
                    }
                    return true;
                  })
              .forEach(__ -> { /* consume the stream */ });
        });
    return IntStream.range(0, 256)
        .filter(i -> errors[i] != 0)
        .mapToLong(i -> errors[i] * ERROR_SCORE.get((char) i))
        .sum();
  }

  @Override
  public Object part2() {
    var score =
        lines.stream()
            .mapToLong(
                line -> {
                  var opens = new Stack<Character>();
                  var isIncomplete =
                      line.chars()
                          .mapToObj(i -> (char) i)
                          .allMatch(
                              c -> {
                                if (OPEN.containsKey(c)) {
                                  opens.push(c);
                                  return true;
                                } else {
                                  return opens.pop().equals(OPEN_MATCHING_CLOSE.get(c));
                                }
                              });
                  if (isIncomplete) {
                    return IntStream.range(0, opens.size())
                        .mapToLong(__ -> OPEN.get(opens.pop()))
                        .reduce(0L, (left, right) -> 5 * left + right);
                  }
                  return 0L;
                })
            .filter(l -> l != 0)
            .sorted()
            .toArray();
    return score[score.length / 2];
  }
}
