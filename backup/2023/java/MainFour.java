import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Card {
  private Integer id;
  private ArrayList<Integer> winningNumbers = new ArrayList<>();
  private ArrayList<Integer> numbers = new ArrayList<>();

  public Card() {
  }

  public void addWinningNumber(Integer n) {
    winningNumbers.add(n);
  }

  public void addNumber(Integer n) {
    numbers.add(n);
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public Integer score() {
    var matches = 0;
    for (Integer n : numbers) {
      if (winningNumbers.indexOf(n) != -1)
        matches++;
    }
    return matches <= 1 ? matches : (int) Math.pow(2, (matches - 1));
  }
}

public class MainFour {
  public static Card parseCard(final String in) {
    Card card = new Card();

    Matcher matcher = Pattern.compile("\\d+").matcher(in);
    matcher.find();
    card.setId(Integer.valueOf(matcher.group()));

    String winningStr = in.substring((in.indexOf(":") + 1), in.indexOf("|")).strip();
    String[] winningNums = winningStr.split("\\s+");
    for (String sw : winningNums) {
      card.addWinningNumber(Integer.parseInt(sw));
    }

    String numStr = in.substring(in.indexOf("|") + 1).strip();
    String[] nums = numStr.split("\\s+");
    for (String sn : nums) {
      card.addNumber(Integer.parseInt(sn));
    }

    return card;
  }

  public static ArrayList<Card> parseFile(String pathStr) {
    Path path = Paths.get(pathStr);

    ArrayList<Card> cards = new ArrayList<>();

    try {
      BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8);

      String line;
      while ((line = br.readLine()) != null) {
        cards.add(parseCard(line));
      }
    } catch (IOException e) {
    }
    return cards;
  }

  public Integer scoreCards(ArrayList<Card> cards) {
    Integer score = 0;

    List<Card> sortedCards = cards.stream().sorted((c1, c2) -> c1.getId().compareTo(c2.getId())).toList();

    ArrayList<Card> listWithCopies = new ArrayList<>();

    for (int i = 0; i < sortedCards.size(); ++i) {
      var card = sortedCards.get(i);

      Integer copies = card.score();

      if (copies > 0) {
        for (int idx = card.getId() + 1; idx < card.getId() + copies; ++idx) {
        }
      }
    }

    return score;
  }

  public static void main(String... args) {
    ArrayList<Card> cards = parseFile("../input/4.txt");
    Integer score = cards.stream().mapToInt((c) -> c.score()).sum();
    System.out.println(score);
  }
}
