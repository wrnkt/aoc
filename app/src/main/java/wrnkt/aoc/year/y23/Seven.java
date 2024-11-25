package wrnkt.aoc.year.y23;

import java.io.BufferedReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import wrnkt.aoc.util.Day;
import wrnkt.aoc.util.Formatter;

public class Seven extends Day {

    public void solution(BufferedReader reader) {
        print(String.format("FULL_HOUSE ranks THREE_OF_A_KIND? %b", (Hand.Type.FULL_HOUSE.typeRanksAbove(Hand.Type.THREE_OF_A_KIND))));
        var hand = new Hand("32T3K 765");
        hand.type();
        print(hand.toString());
    }
}

class Hand {
    public List<Character> cards;

    public int bid = 0;

    public Hand(String data) {
        var parts = data.split("\\s+");

        this.cards = parseCards(parts[0]);
        this.bid = parseBid(parts[1]);
    }

    public Type type() {
        Map<Character, Long> freqMap = getCardFreqMap();
        Stream<Long> counts = freqMap.values().stream();
        if (counts.anyMatch(count -> count == 5))
            return Type.FIVE_OF_A_KIND;
        if (counts.anyMatch(count -> count == 4))
            return Type.FOUR_OF_A_KIND;
        if (counts.filter(count -> count == 3 || count == 2).count() == 2)
            return Type.FULL_HOUSE;
        if (
            counts.anyMatch(count -> count == 3) &&
            counts.filter(count -> count == 1).count() == 2
        )
            return Type.THREE_OF_A_KIND;
        if (counts.filter(count -> count == 1).count() == 5)
            return Type.HIGH_CARD;
        if (counts.filter(count -> count == 2 || count == 1).count() == 4)
            return Type.ONE_PAIR;
        if (counts.filter(count -> count == 2 || count == 1).count() == 3)
            return Type.TWO_PAIR;
        return null;
    }

    private Map<Character, Long> getCardFreqMap() {
        return cards.stream()
                    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

    }

    private String formatFreqMapForDisplay(Map<Character, Long> map) {
        var sb = new StringBuilder();
        for (var entry : map.entrySet()) {
            sb.append(String.format("card[%c] -> %d", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

    private List<Character> parseCards(String s) {
        return s.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    private Integer parseBid(String s) {
        return Integer.parseInt(s);
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

    enum Type {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD;

        public boolean typeRanksAbove(Type other) {
            return (this.ordinal() - other.ordinal()) < 0;
        }
    }
}
