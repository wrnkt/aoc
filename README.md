# Advent Of Code

## How To Use

To run Java solutions after writing a solution class and specifying it in
`application.properties`, run `./gradlew :java:run`

### Configuration

Configure the package scanned for solutions in `application.properties`. Set the
runner to run days 1, 2, and 3 in 2023 with `runner.year.2023=1,2,3` in
`application.properties`. Add as many years as you would like.

### Example

To start solving a new day, follow these steps. These steps assume the base
configuration in `application.properties`.

1. Put the input file in `./app/src/main/resources/<year>/<day>.txt`. For
   example, the input file for Day One in 2023 would be
   `./app/src/main/resources/2023/1.txt`.

2. Create a new class extending `Day` in the year's package. Note that the
   year's package name convention is "y" followed by the last two digits of the
   year. The file should be the spelled out name of the day. (Ex:
   `./app/src/main/java/wrnkt/aoc/year/y23/One.java`)

3. Override the `public void solution(BufferedReader reader)` function with the
   solution for the day. The `BufferedReader` provides access to the input file
   added in step 2. Utilize the `print(String s)` function for output. Output
   will go to either CONSOLE, LOGGER, OR FILE depending on settings.

   ```java
   public class Eight extends Day {
       @Override
       public void solution(BufferedReader reader) {
           // solution here...
       }
   }
   ```
