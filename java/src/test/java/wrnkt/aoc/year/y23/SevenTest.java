package wrnkt.aoc.year.y23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SevenTest {
    public static final Logger log = LoggerFactory.getLogger(SevenTest.class);

    @Test
    void sanityTest() {
        assertTrue(1 == 1);
    }

    @Test
    void handFiveKindTest() {
        Hand h = new Hand("AAAAA 123");
        assertNotNull(h);
        log.info("{}", h.toString());

        var cardFreqMap = h.getCardFreqMap();
        assertNotNull(cardFreqMap);
        var stringifiedMap = Hand.formatFreqMapForDisplay(cardFreqMap);
        log.info("{}", stringifiedMap);

        var type = h.getType();
        assertNotNull(type);
        log.info("hand has type: {}", type);
        assertEquals(Hand.Type.FIVE_OF_A_KIND, type);

    }

    @Test
    void handFourKindTest() {
        Hand h = new Hand("AA8AA 123");
        assertNotNull(h);
        log.info("{}", h.toString());

        var cardFreqMap = h.getCardFreqMap();
        assertNotNull(cardFreqMap);
        var stringifiedMap = Hand.formatFreqMapForDisplay(cardFreqMap);
        log.info("{}", stringifiedMap);

        var type = h.getType();
        assertNotNull(type);
        log.info("hand has type: {}", type);
        assertEquals(Hand.Type.FOUR_OF_A_KIND, type);

    }

    @Test
    void handFullHouseTest() {
        Hand h = new Hand("23332 123");
        assertNotNull(h);
        log.info("{}", h.toString());

        var cardFreqMap = h.getCardFreqMap();
        assertNotNull(cardFreqMap);
        var stringifiedMap = Hand.formatFreqMapForDisplay(cardFreqMap);
        log.info("{}", stringifiedMap);

        var type = h.getType();
        assertNotNull(type);
        log.info("hand has type: {}", type);
        assertEquals(Hand.Type.FULL_HOUSE, type);

    }

    @Test
    void handThreeKindTest() {
        Hand h = new Hand("TTT98 123");
        assertNotNull(h);
        log.info("{}", h.toString());

        var cardFreqMap = h.getCardFreqMap();
        assertNotNull(cardFreqMap);
        var stringifiedMap = Hand.formatFreqMapForDisplay(cardFreqMap);
        log.info("{}", stringifiedMap);

        var type = h.getType();
        assertNotNull(type);
        log.info("hand has type: {}", type);
        assertEquals(Hand.Type.THREE_OF_A_KIND, type);

    }

    @Test
    void handTwoPairTest() {
        Hand h = new Hand("23432 123");
        assertNotNull(h);
        log.info("{}", h.toString());

        var cardFreqMap = h.getCardFreqMap();
        assertNotNull(cardFreqMap);
        var stringifiedMap = Hand.formatFreqMapForDisplay(cardFreqMap);
        log.info("{}", stringifiedMap);

        var type = h.getType();
        assertNotNull(type);
        log.info("hand has type: {}", type);
        assertEquals(Hand.Type.TWO_PAIR, type);

    }

    @Test
    void handOnePairTest() {
        Hand h = new Hand("A23A4 123");
        assertNotNull(h);
        log.info("{}", h.toString());

        var cardFreqMap = h.getCardFreqMap();
        assertNotNull(cardFreqMap);
        var stringifiedMap = Hand.formatFreqMapForDisplay(cardFreqMap);
        log.info("{}", stringifiedMap);

        var type = h.getType();
        assertNotNull(type);
        log.info("hand has type: {}", type);
        assertEquals(Hand.Type.ONE_PAIR, type);

    }

    @Test
    void handHighCardTest() {
        Hand h = new Hand("23456 123");
        assertNotNull(h);
        log.info("{}", h.toString());

        var cardFreqMap = h.getCardFreqMap();
        assertNotNull(cardFreqMap);
        var stringifiedMap = Hand.formatFreqMapForDisplay(cardFreqMap);
        log.info("{}", stringifiedMap);

        var type = h.getType();
        assertNotNull(type);
        log.info("hand has type: {}", type);
        assertEquals(Hand.Type.HIGH_CARD, type);

    }

    @Test
    void testWinningHand() {
        var handOne = new Hand("KK677 123");
        var handTwo = new Hand("KTJJT 123");

        var resultOne = handOne.compareCards(handTwo);
        log.info("handOne.compareCards(handTwo) => {}", resultOne);
        assertTrue(handOne.compareCards(handTwo) > 0);

        var resultTwo = handTwo.compareCards(handOne);
        log.info("handTwo.isStrongerByCardThan(handOne) => {}", resultTwo);
    }

    @Test
    void testTypeRankings() {
        assertTrue(
            Hand.Type.FIVE_OF_A_KIND.compareTo(
                Hand.Type.FOUR_OF_A_KIND)
            > 0
        );
    }
    
}
