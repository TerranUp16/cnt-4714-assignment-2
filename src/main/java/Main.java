/*
  Name:Robert Schwyzer
  Course:CNT 4714 Fall 2020
  Assignment title: Project 2 – Multi-threaded programming in Java
  Date: Sunday October 4, 2020

  Class: Main
*/
public class Main {
  // Maintain consistent reference to Factory object
  static Factory fac;

  // Keep track of how many stations are actively executing -- included here instead of in Factory for convenience and performance
  static int stationsGoing;

  /**
   * This method should only be called by StationThread threads which have completed their workloads.
   * 
   * On each call, decrements stationsGoing.
   * 
   * When stationsGoing reaches 0, prints a message that all workloads have completed.
   * 
   * TROUBLESHOOTING: If ALL WORKLOADS COMPLETE message appears multiple times, then check logs to see if a thread executed more than once
   */
  public static void allDone() {
    stationsGoing--;

    if (stationsGoing <= 0) {
      System.out.printf("* * * * ALL WORKLOADS COMPLETE * * * SIMULATION ENDS * * * *%n%n");
    }
  }
  public static void main(String[] args) {
    // Create Factory (main, management class)
    fac = new Factory();

    // Read input file
    fac.readInput();

    // Get the total number of stations
    stationsGoing = fac.getStationCount();

    // Declare start of simulation
    System.out.println("* * * SIMULATION BEGINS * * *" + String.format("%n"));

    for (Station station : fac.getStations()) {
      station.printWorkload();
    }

    // Print a couple of returns for output formatting
    System.out.printf("%n%n");

    for (Station station : fac.getStations()) {
      StationThread thread = new StationThread(station);
      thread.start();
    }
  }
}
