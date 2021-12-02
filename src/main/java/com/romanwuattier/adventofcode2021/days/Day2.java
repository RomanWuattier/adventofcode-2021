package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.List;

/** Day 2: Dive! */
public class Day2 implements Day {
  public static void main(String[] args) {
    new Day2().printParts();
  }

  private final List<SubmarineCommand> submarineCommands = readDay(2).stream().map(SubmarineCommand::parse).toList();

  @Override
  public Object part1() {
    return run((horizontal, down, up, depth) -> horizontal * (down - up));
  }

  @Override
  public Object part2() {
    return run((horizontal, down, up, depth) -> horizontal * depth);
  }

  private long run(CommandOutputOperator outputOp) {
    long horizontal = 0, down = 0, up = 0, depth = 0, aim = 0;
    for (var command : submarineCommands) {
      switch (command.dir()) {
        case "forward" -> {
          horizontal += command.val();
          depth += aim * command.val();
        }
        case "down" -> {
          down += command.val();
          aim += command.val();
        }
        case "up" -> {
          up += command.val();
          aim -= command.val();
        }
        case null, default -> throw new IllegalStateException("Invalid command " + command);
      }
    }
    return outputOp.apply(horizontal, down, up, depth);
  }

  private record SubmarineCommand(String dir, int val) {
    public static SubmarineCommand parse(String input) {
      var a = input.split(" ");
      return new SubmarineCommand(a[0], Integer.parseInt(a[1]));
    }
  }

  @FunctionalInterface
  private interface CommandOutputOperator {
    long apply(long horizontal, long down, long up, long depth);
  }
}
