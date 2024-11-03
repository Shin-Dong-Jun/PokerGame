package PokerGame;

public class Card {
    static final int KIND_MAX = 4;
    static final int NUM_MAX = 13;

    int kind;
    int number;

    Card(){
        this(1, 1);
    }

    Card(int kind, int number){
        this.kind = kind;
        this.number = number;
    }

    public String toString() {
        String kind="";
        String number="";

        switch(this.kind) {
            case 4 :
                kind = "♤";
                break;
            case 3 :
                kind = "◇";
                break;
            case 2 :
                kind = "♡";
                break;
            case 1 :
                kind = "♧";
                break;
            default :
        }

        switch(this.number) {
            case 13 :
                number = "K";
                break;
            case 12 :
                number = "Q";
                break;
            case 11 :
                number = "J";
                break;
            case 1 :
                number = "A";
                break;
            default :
                number = this.number + "";
        }
        return kind+ ", " + number;
    }



}
