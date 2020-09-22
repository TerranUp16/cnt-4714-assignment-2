/*
  Name:Robert Schwyzer
  Course:CNT 4714 Fall 2020
  Assignment title: Project 2 – Multi-threaded programming in Java
  Date: Sunday October 4, 2020

  Class: StationThread
*/

/**
 * Thread companion for Station class. Contains primary logic for executing Station's workload
 */
public class StationThread extends Thread {
  // Reference to an associated Station object
  Station station;

  // Time in ms which dictates how long Station waits while idling - lower values increase responsiveness at the cost of using more CPU cycles
  long pollrate;

  // Convenient property which holds a formatting prefix for log output from this Station
  String stationIdentifier;

  /**
   * Create a new StationThread
   * @param s reference to a Station object to use
   */
  public StationThread(Station s) {
    station = s;
    pollrate = 1000; // In ms
    stationIdentifier = "Routing Station " + station.getStationNumber() + ": "; // Ex- "Routing Station 4: "
  }

  /**
   * Sleep for pollrate ms
   * 
   * Typically used to wait before retrying to obtain a lock
   */
  void idle() {
    try {
      Thread.sleep(pollrate);
    } catch (InterruptedException e) {
      System.out.println("Sleep disturbed.");
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Sleep for a random number of seconds to simulate moving package groups
   */
  void workWork() {
    try {
      Thread.sleep((long)Math.random() * 1000);
    } catch (InterruptedException e) {
      System.out.println("Work interrupted.");
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Main logic for attempting to obtain Conveyor locks
   * 
   * If Conveyor locks successfully obtained, automatically calls doWork()
   */
  void getConveyors() {
    // Start by assuming this Station does not hold the locks to its Conveyors
    boolean input = false;
    boolean output = false;

    // Loop until this Station holds the locks for BOTH input and output Conveyors
    while (!input || !output) {
      // Ensure this Station attempts to get locks for both Conveyors
      input = false;
      output = false;

      // Checking and getting locks occurs in one operation to ensure state change between check and set do not occur
      if (station.getInput().setStation(station)) {
        // Obtained lock on input conveyor
        input = true;

        System.out.println(stationIdentifier + "holds lock on input conveyor C" + station.getInput().getNumber() + ".");
      }
      if (station.getOutput().setStation(station)) {
        // Obtained lock on input conveyor
        output = true;

        System.out.println(stationIdentifier + "holds lock on output conveyor C" + station.getOutput().getNumber() + ".");
      } else {
        // Lock not obtained on output conveyor
        if (input) {
          // If lock was obtained on input conveyor, release lock
          station.getInput().releaseStation();

          System.out.println(stationIdentifier + "unable to lock output conveyor - releasing lock on input conveyor C" + station.getInput().getNumber() + ".");
        }
      }

      if (output && !input) {
        // If lock was obtained on output conveyor but not input conveyor, release output conveyor lock
        station.getOutput().releaseStation();

        System.out.println(stationIdentifier + "unable to lock input conveyor - releasing lock on output conveyor C" + station.getOutput().getNumber() + ".");
      }

      if (!output || !input) {
        // If either lock was failed to be acquired, idle before retrying
        idle();
      }
    }

    // If thread gets here, then locks have been acquired and work can be done
    doWork();
  }

  /**
   * Only call this after doWork() completes successfully as this wil release whatever locks the target Conveyors have, regardless of whether this Station is the one which owns those locks
   */
  void releaseConveyors() {
    station.getInput().releaseStation();
    System.out.println(stationIdentifier + "unlocks input conveyor C" + station.getInput().getNumber() + ".");
    station.getOutput().releaseStation();
    System.out.println(stationIdentifier + "unlocks output conveyor C" + station.getOutput().getNumber() + ".");
  }

  /**
   * Simulate moving a package group. Release Conveyor locks when done
   */
  void doWork() {
    System.out.println(stationIdentifier + "**** Now moving packages. ****");

    // Sleep for a random amount of time to simulate work occurring
    workWork();
    System.out.println(stationIdentifier + "successfully moves packages into station on input conveyor " + station.getInput().getNumber() + ".");
    System.out.println(stationIdentifier + "successfully moves packages out of station on output conveyor " + station.getOutput().getNumber() + ".");

    // Reduce remaining workload
    station.decrementWorkload();
    System.out.printf("%shas %d package groups left to move.%n%n%n", stationIdentifier, station.getRemainingWorkload());

    // Release conveyors for other stations to use
    releaseConveyors();
  }

  /**
   * This executes when this object is started. Calls getConveyors() in a loop until the Station's remaining workload drops to 0
   */
  @Override
  public void run() {
    System.out.println(stationIdentifier + "input connection is set to conveyor number C" + station.getInput().getNumber() + ".");
    System.out.println(stationIdentifier + "output connection is set to conveyor number C" + station.getOutput().getNumber() + ".");
    System.out.println(stationIdentifier + "Workload set. Station " + station.getStationNumber() + " has a total of " + station.getRemainingWorkload() + " package groups to move.");

    // Until no package groups remain, attempt to get locks on Conveyors (note that obtaining locks automatically calls doWork())
    while (station.getRemainingWorkload() > 0) {
      getConveyors();
    }

    System.out.printf("%n* * Station %d: workload successfully completed. * *%n%n%n", station.getStationNumber());

    // Let main thread know this thread is done
    Main.allDone();
  }
}
