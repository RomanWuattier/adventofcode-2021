package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

/** Day 4: Giant Squid */
public class Day4 implements Day {
  public static void main(String[] args) {
    new Day4().printParts();
  }

  private final String[] entries = readDayAsString(4).split("\n\n");
  private final int[] order =
      Arrays.stream(entries[0].split(",")).map(Integer::parseInt).mapToInt(i -> i).toArray();

  @Override
  public Object part1() {
    return run(Board.build(entries), true, (first, __) -> first);
  }

  @Override
  public Object part2() {
    return run(Board.build(entries), false, (__, second) -> second);
  }

  private int run(Board[] boards, boolean takeFirst, BinaryOperator<Board> reducer) {
    var winners = new HashSet<Integer>();
    var output = -1;
    for (int num : order) {
      var reducedWinner =
          Arrays.stream(boards)
              .filter(board -> !winners.contains(board.id) && board.mark(num))
              .peek(winner -> winners.add(winner.id))
              .reduce(reducer);

      if (reducedWinner.isPresent()) {
        winners.add(reducedWinner.get().id);
        output = num * reducedWinner.get().unmarked;
        if (takeFirst) {
          return output;
        }
      }
    }
    return output;
  }

  private static class Board {
    private final int id;
    private final int width;
    private final int height;
    private final int[] rows;
    private final int[] cols;
    private final Map<Integer, Integer> pos = new HashMap<>();
    private int unmarked;

    private Board(int id, int[] nums) {
      this.id = id;
      this.width = 5;
      this.height = 5;
      this.rows = new int[width];
      this.cols = new int[height];
      IntStream.range(0, nums.length).forEach(i -> this.pos.put(nums[i], i));
      this.unmarked = Arrays.stream(nums).sum();
    }

    boolean mark(int num) {
      var position = pos.get(num);
      if (position == null) {
        return false;
      }
      unmarked -= num;
      return ++rows[position / width] == width || ++cols[position % height] == height;
    }

    static Board[] build(String[] entries) {
      return IntStream.range(1, entries.length)
          .mapToObj(
              i -> {
                var nums =
                    Arrays.stream(entries[i].split("\\W+"))
                        .filter(s -> !s.isBlank())
                        .mapToInt(Integer::parseInt)
                        .toArray();
                return new Board(i, nums);
              })
          .toArray(Board[]::new);
    }
  }
}
