/*
  Name:Robert Schwyzer
  Course:CNT 4714 Fall 2020
  Assignment title: Project 2 – Multi-threaded programming in Java
  Date: Sunday October 4, 2020

  Class: Station
*/

/**
 * Simulates a station which has an input and an output Conveyor alongside a workload of package groups to process
 */
public class Station {
  // Identifier
  int stationNumber;

  // Number of package groups remaining to process
  int workload;

  // Reference to input Conveyor
  Conveyor input;

  // Reference to output Conveyor
  Conveyor output;

  /**
   * Create a new routing station
   * @param number  Station's identifier number
   * @param work    number of package groups Station needs to move
   * @param i       input conveyor belt
   * @param o       output conveyor belt
   */
  public Station(int number, int work, Conveyor i, Conveyor o) {
    stationNumber = number;
    workload = work;
    input = i;
    output = o;
  }

  /**
   * @return  numeric identifier for this station
   */
  public int getStationNumber() {
    return stationNumber;
  }

  /**
   * @return  remaining workload this station has to process
   */
  public int getRemainingWorkload() {
    return workload;
  }

  /**
   * @return  Conveyor object this Station uses for input
   */
  public Conveyor getInput() {
    return input;
  }
  
  /**
   * @return  Conveyor object this Station uses for output
   */
  public Conveyor getOutput() {
    return output;
  }

  /**
   * Attempt to obtain a lock on the input Conveyor
   * @return  true if lock was successfully obtained, false if not
   */
  public boolean lockInput() {
    return input.setStation(this);
  }

  /**
   * Attempt to obtain a lock on the output Conveyor
   * @return  true if lock was successfully obtained, false if not
   */
  public boolean lockOutput() {
    return output.setStation(this);
  }

  /**
   * Release lock on input Conveyor
   */
  public void releaseInput() {
    input.releaseStation();
  }

  /**
   * Release lock on output Conveyor
   */
  public void releaseOutput() {
    output.releaseStation();
  }

  /**
   * Reduce workload by one
   */
  public void decrementWorkload() {
    workload--;
  }

  /**
   * Print station's current, remaining workload to stdout
   */
  public void printWorkload() {
    System.out.println("Routing Station " + stationNumber + " Has Total Workload Of " + workload);
  }
}
