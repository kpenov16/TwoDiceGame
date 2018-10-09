import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    Player player;
    private HandImpl hand;

    @BeforeEach
    void setUp() {
        player = GameContext.getPlayer();
        hand = new HandImpl();
    }

    @AfterEach
    void tearDown() {
    }

/*
* Det skal være et spil mellem 2 personer.
* Spillet skal gå ud på at man slår med et raflebæger med to terninger og ser resultatet med det samme.
* Summen af terningernes værdier lægges til ens point.
* Vinderen er den, der opnår 40 point.
* Hvis der er ressourcer til det, er der følgende ekstraopgaver:
* - Spilleren mister alle sine point hvis spilleren slår to 1'ere.
* - Spilleren får en ekstra tur hvis spilleren slår to ens.
* - Spilleren kan vinde spillet ved at slå to 6'ere, hvis spilleren også i forrige kast slog to 6'ere uanset om det er på ekstrakast eller i forrige tur.
* - Spilleren skal slå to ens for at vinde spillet, efter at man har opnået 40 point.
* */

    @Test
    void givenHandRollsEvenWithDifferentDices_returnHandHasNotTheSameDices(){
        hand = setupNextHand(1, 3);
        assertEquals( 4, hand.roll());
        assertFalse(hand.hasSameDices());
    }

    @Test
    void givenHandRollsEvenWithSameDices_returnHandHasTheSameDices(){
        hand = setupNextHand(2, 2);
        assertTrue( hand.roll() % 2 == 0);
        assertTrue(hand.hasSameDices());
    }

    private HandImpl setupNextHand(int dice1Rolled, int dice2Rolled) {
        ArrayList<Roller> dices = new ArrayList<>();
        Roller dice1 = new Roller() {
            @Override
            public int roll() {
                return dice1Rolled;
            }

            //this is confusing and has to be refactored and removed
            @Override
            public boolean hasSameDices() {
                return false;
            }
        };
        Roller dice2 = new Roller() {
            @Override
            public int roll() {
                return dice2Rolled;
            }

            //this is confusing and has to be refactored and removed
            @Override
            public boolean hasSameDices() {
                return false;
            }
        };
        dices.add(dice1);
        dices.add(dice2);
        HandImpl hand = new HandImpl();
        hand.setDices(dices);
        return hand;
    }


    @Test
    void givenPlayerRolls2times12ByDiceBetweenOtherHands_returnPlayerWinsTheGame(){
        setPlayerNextHand(2, 2);
        setPlayerNextHand(6, 6);
        setPlayerNextHand(1, 1);
        setPlayerNextHand(6, 6);
        assertTrue(player.isWinner());
    }

    @Test
    void givenPlayerRolls2times12BetweenOtherHands_returnPlayerWinsTheGame(){
        setPlayerNextHand(4, true);
        setPlayerNextHand(12, true);
        setPlayerNextHand(2, true);
        setPlayerNextHand(12, true);
        assertTrue(player.isWinner());
    }

    @Test
    void givenPlayerRolls2times12_returnPlayerWinsTheGame(){
        setPlayerNextHand(12, true);
        setPlayerNextHand(12, true);
        assertTrue(player.isWinner());
    }

    @Test
    void givenPlayerRollsEvenHand_returnPlayerGetsExtraHand(){
        for(int i=2; i<=12;i+=2){
            setPlayerNextHand(i, true);
            assertTrue(player.hasExtraHand());
        }
    }

    private Player setPlayerNextHand(int dice1Rolled, int dice2Rolled) {
        Roller hand = setupNextHand(dice1Rolled, dice2Rolled);
        ((PlayerImpl)player).setHand(hand);
        assertEquals(dice1Rolled+dice2Rolled, player.roll());
        return player;
    }

    private void setPlayerNextHand(int rollThisNext, boolean sameDice) {
        ((PlayerImpl)player).setHand(new Roller() {
            @Override
            public int roll() {
                return rollThisNext;
            }
            @Override
            public boolean hasSameDices(){
                return sameDice;
            }
        });
        assertEquals(rollThisNext, player.roll());
    }

    @Test
    void givenPlayerRollsHand2Points_returnScoreIsZero(){
        setPlayerNextHand(2, true);
        assertTrue( player.getScore() == 0);
        assertFalse( player.isWinner() );
    }

    @Test
    void givenPlayerRolls_returnNumberFrom2To12(){
        int roll = player.roll();
        assertTrue(roll >=1 && roll <=12 );
    }

    @Test
    void givenPlayerRolls40PointsOrMoreFollowedByEqualHand_returnPlayerIsWinner(){
        setPlayerNextHand(10, true);
        setPlayerNextHand(10, true);
        setPlayerNextHand(10, true);
        setPlayerNextHand(10, true);
        setPlayerNextHand(4, true);
        assertTrue( player.getScore() >= 40);
        assertTrue( player.isWinner() );
    }

    @Test
    void givenPlayerRolls40PointsOrMoreFollowedByHand2_returnPlayerIsNotWinnerYet(){
        setPlayerNextHand(10, true);
        setPlayerNextHand(10, true);
        setPlayerNextHand(10, true);
        setPlayerNextHand(10, true);
        setPlayerNextHand(2, true);
        assertTrue( player.getScore() == 0);
        assertFalse( player.isWinner() );
    }

    @Test
    void givenPlayerRolls40PointsOrMore_returnPlayerIsNotWinnerYet(){
        setPlayerNextHand(10, true);
        setPlayerNextHand(10, true);
        setPlayerNextHand(10, true);
        setPlayerNextHand(10, true);
        assertTrue( player.getScore() >= 40);
        assertFalse( player.isWinner() );
    }

    @Test
    void givenPlayerRollsLessThan40points_returnNotWinner(){

        player.roll();
        assertNotNull(player);

        assertFalse(player.isWinner());
    }
}