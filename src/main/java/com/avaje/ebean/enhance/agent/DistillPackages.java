package com.avaje.ebean.enhance.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Distill packages into distinct top level packages for searching.
 */
class DistillPackages {

  /**
   * Distill the packages into distinct top level packages.
   */
  static List<String> distill(Collection<String> packages) {
    return new DistillPackages().add(packages).distill();
  }

  private TreeSet<String> treeSet = new TreeSet<String>();

  /**
   * Add packages that we want to distill.
   */
  DistillPackages add(Collection<String> packages) {
    treeSet.addAll(packages);
    return this;
  }

  /**
   * Distill the list of packages into distinct top level packages.
   */
  List<String> distill() {

    List<String> distilled = new ArrayList<String>();

    // build the distilled list
    for (String pack : treeSet) {
      if (notAlreadyContained(distilled, pack)) {
        distilled.add(pack);
      }
    }

    return distilled;
  }

  /**
   * Return true if the package is not already contained in the distilled list.
   */
  private boolean notAlreadyContained(List<String> distilled, String pack) {

    for (int i = 0; i < distilled.size(); i++) {
      if (pack.startsWith(distilled.get(i))) {
        return false;
      }
    }
    return true;
  }
}
