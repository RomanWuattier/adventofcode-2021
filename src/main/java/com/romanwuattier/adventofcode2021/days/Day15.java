package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.Arrays;

/** Day 15: Chiton */
public class Day15 implements Day {
  public static void main(String[] args) {
    new Day15().printParts();
  }

  private static final int[][] DIRS = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

  private final int[][] grid =
      readDay(15).stream()
          .map(l -> l.split(""))
          .map(a -> Arrays.stream(a).map(Integer::parseInt).mapToInt(i -> i).toArray())
          .toArray(size -> new int[size][1]);
  private final int m = grid.length;
  private final int n = grid[0].length;

  @Override
  public Object part1() {
    return run(expand(grid, m, n, 1), m, n);
  }

  @Override
  public Object part2() {
    return run(expand(grid, m, n, 5), m * 5, n * 5);
  }

  // Bottom-up approach
  private int run(int[][] grid, int m, int n) {
    int[][] sum = new int[m][n];
    for (var y = 0; y < m; y++) {
      for (var x = 0; x < n; x++) {
        sum[y][x] = Integer.MAX_VALUE - 10;
      }
    }
    sum[m - 1][n - 1] = 0;

    var hasConverged = false;
    while (!hasConverged) {
      var prev = sum[0][0];
      for (var y = m - 1; y >= 0; y--) {
        for (var x = n - 1; x >= 0; x--) {
          var min = Integer.MAX_VALUE;
          for (var dir : DIRS) {
            int dy = y + dir[0], dx = x + dir[1];
            if (dy >= 0 && dy < m && dx >= 0 && dx < n) {
              min = Math.min(min, grid[dy][dx] + sum[dy][dx]);
            }
          }
          sum[y][x] = Math.min(sum[y][x], min);
        }
      }
      hasConverged = sum[0][0] == prev;
    }
    return sum[0][0];
  }

  private int[][] expand(int[][] grid, int m, int n, int times) {
    int[][] expanded = new int[m * times][n * times];
    for (var y = 0; y < m; y++) {
      for (var x = 0; x < n; x++) {
        for (var j = 0; j < times; j++) {
          for (var i = 0; i < times; i++) {
            var val = grid[y][x] + j + i;
            if (val > 9) {
              val %= 9;
            }
            expanded[y + j * m][x + i * n] = val;
          }
        }
      }
    }
    return expanded;
  }
}
