package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/** Day 22: Reactor Reboot */
public class Day22 implements Day {
  public static void main(String[] args) {
    new Day22().printParts();
  }

  private final List<Cuboid> cuboids =
      readDay(22).stream()
          .map(
              line -> {
                var a = line.split(" ");
                var onOff = a[0];
                var matcher = Pattern.compile("-?\\d+").matcher(a[1]);
                var xyz = new ArrayList<Integer>();
                while (matcher.find()) {
                  xyz.add(Integer.parseInt(matcher.group()));
                }
                if (xyz.size() != 6) {
                  throw new IllegalStateException("Invalid line " + line);
                }
                return new Cuboid(
                  xyz.get(0), xyz.get(1), xyz.get(2), xyz.get(3), xyz.get(4), xyz.get(5), onOff.equals("on"));
              })
          .toList();

  @Override
  public Object part1() {
    var target = new Cuboid(-50, 50, -50, 50, -50, 50, true);
    var on = new HashSet<Point>();

    cuboids.stream()
      .map(cuboid -> cuboid.intersect(target, cuboid.on))
      .filter(Objects::nonNull)
      .forEach(cuboid -> {
        if (cuboid.on) {
          on.addAll(cuboid.points());
        } else {
          on.removeAll(cuboid.points());
        }
      });
    return on.size();
  }

  @Override
  public Object part2() {
    var all = new ArrayList<Cuboid>();
    for (var cuboid : cuboids) {
      var intersections = new ArrayList<Cuboid>();
      for (var c : all) {
        // Add the intersection to cancel the previous value in the list
        Optional.ofNullable(c.intersect(cuboid, !c.on)).ifPresent(intersections::add);
      }
      if (cuboid.on) {
        intersections.add(cuboid);
      }
      all.addAll(intersections);
    }
    return all.stream().mapToLong(Cuboid::sum).sum();
  }

  private record Cuboid(int x1, int x2, int y1, int y2, int z1, int z2, boolean on) {
    Cuboid intersect(Cuboid c, boolean on) {
      if (c.x2 < x1 || c.x1 > x2 || c.y1 > y2 || c.y2 < y1 ||c.z1 > z2 || c.z2 < z1) {
        return null;
      }
      return new Cuboid(
        Math.max(x1, c.x1), Math.min(x2, c.x2),
        Math.max(y1, c.y1), Math.min(y2, c.y2),
        Math.max(z1, c.z1), Math.min(z2, c.z2),
        on);
    }

    Set<Point> points() {
      var all = new HashSet<Point>();
      for (var x = x1; x <= x2; x++) {
        for (var y = y1; y <= y2; y++) {
          for (var z = z1; z <= z2; z++) {
            all.add(new Point(x, y, z));
          }
        }
      }
      return all;
    }

    long sum() {
      var m = on ? 1 : -1;
      return (x2 - x1 + 1L) * (y2 - y1 + 1L) * (z2 - z1 + 1L) * m;
    }
  }

  record Point(int x, int y, int z) {}
}
