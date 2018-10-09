import java.util.ArrayList;

public class HandImpl implements Roller {
    private ArrayList<Roller> dices;
    private boolean dicesAreSame = true;

    @Override
    public int roll() {
        int hand = 0;
        int currentDice = dices.get(0).roll();//1
        int previousDice = -1;
        hand += currentDice;
        for(int i=1; i< dices.size(); i++){
            previousDice = currentDice;
            currentDice = dices.get(i).roll();//3
            if(currentDice != previousDice)
                dicesAreSame = false;
            hand += currentDice;
        }
        return hand;
    }

    @Override
    public boolean hasSameDices() {
        return dicesAreSame;
    }

    protected void setDices(ArrayList<Roller> dices){
        this.dices = dices;
    }
}
