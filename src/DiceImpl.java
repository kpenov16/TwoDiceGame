public class DiceImpl implements Roller {
    @Override
    public int roll() {
        return (int)(Math.random()*6) + 1;
    }

    @Override
    public boolean hasSameDices() {
        return false;
    }
}
