package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/** Day 13: Transparent Origami */
public class Day13 implements Day {
  public static void main(String[] args) {
    new Day13().printParts();
  }

  private static final BiFunction<Integer, Integer, Integer> FOLD_FORMULA =
      (val, limit) -> val < limit ? val : limit * 2 - val;

  private final List<String> input = readDay(13);
  private final Set<Point> dots =
      input.stream()
          .map(
              line -> {
                if (line.contains(",")) {
                  var a = line.split(",");
                  return new Point(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
                }
                return null;
              })
          .filter(Objects::nonNull)
          .collect(Collectors.toSet());
  private final List<String> foldInstructions =
      input.stream().filter(line -> line.startsWith("fold along")).toList();

  @Override
  public Object part1() {
    return fold(dots, foldInstructions.get(0)).size();
  }

  @Override
  public Object part2() {
    Set<Point> dots = this.dots;
    for (String instruction : foldInstructions) {
      dots = fold(dots, instruction);
    }

    int minx = dots.stream().min(Comparator.comparingInt(p -> p.x)).map(p -> p.x).orElseThrow();
    int maxx = dots.stream().max(Comparator.comparingInt(p -> p.x)).map(p -> p.x).orElseThrow();
    int miny = dots.stream().min(Comparator.comparingInt(p -> p.y)).map(p -> p.y).orElseThrow();
    int maxy = dots.stream().max(Comparator.comparingInt(p -> p.y)).map(p -> p.y).orElseThrow();
    var sb = new StringBuilder("\n");
    for (int y = miny; y <= maxy; y++) {
      for (int x = minx; x <= maxx; x++) {
        sb.append(dots.contains(new Point(x, y)) ? "#" : " ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  private Set<Point> fold(Set<Point> origin, String instruction) {
    var foldLine = instruction.split(" ")[2];
    var fold = foldLine.split("=");
    var axis = fold[0];
    var limit = Integer.parseInt(fold[1]);
    if ("x".equals(axis)) {
      return origin.stream()
          .map(dot -> new Point(FOLD_FORMULA.apply(dot.x, limit), dot.y))
          .collect(Collectors.toSet());
    } else if ("y".equals(axis)) {
      return origin.stream()
          .map(dot -> new Point(dot.x, FOLD_FORMULA.apply(dot.y, limit)))
          .collect(Collectors.toSet());
    } else {
      throw new IllegalArgumentException("Invalid fold instruction " + instruction);
    }
  }

  private record Point(int x, int y) {}
}
