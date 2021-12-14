package com.romanwuattier.adventofcode2021.days;

import static com.romanwuattier.adventofcode2021.helper.Helpers.sliding;

import com.romanwuattier.adventofcode2021.common.Day;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    var res = template;
    for (int step = 0; step < 10; step++) {
      res = run(res);
    }
    var count = new HashMap<Character, Long>();
    res.chars().mapToObj(i -> (char) i).forEach(c -> count.put(c, count.getOrDefault(c, 0L) + 1));
    var min = count.values().stream().min(Comparator.comparingLong(a -> a)).orElseThrow();
    var max = count.values().stream().max(Comparator.comparingLong(a -> a)).orElseThrow();
    return max - min;
  }

  @Override
  public Object part2() {
    return null;
  }

  private String run(String tmpl) {
    var sb = new StringBuilder();
    sliding(tmpl.chars().mapToObj(i -> (char) i).map(c -> Character.toString(c)).toList(), 2)
        .forEach(
            pair -> {
              var concat = pair.get(0) + pair.get(1);
              var toInsert = rules.get(concat);
              sb.append(pair.get(0)).append(toInsert);
            });
    return sb.append(template.charAt(template.length() - 1)).toString();
  }
}
