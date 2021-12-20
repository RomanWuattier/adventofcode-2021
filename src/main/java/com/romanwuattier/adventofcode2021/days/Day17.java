package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

/** Day 17: Trick Shot */
public class Day17 implements Day {
  public static void main(String[] args) {
    new Day17().printParts();
  }

  private static final int STEPS = 1000;

  private final int[] xTarget = new int[] {240, 292};
  private final int[] yTarget = new int[] {-90, -57};

  @Override
  public Object part1() {
    int res = 0;
    for (int vx = -200; vx <= 200; vx++) {
      for (int vy = -200; vy <= 200; vy++) {
        res = Math.max(res, run(vx, vy));
      }
    }
    return res;
  }

  @Override
  public Object part2() {
    int res = 0;
    for (int vx = -1000; vx <= 1000; vx++) {
      for (int vy = -1000; vy <= 1000; vy++) {
        if (run(vx, vy) >= 0) {
          res++;
        }
      }
    }
    return res;
  }

  private int run(int vx, int vy) {
    int x = 0, y = 0;
    int maxY = 0;
    for (var step = 0; step < STEPS; step++) {
      x += vx;
      y += vy;
      maxY = Math.max(maxY, y);
      if (xTarget[0] <= x && x <= xTarget[1] && yTarget[0] <= y && y <= yTarget[1]) {
        return maxY;
      }
      vx += sign(vx);
      vy--;
    }
    return -1;
  }

  private int sign(int x) {
    if (x > 0) {
      return -1;
    } else if (x < 0) {
      return 1;
    }
    return 0;
  }
}
