import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Coord {
  public int x;
  public int y;
}

class Board {
  public int id = 0;
  public ArrayList<List<Integer>> grid = new ArrayList<>();
  public long[] marked = new long[5];

  public boolean markNumber(int num) {
    for (int y = 0; y < grid.size(); ++y) {
      for (int x = 0; x < grid.get(0).size(); ++x) {
        if (num == grid.get(y).get(x)) {
          marked[y] |= (1 << x);
          return true;
        }
      }
    }
    return false;
  }

  public void printMarked() {
    StringBuilder sb = new StringBuilder();
    long markedRow = 0L;
    for (int y = 0; y < marked.length; ++y) {
      markedRow = marked[y];
      for (int x = 0; x < 5; ++x) {
        String iden = ((markedRow & (1L << x)) != 0) ? "x" : grid.get(y).get(x).toString();
        // sb.append(iden + " ");
        sb.append(String.format("%2s ", iden));
      }
      sb.append("\n");
    }
    System.out.println(sb.toString());
  }

  public static void printBoardInfo(Board b, Integer winningNum) {
    StringBuilder sb = new StringBuilder();
    int sum = 0;
    for (int y = 0; y < b.grid.size(); ++y) {
      for (int x = 0; x < b.grid.get(0).size(); ++x) {
        if (!((b.marked[y] & (1L << x)) != 0)) {
          sum += b.grid.get(y).get(x);
        }
      }
    }
    sb.append(String.format("Board ID: %d\n", b.id));
    System.out.println(b);
    b.printMarked();
    sb.append(String.format("Sum of the unmarked numbers: %d\n", sum));
    sb.append(String.format("%d x %d = %d\n", sum, winningNum, (sum * winningNum)));
    System.out.println(sb.toString());
  }

  public boolean checkWinAndOperate(int num, BiConsumer<Board, Integer> f) {
    if (checkWin()) {
      f.accept(this, num);
      return true;
    }
    return false;
  }

  public boolean checkWin() {
    final long allSet = 0b11111L;
    long vCheck = 0b11111L;
    for (long markedRow : marked) {
      if (markedRow == allSet)
        return true;
      vCheck &= markedRow;
    }
    if (vCheck == allSet)
      return true;
    return false;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Board ID: %d\n\n", id));
    sb.append(
        grid.stream()
            .map((line) -> {
              return line.stream().collect(
                  StringBuilder::new,
                  (x, y) -> x.append(String.format("%2d ", y)),
                  (a, b) -> a.append(b));
            }).collect(
                StringBuilder::new,
                (x, y) -> x.append(String.format("%s\n", y)),
                (a, b) -> a.append(b))
            .toString());
    return sb.toString();
  }
}

class Game {
  public ArrayList<Board> boards = new ArrayList<>();
  public int[] moves;

  public void playGame() {
    ArrayList<Board> currentWinningBoards = new ArrayList<>();
    for (int move : moves) {
      currentWinningBoards = playTurn(move);
      if (currentWinningBoards.size() > 0) {
        StringBuilder sb = new StringBuilder();
        sb.append("There has been a winner!\n");
        sb.append("Winning Board IDs:\n");
        currentWinningBoards.forEach((board) -> sb.append(String.format("%d ", board.id)));
        break;
      }
    }
  }

  public ArrayList<Board> playTurn(int n) {
    ArrayList<Board> winningBoards = new ArrayList<>();
    for (Board b : boards) {
      if (playTurn(b, n) && b.checkWinAndOperate(n, Board::printBoardInfo)) {
        winningBoards.add(b);
      }
    }
    return winningBoards;
  }

  public boolean playTurn(Board b, int n) {
    return b.markNumber(n);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Moves:\n");
    Arrays.stream(moves, 0, (moves.length - 1)).forEachOrdered(move -> sb.append(String.format("%d, ", move)));
    sb.append(String.format("%d", moves[moves.length - 1]));
    sb.append("\nBoards:\n");
    for (Board board : boards) {
      sb.append(board);
      sb.append("\n");
    }
    return sb.toString();
  }
}

public class DayFour {

  public static ArrayList<String> parseFile(String fName) {
    ArrayList<String> lines = new ArrayList<>();
    Path p = Paths.get(fName);

    try {
      BufferedReader r = Files.newBufferedReader(p, StandardCharsets.UTF_8);
      String line;
      while ((line = r.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      System.err.println(String.format("Could not read file %s", fName));
      System.err.println(e.getMessage());
    }
    return lines;
  }

  public static Game parseGame(ArrayList<String> lines) {
    Game g = new Game();

    int boardId = 0;
    int i = 0;
    while (i < lines.size()) {
      if (i == 0) {
        g.moves = Stream.of(lines.get(i).split(",")).mapToInt(s -> Integer.parseInt(s)).toArray();
      }
      if (lines.get(i).isEmpty()) {
        Board b = new Board();
        String line = null;
        for (int j = i + 1; j < (i + 6); ++j) {
          line = lines.get(j).strip();
          b.grid.add(
              Stream.of(line.split("\\s+"))
                  .map((s) -> Integer.parseInt(s)).toList());
        }
        b.setId(boardId++);
        g.boards.add(b);
      }
      ++i;
    }
    return g;
  }

  public static void partOne() {
    ArrayList<String> lines = parseFile("../input/four.txt");
    Game game = parseGame(lines);
    System.out.println(game);
    game.playGame();
  }

  public static void main(String... args) {
    partOne();
  }
}
