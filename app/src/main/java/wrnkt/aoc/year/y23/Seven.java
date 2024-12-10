package wrnkt.aoc.year.y23;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import aoc.framework.Day;
import aoc.framework.util.Formatter;

public class Seven extends Day {

    public void solution(BufferedReader reader) {
        List<Hand> hands = parseHands(reader);
        int winnings = partOne(hands);
    }

    public int partOne(List<Hand> hands) {
        List<Hand> rankedHands = hands.stream().sorted().toList();
        var handsCount = rankedHands.size();

        print(String.format("Sorted %d hands.", handsCount));

        int winnings = 0;
        for (int rank = 1; rank <= (handsCount); rank++) {
            var hand = rankedHands.get(rank-1);
            winnings += (hand.getBid() * rank);
        }

        print(String.format("Total winnings for these hands: %d", winnings));
        return winnings;
    }

    public List<Hand> parseHands(BufferedReader reader) {
        List<Hand> hands = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                hands.add(new Hand(line));
            }
        } catch (Exception e) {
            print("Failed to parse hands.");
        }
        return hands;
    }

}

class Hand implements Comparable<Hand> {

    public final List<Character> cards;
    public int bid = 0;
    public final Type type;

    public Hand(String data) {
        var parts = data.split("\\s+");

        this.cards = parseCards(parts[0]);
        this.bid = parseBid(parts[1]);
        this.type = calculateType();
    }

    public Type getType() { return this.type; }

    public int getBid() { return this.bid; }

    /* ------------------------- */
    /* ---- Type Calcuation ---- */
    /* ------------------------- */

    public Map<Character, Long> getCardFreqMap() {
        return cards.stream()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    private Supplier<Stream<Map.Entry<Character, Long>>> mappedFreqStreamSupplier = () -> {
        return getCardFreqMap().entrySet().stream();
    };

    private Supplier<Stream<Long>> freqStreamSupplier = () -> mappedFreqStreamSupplier.get().map(e -> e.getValue());

    private Type calculateType() {
        if (freqStreamSupplier.get().anyMatch(count -> count == 5))
            return Type.FIVE_OF_A_KIND;
        if (freqStreamSupplier.get().anyMatch(count -> count == 4))
            return Type.FOUR_OF_A_KIND;
        if (
            freqStreamSupplier.get().filter(count -> count == 3 || count == 2).count() == 2
            && freqStreamSupplier.get().filter(count -> count == 1).count() == 0
        )
            return Type.FULL_HOUSE;
        if (
            freqStreamSupplier.get().anyMatch(count -> count == 3) &&
            freqStreamSupplier.get().filter(count -> count == 1).count() == 2
        )
            return Type.THREE_OF_A_KIND;
        if (freqStreamSupplier.get().filter(count -> count == 1).count() == 5)
            return Type.HIGH_CARD;
        if (freqStreamSupplier.get().filter(count -> count == 2 || count == 1).count() == 4)
            return Type.ONE_PAIR;
        if (
            freqStreamSupplier.get().filter(count -> count == 2).count() == 2
            && freqStreamSupplier.get().filter(count -> count == 1).count() == 1
        )
            return Type.TWO_PAIR;
        return null;
    }

    /* -------------------- */
    /* ---- Comparison ---- */
    /* -------------------- */

    public int compareTypes(Hand other) {
        return getType().compareTo(other.getType());
    }

    public static final List<Character> RANKED_FACE_CARDS  = List.of('A', 'K', 'Q', 'J', 'T' );

    public int compareCards(Hand other) {
        if (cards.equals(other.cards)) return 0;

        int i = 0;
        Character card;
        Character otherCard;

        while (true) {
            card = cards.get(i);
            otherCard = other.cards.get(i);

            if (card.equals(otherCard)) {
                ++i;
                continue;
            }

            if (Character.isDigit(card)) {
                if (!Character.isDigit(otherCard)) return -1;
                if ((card - '0') > (otherCard - '0')) return 1;
                return -1;
            } else {
                if (Character.isDigit(otherCard)) return 1;
                if (RANKED_FACE_CARDS.indexOf(card) - RANKED_FACE_CARDS.indexOf(otherCard) < 0 ) return 1;
                return -1;
            }

        }
    }

    @Override
    public int compareTo(Hand other) {
        var typeComparison = compareTypes(other);
        if (typeComparison == 0) {
            return compareCards(other);
        }
        return typeComparison;
    }

    /* ------------------- */
    /* ----- Parsing ----- */
    /* ------------------- */

    public List<Character> parseCards(String s) {
        return s.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    private Integer parseBid(String s) {
        return Integer.parseInt(s);
    }

    /* ------------------- */
    /* ----- Display ----- */
    /* ------------------- */

    public static String formatFreqMapForDisplay(Map<Character, Long> map) {
        var sb = new StringBuilder();
        for (var entry : map.entrySet()) {
            sb.append(String.format("card[%c] -> %d\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

    public String formatFreqMapForDisplay() {
        return Hand.formatFreqMapForDisplay(getCardFreqMap());
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("Hand[\n");
        sb.append("   cards={");
        sb.append(Formatter.commaSep(cards));
        sb.append("},\n");
        sb.append(String.format("   bid=%d\n", bid));
        sb.append("]\n");
        return sb.toString();
    }

    /* --------------------- */
    /* ----- Card Type ----- */
    /* --------------------- */

    enum Type {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND;
    }

}
