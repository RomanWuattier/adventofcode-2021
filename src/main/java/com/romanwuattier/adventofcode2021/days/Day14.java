package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.romanwuattier.adventofcode2021.helper.Helpers.sliding;

/** Day 14: Extended Polymerization */
public class Day14 implements Day {
  public static void main(String[] args) {
    new Day14().printParts();
  }

  private final String template = readDay(14).get(0).strip();
  private final Map<String, String> rules =
      readDay(14).stream()
          .filter(line -> line.contains("->"))
          .map(line -> line.split(" -> "))
          .collect(Collectors.toMap(a -> a[0], a -> a[1]));

  @Override
  public Object part1() {
    return run(10);
  }

  @Override
  public Object part2() {
    return run(40);
  }

  private long run(int steps) {
    var polymers = rules.keySet().stream().collect(Collectors.toMap(polymer -> polymer, __ -> 0L));
    var acc = rules.keySet().stream().collect(Collectors.toMap(polymer -> polymer, __ -> 0L));
    var counts = new HashMap<Character, Long>();

    sliding(template.chars().mapToObj(i -> (char) i).map(c -> Character.toString(c)).toList(), 2)
        .forEach(
            pair -> {
              var polymer = pair.get(0) + pair.get(1);
              polymers.put(polymer, polymers.get(polymer) + 1);
            });

    for (var step = 0; step < steps; step++) {
      for (var polymer : polymers.keySet()) {
        if (polymers.get(polymer) > 0) {
          var fromLeft = polymer.charAt(0) + rules.get(polymer);
          var fromRight = rules.get(polymer) + polymer.charAt(1);
          acc.put(fromLeft, acc.get(fromLeft) + polymers.get(polymer));
          acc.put(fromRight, acc.get(fromRight) + polymers.get(polymer));
        }
      }

      for (var polymer : acc.keySet()) {
        polymers.put(polymer, acc.get(polymer));
        acc.put(polymer, 0L);
      }
    }

    counts.put(template.charAt(template.length() - 1), 1L);
    for (var polymer : polymers.keySet()) {
      counts.putIfAbsent(polymer.charAt(0), 0L);
      counts.put(polymer.charAt(0), counts.get(polymer.charAt(0)) + polymers.get(polymer));
    }
    return Collections.max(counts.values()) - Collections.min(counts.values());
  }
}
