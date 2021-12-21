package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.HashMap;
import java.util.Map;

/** Day 21: Dirac Dice */
public class Day21 implements Day {
  public static void main(String[] args) {
    new Day21().printParts();
  }

  // There is only one possibility to get 3 (1,1,1)
  // 3 possibilities to get 4 (1,2,1), (1,1,2), (2,1,1) ...
  // Avoid to create too many recursive branches as the sum remain the same
  private static final int[][] ROLLS =
      new int[][] {{3, 1}, {4, 3}, {5, 6}, {6, 7}, {7, 6}, {8, 3}, {9, 1}};

  private final int player1 = 2;
  private final int player2 = 1;

  private int die = 1;

  @Override
  public Object part1() {
    var pos = new int[] {player1, player2};
    var scores = new int[2];
    var turn = 0;
    while (true) {
      var who = turn % 2;
      pos[who] = (pos[who] + roll() + roll() + roll() - 1) % 10 + 1;
      scores[who] += pos[who];
      if (scores[who] >= 1000) {
        break;
      }
      turn++;
    }
    return Math.min(scores[0], scores[1]) * (turn + 1) * 3;
  }

  @Override
  public Object part2() {
    var res = run(0, player1, player2, 0, 0, new HashMap<>());
    return Math.max(res[0], res[1]);
  }

  private int roll() {
    var current = die;
    die = die % 100 + 1;
    return current;
  }

  private long[] run(
      int turn, int player1, int player2, int score1, int score2, Map<String, long[]> mem) {
    var pos = new int[] {player1, player2};
    var scores = new int[] {score1, score2};
    var who = turn % 2;
    if (scores[0] >= 21) {
      return new long[] {1, 0};
    }
    if (scores[1] >= 21) {
      return new long[] {0, 1};
    }
    var key = "t:" + turn + "p1:" + player1 + "p2:" + player2 + "s1:" + score1 + "s2:" + score2;
    if (mem.containsKey(key)) {
      return mem.get(key);
    }

    var res = new long[] {0, 0};
    for (var roll : ROLLS) {
      var sum = roll[0];
      var paths = roll[1];
      pos[who] = (pos[who] + sum - 1) % 10 + 1;
      scores[who] += pos[who];

      var tmp = run(turn + 1, pos[0], pos[1], scores[0], scores[1], mem);

      res[0] += paths * tmp[0];
      res[1] += paths * tmp[1];
      scores[who] -= pos[who];
      pos[who] = (pos[who] - sum - 1) % 10 + 1;
    }
    mem.put(key, res);
    return res;
  }
}
