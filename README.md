# Assignment 2
This project was created under the direction of Dr. Mark Llewellyn for University of Central Florida course CNT-4714.

This project simulates a conveyor system which moves groups of packages through stations. Each station is assigned an input and output conveyor. The number of stations and the amount of packages each station must move is provided by `config.txt`. The application outputs log information to `stdout` describing how the stations handled the workload.

The key goal of the simulation is for the stations to operate in parallel despite the constraint that each station's input conveyor and output conveyor are shared with another station.

# Dependencies
* [Java 14](https://www.oracle.com/java/technologies/javase-downloads.html)
* [Apache Maven](https://maven.apache.org/download.cgi)

# Install and Run
## Using `git`
```
git clone https://github.com/TerranUp16/cnt-4714-assignment-2.git ;
cd cnt-4714-assignment-2 ;
mvn clean ;
mvn package ;
java -jar target/assignment-2-1.00.jar ;
```

## Download Code
1. [Download `.zip`](https://github.com/TerranUp16/cnt-4714-assignment-2/archive/master.zip)
2. Unzip `cnt-4714-assignment-2-master.zip`
3. Open a shell/terminal application (ex- PowerShell on Windows, Terminal on Mac)
4. Navigate to extracted `cnt-4714-assignment-2-master` directory/folder
5. Run-

```
mvn clean ;
mvn package ;
java -jar target/assignment-2-1.00.jar ;
```

## Troubleshooting
> `mvn` not found

Please review [Maven's installation and configuration documentation](https://maven.apache.org/install.html). In particular, make sure `mvn` is configured for `PATH` for the shell/terminal you are using.

> `JAVA_HOME` not found

Please review [Maven's installation and configuration documentation](https://maven.apache.org/install.html). In particular, make sure `JAVA_HOME` is configured for `PATH` for the shell/terminal you are using.

Please also review Oracle's documentation on this for-

* [Windows](https://docs.oracle.com/en/java/javase/14/install/installation-jdk-microsoft-windows-platforms.html#GUID-96EB3876-8C7A-4A25-9F3A-A2983FEC016A)
* [Mac](https://docs.oracle.com/en/java/javase/14/install/installation-jdk-macos.html#GUID-F9183C70-2E96-40F4-9104-F3814A5A331F)
* [Linux](https://docs.oracle.com/en/java/javase/14/install/installation-jdk-linux-platforms.html#GUID-737A84E4-2EFF-4D38-8E60-3E29D1B884B8)

# Editing Configuration
Open `config.txt` in your favorite text editor. The file uses the following format:

1. First line is an integer less than or equal to 10 which describes the total number of stations to create
2. Each line after the first line is an integer which assigns a workload to a station (which station depends on the line number as workloads are assigned in order)

## Example

```
3
2
3
4
```

This creates `3` stations, `0`, `1`, and `2`. This assigns package group workloads as follows-

| Station ID | Workload |
| --- | --- |
| `0` | 2 |
| `1` | 3 |
| `2` | 4 |
