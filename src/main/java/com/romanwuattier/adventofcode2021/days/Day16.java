package com.romanwuattier.adventofcode2021.days;

import com.romanwuattier.adventofcode2021.common.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Day 16: Packet Decoder */
public class Day16 implements Day {
  public static void main(String[] args) {
    new Day16().printParts();
  }

  private static final Map<Character, String> MAPPING = new HashMap<>();

  static {
    MAPPING.put('0', "0000");
    MAPPING.put('1', "0001");
    MAPPING.put('2', "0010");
    MAPPING.put('3', "0011");
    MAPPING.put('4', "0100");
    MAPPING.put('5', "0101");
    MAPPING.put('6', "0110");
    MAPPING.put('7', "0111");
    MAPPING.put('8', "1000");
    MAPPING.put('9', "1001");
    MAPPING.put('A', "1010");
    MAPPING.put('B', "1011");
    MAPPING.put('C', "1100");
    MAPPING.put('D', "1101");
    MAPPING.put('E', "1110");
    MAPPING.put('F', "1111");
  }

  private final Optional<String> line =
      readDay(16).stream()
          .map(hex -> hex.chars().mapToObj(c -> MAPPING.get((char) c)).collect(Collectors.joining()))
          .findFirst();

  @Override
  public Object part1() {
    return line.map(
                 bin -> {
                   var s = new Stack<Character>();
                   new StringBuilder(bin).reverse().toString().chars().mapToObj(c -> (char) c).forEach(s::push);
                   return run(s).version;
                 })
               .orElseThrow();
  }

  @Override
  public Object part2() {
    return line.map(
                 bin -> {
                   var s = new Stack<Character>();
                   new StringBuilder(bin).reverse().toString().chars().mapToObj(c -> (char) c).forEach(s::push);
                   return run(s).val;
                 })
               .orElseThrow();
  }
  private Pair run(Stack<Character> bin) {
    BiFunction<Stack<Character>, Integer, Integer> popN =
        (stack, n) -> {
          if (stack.size() < n) {
            throw new IllegalStateException();
          }
          var sb = new StringBuilder();
          while (--n >= 0) {
            sb.append(stack.pop());
          }
          return Integer.parseInt(sb.toString(), 2);
        };

    BiFunction<String, List<Long>, Long> op = (symbol, values) -> switch (symbol) {
      case "+" -> values.stream().mapToLong(l -> l).sum();
      case "*" -> values.stream().mapToLong(l -> l).reduce((a , b) -> a * b).orElseThrow();
      case "min" -> values.stream().mapToLong(l -> l).min().orElseThrow();
      case "max" -> values.stream().mapToLong(l -> l).max().orElseThrow();
      default -> throw new IllegalStateException("Unexpected op " + symbol);
    };

    BiFunction<String, List<Long>, Long> binOp = (symbol, values) -> switch (symbol) {
      case "gt" -> values.get(0) > values.get(1) ? 1L : 0L;
      case "lt" -> values.get(0) < values.get(1) ? 1L : 0L;
      case "eq" -> values.get(0).equals(values.get(1)) ? 1L : 0L;
      default -> throw new IllegalStateException("Unexpected bin op: " + symbol);
    };

    var version = popN.apply(bin, 3);
    var type = popN.apply(bin, 3);

    if (type == 4) {
      var val = new StringBuilder();
      while (true) {
        var prefix = popN.apply(bin, 1);
        IntStream.range(0, 4).forEach(__ -> val.append(bin.pop()));
        if (prefix == 0) {
          break;
        }
      }
      return new Pair(version, Long.parseLong(val.toString(), 2));
    }

    var lengthType = popN.apply(bin, 1);
    var subPackets = new ArrayList<Pair>();
    if (lengthType == 0) {
      var subLength = popN.apply(bin, 15);
      var subStack = new Stack<Character>();
      var sb = new StringBuilder();
      while (--subLength >= 0) {
        sb.append(bin.pop());
      }
      sb.reverse().toString().chars().mapToObj(c -> (char) c).forEach(subStack::push);
      while (!subStack.isEmpty()) {
        subPackets.add(run(subStack));
      }
    } else if (lengthType == 1) {
      var numSubPackets = popN.apply(bin, 11);
      while (--numSubPackets >= 0) {
        subPackets.add(run(bin));
      }
    } else {
      throw new IllegalStateException();
    }

    var subValues = subPackets.stream().map(p -> p.val).toList();
    var val = switch (type) {
      case 0 -> op.apply("+", subValues);
      case 1 -> op.apply("*", subValues);
      case 2 -> op.apply("min", subValues);
      case 3 -> op.apply("max", subValues);
      case 5 -> binOp.apply("gt", subValues);
      case 6 -> binOp.apply("lt", subValues);
      case 7 -> binOp.apply("eq", subValues);
      default -> throw new IllegalStateException("Unexpected type: " + type);
    };
    return new Pair(version + subPackets.stream().mapToLong(p -> p.version).sum(), val);
  }

  private record Pair(long version, long val) {}
}
