import java.util.ArrayList;

public class HandImpl implements Roller {
    private ArrayList<Roller> dices;
    @Override
    public int roll() {
        int hand = 0;
        for(Roller d : dices)
            hand += d.roll();
        return hand;
    }

    protected void setDices(ArrayList<Roller> dices){
        this.dices = dices;
    }
}
