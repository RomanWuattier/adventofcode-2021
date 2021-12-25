package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.LinkedHashSet;
import java.util.List;

/** Day 25: Sea Cucumber */
public class Day25 implements Day {
  public static void main(String[] args) {
    new Day25().printParts();
  }

  private final List<String> lines = readDay(25);
  private final int height = lines.size();
  private final int width = lines.get(0).length();

  @Override
  public Object part1() {
    return run();
  }

  @Override
  public Object part2() {
    return "There is no part 2";
  }

  private int run() {
    var east = new LinkedHashSet<Point>();
    var south = new LinkedHashSet<Point>();
    for (var y = 0; y < height; y++) {
      for (var x = 0; x < width; x++) {
        switch (lines.get(y).charAt(x)) {
          case '>': east.add(new Point(x, y));break;
          case 'v': south.add(new Point(x, y));break;
          case '.': /* valid */ break;
          default: throw new IllegalArgumentException("Invalid char " + lines.get(y).charAt(x));
        }
      }
    }

    var step = 1;
    while (true) {
      var anyMoved = false;
      var tmpEast = new LinkedHashSet<Point>();
      for (var p : east) {
        var target = new Point((p.x + 1) % width, p.y);
        var canMove = !east.contains(target) && !south.contains(target);
        tmpEast.add(canMove ? target : p);
        anyMoved |= canMove;
      }
      east = tmpEast;

      var tmpSouth = new LinkedHashSet<Point>();
      for (var p : south) {
        var target = new Point(p.x, (p.y + 1) % height);
        var canMove = !east.contains(target) && !south.contains(target);
        tmpSouth.add(canMove ? target : p);
        anyMoved |= canMove;
      }
      south = tmpSouth;

      if (!anyMoved) {
        break;
      }
      step++;
    }
    return step;
  }

  private record Point(int x, int y) {}
}
