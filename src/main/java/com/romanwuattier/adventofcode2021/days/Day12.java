package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Day 12: Passage Pathing */
public class Day12 implements Day {
  public static void main(String[] args) {
    new Day12().printParts();
  }

  private static final String START = "start";
  private static final String END = "end";

  private final Map<String, List<String>> graph = new HashMap<>();

  public Day12() {
    readDay(12).stream()
        .map(line -> line.split("-"))
        .forEach(
            a -> {
              graph.compute(a[0], (k, values) -> values == null ? new ArrayList<>() : values).add(a[1]);
              graph.compute(a[1], (k, values) -> values == null ? new ArrayList<>() : values).add(a[0]);
            });
  }

  @Override
  public Object part1() {
    var seen = new HashSet<String>();
    seen.add(START);
    return dfs(graph, START, seen, false);
  }

  @Override
  public Object part2() {
    var seen = new HashSet<String>();
    seen.add(START);
    return dfs(graph, START, seen, true);
  }

  private int dfs(
      Map<String, List<String>> g, String root, Set<String> seen, boolean canVisitSmallCaveTwice) {
    if (root.equals(END)) {
      return 1;
    }
    return g.get(root).stream()
        .mapToInt(
            neigh -> {
              var paths = 0;
              var isSmall = neigh.equals(neigh.toLowerCase());
              if (!isSmall || !seen.contains(neigh)) {
                seen.add(neigh);
                paths = dfs(g, neigh, seen, canVisitSmallCaveTwice);
                seen.remove(neigh);
              } else if (!neigh.equals(START) && canVisitSmallCaveTwice) {
                paths = dfs(g, neigh, seen, false);
              }
              return paths;
            })
        .sum();
  }
}
