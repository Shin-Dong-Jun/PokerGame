package PokerGame;

public class Dealer {
    final int CARD_NUM = 52;
    Card c[] = new Card[CARD_NUM];
    int pos = 0;

    Dealer() { // 초기 카드
        for (int i = 0; i < Card.NUM_MAX; i++) {
            for (int j = 0; j < Card.KIND_MAX; j++) {
                c[i * Card.KIND_MAX + j] = new Card(j + 1, i + 1);
            }
        }
    }

    void shuffle() { // 카드 섞기
        for (int i = 0; i < 1000; i++) { // 1000번 반복
            for (int j = 0; j < CARD_NUM; j++) { // 각 섞기마다 카드를 섞음
                int n = (int) (Math.random() * CARD_NUM);
                Card temp = c[j];
                c[j] = c[n];
                c[n] = temp;
            }
        }
    }

    Card giveCard() { // 카드 배분하기
        Card card = null;

        card = c[pos]; //카드 섞은다음 카드에 저장
        c[pos++] = null;

        return card;
    }
}

