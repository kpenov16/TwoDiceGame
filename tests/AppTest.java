import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    Player player;

    @BeforeEach
    void setUp() {
        player = GameContext.getPlayer();
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
    void givenPlayerRolls2times12BetweenOtherHands_returnPlayerWinsTheGame(){
        setPlayerNextHand(4);
        setPlayerNextHand(12);
        setPlayerNextHand(2);
        setPlayerNextHand(12);
        assertTrue(player.isWinner());
    }

    @Test
    void givenPlayerRolls2times12_returnPlayerWinsTheGame(){
        setPlayerNextHand(12);
        setPlayerNextHand(12);
        assertTrue(player.isWinner());
    }

    @Test
    void givenPlayerRollsEvenHand_returnPlayerGetsExtraHand(){
        for(int i=2; i<=12;i+=2){
            setPlayerNextHand(i);
            assertTrue(player.hasExtraHand());
        }
    }

    private void setPlayerNextHand(int rollThisNext) {
        ((PlayerImpl)player).setHand(new Roller() {
            @Override
            public int roll() {
                return rollThisNext;
            }
        });
        assertEquals(rollThisNext, player.roll());
    }

    @Test
    void givenPlayerRollsHand2Points_returnScoreIsZero(){
        setPlayerNextHand(2);
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
        setPlayerNextHand(10);
        setPlayerNextHand(10);
        setPlayerNextHand(10);
        setPlayerNextHand(10);
        setPlayerNextHand(4);
        assertTrue( player.getScore() >= 40);
        assertTrue( player.isWinner() );
    }

    @Test
    void givenPlayerRolls40PointsOrMoreFollowedByHand2_returnPlayerIsNotWinnerYet(){
        setPlayerNextHand(10);
        setPlayerNextHand(10);
        setPlayerNextHand(10);
        setPlayerNextHand(10);
        setPlayerNextHand(2);
        assertTrue( player.getScore() == 0);
        assertFalse( player.isWinner() );
    }

    @Test
    void givenPlayerRolls40PointsOrMore_returnPlayerIsNotWinnerYet(){
        setPlayerNextHand(10);
        setPlayerNextHand(10);
        setPlayerNextHand(10);
        setPlayerNextHand(10);
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