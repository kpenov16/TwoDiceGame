import java.util.ArrayList;

public class GameContext {
    public static Player getPlayer() {
        DiceImpl dice1 = new DiceImpl();
        DiceImpl dice2 = new DiceImpl();
        ArrayList<Roller> dices = new ArrayList<>();
        dices.add(dice1);
        dices.add(dice2);

        HandImpl hand = new HandImpl();
        hand.setDices(dices);
        PlayerImpl p = new PlayerImpl();
        p.setHand(hand);

        return p;
    }
}
