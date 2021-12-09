package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/** Day 9: Smoke Basin */
public class Day9 implements Day {
  public static void main(String[] args) {
    new Day9().printParts();
  }

  private final int[][] grid =
      readDay(9).stream()
          .map(l -> l.split(""))
          .map(a -> Arrays.stream(a).map(Integer::parseInt).mapToInt(i -> i).toArray())
          .toArray(size -> new int[size][1]);
  private final int height = grid.length;
  private final int width = grid[0].length;

  @Override
  public Object part1() {
    return lowPoints().stream().mapToInt(p -> p.val + 1).sum();
  }

  @Override
  public Object part2() {
    return lowPoints().stream()
        .map(p -> dfs(p.x, p.y))
        .sorted((a, b) -> b - a)
        .limit(3)
        .reduce(1, (left, right) -> left * right);
  }

  private List<Point> lowPoints() {
    return IntStream.range(0, height)
        .boxed()
        .flatMap(
            y ->
                IntStream.range(0, width)
                    .mapToObj(
                        x -> {
                          var val = grid[y][x];
                          if (x > 0 && grid[y][x - 1] <= val
                              || y > 0 && grid[y - 1][x] <= val
                              || x < width - 1 && grid[y][x + 1] <= val
                              || y < height - 1 && grid[y + 1][x] <= val) {
                            return null;
                          } else {
                            return new Point(x, y, val);
                          }
                        })
                    .filter(Objects::nonNull))
        .toList();
  }

  private int dfs(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height || grid[y][x] == 9) {
      return 0;
    }
    grid[y][x] = 9;
    return 1 + dfs(x + 1, y) + dfs(x - 1, y) + dfs(x, y + 1) + dfs(x, y - 1);
  }

  private record Point(int x, int y, int val) {}
}
