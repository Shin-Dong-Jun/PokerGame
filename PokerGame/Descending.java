package PokerGame;
import java.util.Comparator;
public class Descending implements Comparator<Card>{

    public int compare(Card c1, Card c2){ // 카드비교 Comparator
        if(c1.number != c2.number){
            return c2.number - c1.number;
        }
        return c2.kind - c1.kind;
    }

}
