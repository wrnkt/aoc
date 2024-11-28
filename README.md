# Advent Of Code

## How To Use

To start solving a new day, follow these steps.

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
