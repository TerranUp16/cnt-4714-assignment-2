/*
  Name:Robert Schwyzer
  Course:CNT 4714 Fall 2020
  Assignment title: Project 2 – Multi-threaded programming in Java
  Date: Sunday October 4, 2020

  Class: Factory
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Holds a set of Stations and Conveyors, using a "config.txt" file as input for the number of Stations and the amount of package groups each Station must move
 */
public class Factory {
  // Filepath to the configuration file to load
  String inputFilepath = "config.txt";

  // Array of Conveyor objects
  Conveyor[] conveyors;

  // Array of Station objects
  Station[] stations;

  // Total number of stations in the Factory
  int numberOfStations;

  // Initialize a new Factory
  public Factory() {
    // Set to 0 until configuration file is read
    numberOfStations = 0;
  }

  /**
   * @return  filepath of the input file
   */
  public String getFilepath() {
    return inputFilepath;
  }

  /**
   * @return  array of Conveyors in this Factory
   */
  public Conveyor[] getConveyors() {
    return conveyors;
  }

  /**
   * @return  array of Stations in this Factory
   */
  public Station[] getStations() {
    return stations;
  }

  /**
   * @return  number of stations in this Factory
   */
  public int getStationCount() {
    return numberOfStations;
  }

  /**
   * Debug method which pretty prints (to stdout) the current configuration of Stations in this Factory.
   * 
   * Intended to be used after readInput to validate Factory correctly interpreted contents of input file
   */
  public void dumpAll() {
    System.out.printf("%25s %25s %25s %25s%n", "Station ID", "Station Workload", "Input Conveyor ID", "Output Conveyor ID");
    
    for (Station station : stations) {
      System.out.printf("%25d %25d %25d %25d%n", station.getStationNumber(), station.getRemainingWorkload(), station.getInput().getNumber(), station.getOutput().getNumber());
    }
  }

  /**
   * Expects a file at inputFilepath which contains one integer per line
   * 
   * FIRST LINE-      Number of Stations (maximum of 10)
   * REMAINING LINES- Number of package groups for a Station to move. This goes in order, so the first of the remaining lines correlates to the first station, the next line to the second station, and so on...
   */
  void readInput() {
    // Keep track of line number
    int line;

    // Instantiate the File from the inputFilepath
    File input = new File(inputFilepath);

    // Attempt to open Scanner on the file
    try (Scanner reader = new Scanner(input)) {
      // Set first line
      line = 1;

      // Start station count at 0
      numberOfStations = 0;

      // Read next integer- technically does not require numbers to be on separate lines, but an input file which would trigger this would already be invalid
      while (reader.hasNextInt()) {
        // Read the next integer in the file
        int data = reader.nextInt();

        if (line == 1) {
          // First line contains number of stations
          numberOfStations = data;

          // Instantiate arrays
          stations = new Station[numberOfStations];
          conveyors = new Conveyor[numberOfStations];

          // Instantiate the first Conveyor
          conveyors[0] = new Conveyor(0);
        } else {
          if (line == 2) {
            // Handle first station specially - create last Conveyor and set first Station's output to last Conveyor
            conveyors[numberOfStations - 1] = new Conveyor(numberOfStations - 1);
            stations[0] = new Station(0, data, conveyors[0], conveyors[numberOfStations - 1]);
          } else {
            // All remaining stations can be handled the same - create one new Conveyor, then set the new Station to use the new Conveyor as input and a previously-created Conveyor as output
            conveyors[line - 2] = new Conveyor(line - 2);
            stations[line - 2] = new Station(line - 2, data, conveyors[line - 2], conveyors[line - 3]);
          }
        }

        // Increment line
        line++;
      }
    } catch (FileNotFoundException e) {
        System.out.println("File not found.");
        e.printStackTrace();
    }
  }
}