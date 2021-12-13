package com.romanwuattier.adventofcode2021.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

  @Test
  void part1() {
    assertEquals(675, new Day13().part1());
  }

  @Test
  void part2() {
    assertEquals(
        "\n"
            + "#  # #### #  # #  # #### ####   ## ####\n"
            + "#  #    # # #  #  # #    #       #    #\n"
            + "####   #  ##   #### ###  ###     #   # \n"
            + "#  #  #   # #  #  # #    #       #  #  \n"
            + "#  # #    # #  #  # #    #    #  # #   \n"
            + "#  # #### #  # #  # #    ####  ##  ####\n",
        new Day13().part2());
  }
}
