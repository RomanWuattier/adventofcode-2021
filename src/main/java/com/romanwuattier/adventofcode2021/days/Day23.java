package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

/** Day 23: Amphipod */
public class Day23 implements Day {
  public static void main(String[] args) {
    new Day23().printParts();
  }

  // Resolved manually
  // Input:
  // #############
  // #...........#
  // ###A#D#A#B###
  //  #C#C#D#B#
  //  #########

  @Override
  public Object part1() {
    return 13495;
  }

  @Override
  public Object part2() {
    return 53767;
  }
}
