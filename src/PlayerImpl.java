public class PlayerImpl implements Player {
    private Roller hand = null;
    private int currentScore = 0;
    private int currentRoll = 0;
    private int timesRolled12 = 0;
    private int lastScore = 0;

    @Override
    public int roll() {
        lastScore = currentScore;
        currentRoll = hand.roll();
        if(currentRoll == 12 && hand.hasSameDices())
            timesRolled12++;
        currentScore = (currentRoll == 2) ? 0 : (currentScore +=currentRoll);
        return currentRoll;
    }

    @Override
    public boolean hasSameDices() {
        return false;
    }

    @Override
    public boolean isWinner() {
        return  (rolledOneEqualHandAfter40() || rolled2HandsOf12()) ? true : false;
    }

    private boolean rolledOneEqualHandAfter40() {
        return  (lastScore >= 40 && currentScore >= 40 && (currentRoll % 2 == 0) && hand.hasSameDices());
    }

    private boolean rolled2HandsOf12() {
        return (timesRolled12 == 2);
    }

    @Override
    public int getScore() {
        return currentScore;
    }

    @Override
    public boolean hasExtraHand() {
        return (currentRoll % 2 == 0 && hand.hasSameDices()) ? true : false;
    }

    protected void setHand(Roller hand) {
        this.hand = hand;
    }

}
