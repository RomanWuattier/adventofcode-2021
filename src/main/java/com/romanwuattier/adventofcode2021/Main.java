package com.romanwuattier.adventofcode2021;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) throws Exception {
    var classes = findAllClasses("com.romanwuattier.adventofcode2021.days");
    for (int i = 0; i < classes.size(); i++) {
      var c = classes.get(i);
      System.out.println("=== Day " + (i + 1) + " ===");
      c.getMethod("printParts").invoke(c.getConstructor().newInstance());
      System.out.println();
    }
  }

  private static List<Class<?>> findAllClasses(String pkg) {
    var inStream =
        ClassLoader.getSystemClassLoader().getResourceAsStream(pkg.replaceAll("[.]", "/"));
    return inStream == null
        ? List.of()
        : new BufferedReader(new InputStreamReader(inStream))
            .lines()
            .map(name -> getClass(name, pkg))
            .filter(c -> !c.isMemberClass() && !c.isAnonymousClass() && !c.isLocalClass())
            .collect(Collectors.toList());
  }

  private static Class<?> getClass(String name, String pkg) {
    try {
      return Class.forName(pkg + "." + name.substring(0, name.lastIndexOf('.')));
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Class not found" + e);
    }
  }
}
