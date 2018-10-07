public class DiceImpl implements Dice {
    @Override
    public int roll() {
        return (int)(Math.random()*6) + 1;
    }
}
