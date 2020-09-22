/*
  Name:Robert Schwyzer
  Course:CNT 4714 Fall 2020
  Assignment title: Project 2 – Multi-threaded programming in Java
  Date: Sunday October 4, 2020

  Class: Conveyor
*/

/**
 * Simulates a conveyor belt which can move packages from a Station. Can only move packages from one Station at a time
 */
public class Conveyor {
  // Identifier
  int number;

  // Reference to the Station which has a lock on this Conveyor
  Station inUseBy;

  /**
   * Initialize a new Conveyor which starts as idle
   * @param n integer identifier for the Conveyer
   */
  public Conveyor(int n) {
    number = n;
    inUseBy = null;
  }

  /**
   * @return  identifier number for this Conveyor
   */
  public int getNumber() {
    return number;
  }

  /**
   * @return  true if this Conveyor is being used by a Station
   */
  public boolean inUse() {
    return (inUseBy != null);
  }

  /**
   * Try to set the Conveyor to be used by parameter
   * @param station Station to try to set this conveyor to
   * @return        true if setting this Conveyor to use the provided station worked, false if it did not
   */
  public boolean setStation(Station station) {
    if (inUse()) {
      return false;
    } else {
      inUseBy = station;
      return true;
    }
  }

  /**
   * Release the current Station assigned to this Conveyor
   */
  public void releaseStation() {
    inUseBy = null;
  }
}
