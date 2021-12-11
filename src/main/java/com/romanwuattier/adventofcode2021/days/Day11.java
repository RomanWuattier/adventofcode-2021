package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/** Day 11: Dumbo Octopus */
public class Day11 implements Day {
  public static void main(String[] args) {
    new Day11().printParts();
  }

  private static final int[][] DIRS =
      new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, -1}, {-1, 1}};

  private final int[][] grid =
      readDay(11).stream()
          .map(line -> line.split(""))
          .map(a -> Arrays.stream(a).map(Integer::parseInt).mapToInt(i -> i).toArray())
          .toArray(size -> new int[size][1]);
  private final int height = grid.length;
  private final int width = grid[0].length;

  @Override
  public Object part1() {
    return run(100, new MutableCount(), null);
  }

  @Override
  public Object part2() {
    return run(1000, null, new MutableCount());
  }

  private int run(int steps, MutableCount action1, MutableCount action2) {
    var copy = Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);

    for (int step = 0; step < steps; step++) {
      var toFlash = new LinkedList<Point>();
      for (var y = 0; y < height; y++) {
        for (var x = 0; x < width; x++) {
          if (copy[y][x] == 9) {
            toFlash.offer(new Point(x, y));
            copy[y][x] = 0;
          } else {
            copy[y][x]++;
          }
        }
      }

      Optional.ofNullable(action2).ifPresent(count -> count.c = toFlash.size());
      bfs(copy, height, width, toFlash, action1, action2);

      if (Optional.ofNullable(action2).map(count -> count.c).orElse(0) == height * width) {
        return step + 1;
      }
    }
    return action1.c;
  }

  private void bfs(
      int[][] grid, int height, int width, Queue<Point> q, MutableCount action1, MutableCount action2) {
    while (!q.isEmpty()) {
      Optional.ofNullable(action1).ifPresent(count -> count.c++);
      var p = q.remove();
      for (var d : DIRS) {
        var dy = p.y + d[0];
        var dx = p.x + d[1];
        if (dy >= 0 && dy < height && dx >= 0 && dx < width) {
          if (grid[dy][dx] == 9) {
            Optional.ofNullable(action2).ifPresent(count -> count.c++);
            q.offer(new Point(dx, dy));
            grid[dy][dx] = 0;
          } else if (grid[dy][dx] != 0) {
            grid[dy][dx]++;
          }
        }
      }
    }
  }

  private static class MutableCount {
    private int c = 0;
  }

  private record Point(int x, int y) {}
}
