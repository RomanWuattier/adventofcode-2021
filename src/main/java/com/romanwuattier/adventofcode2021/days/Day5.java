package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/** Day 5: Hydrothermal Venture */
public class Day5 implements Day {
  public static void main(String[] args) {
    new Day5().printParts();
  }

  private final List<Line> lines =
      readDay(5).stream()
          .map(l -> l.split(" -> "))
          .map(
              a -> {
                var p1 = a[0].split(",");
                var p2 = a[1].split(",");
                return new Line(
                    Integer.parseInt(p1[0]),
                    Integer.parseInt(p1[1]),
                    Integer.parseInt(p2[0]),
                    Integer.parseInt(p2[1]));
              })
          .collect(Collectors.toList());

  private final int maxHeight =
      lines.stream().map(l -> Math.max(l.y1, l.y2)).max(Comparator.naturalOrder()).orElseThrow();

  @Override
  public Object part1() {
    return run(l -> l.x1 == l.x2 || l.y1 == l.y2);
  }

  @Override
  public Object part2() {
    return run(__ -> true);
  }

  private long run(Predicate<Line> lineFilter) {
    var points = new HashMap<Integer, Integer>();
    lines.stream()
        .filter(lineFilter)
        .forEach(
            l -> {
              int x1 = l.x1, y1 = l.y1, x2 = l.x2, y2 = l.y2;
              if (x1 == x2) {
                var sortedY = new int[] {y1, y2};
                Arrays.sort(sortedY);
                for (int y = sortedY[0]; y <= sortedY[1]; y++) {
                  var k = x1 * maxHeight + y;
                  points.put(k, points.getOrDefault(k, 0) + 1);
                }
              } else if (y1 == y2) {
                var sortedX = new int[] {x1, x2};
                Arrays.sort(sortedX);
                for (int x = sortedX[0]; x <= sortedX[1]; x++) {
                  var k = x * maxHeight + y1;
                  points.put(k, points.getOrDefault(k, 0) + 1);
                }
              } else {
                var sorted = new int[][] {{x1, y1}, {x2, y2}};
                Arrays.sort(sorted, Comparator.comparingInt(i -> i[0]));
                int xa = sorted[0][0], xb = sorted[1][0], ya = sorted[0][1], yb = sorted[1][1];
                var dy = ya > yb ? -1 : 1;
                for (int i = 0; i <= xb - xa; i++) {
                  int x = xa + i, y = ya + (dy * i);
                  var k = x * maxHeight + y;
                  points.put(k, points.getOrDefault(k, 0) + 1);
                }
              }
            });
    return points.values().stream().filter(v -> v > 1).count();
  }

  private record Line(int x1, int y1, int x2, int y2) {}
}
