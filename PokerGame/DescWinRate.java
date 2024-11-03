package PokerGame;

import java.util.Comparator;

public class DescWinRate implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        return p2.winRate - p1.winRate;
    }
}
