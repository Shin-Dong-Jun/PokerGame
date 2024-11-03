package PokerGame;

public class CardCheckDealer {

    public String cardCheck(Player p) {
        int[] countPair = new int[14];  // 카드 번호 1~13 (A는 1로 처리)
        int[] kind = new int[4];         // 카드 종류 (Spade, Heart, Diamond, Clover)
        boolean flush = false;            // 플러시 확인
        boolean straight = false;         // 스트레이트 확인
        boolean royal = false;              // 스트레이트 플러시 + 로얄 확인

        // 카드 분류: 숫자와 종류 카운트.
        for (Card card : p.playerDeck) {
            countPair[card.number]++;     // 숫자 카운트
            kind[card.kind - 1]++;        // 종류 카운트
        }

        // 플러시 판단
        for (int i = 0; i < kind.length; i++) { // 같은 종류의 카드가 5장 있을 경우
            if (kind[i] == 5) {
                flush = true;
                break;
            }
        }

        // 스트레이트 판단
        for (int i = 1; i <= 9; i++) {
            if (countPair[i] > 0 && countPair[i + 1] > 0 && countPair[i + 2] > 0 &&
                    countPair[i + 3] > 0 && countPair[i + 4] > 0) {
                straight = true;            // 연속된 숫자가 5장 있을 경우
                break;
            }
        }
        // JQK
        if (countPair[1] > 0 && countPair[10] > 0 && countPair[11] > 0 && countPair[12] > 0 && countPair[13] > 0 && flush) {
            royal = true;
        }

        // 조합 판단
        if (royal) return "로얄 스트레이트 플러시";
        if (flush && straight) return "스트레이트 플러시";
        if (isFourCard(countPair)) return "포카드";
        if (isFullHouse(countPair)) return "풀 하우스";
        if (flush) return "플러시";
        if (straight) return "스트레이트";
        if (isThreeCard(countPair)) return "트리플";
        if (isTwoPair(countPair)) return "투 페어";
        if (isOnePair(countPair)) return "원 페어";


        return "하이 카드";  // 기본 조합
    }

    private boolean isFourCard(int[] count) { // 포카드 체크
        for (int c : count) {
            if (c == 4) return true;
        }
        return false;
    }

    private boolean isFullHouse(int[] count) { // 풀하우스 체크
        boolean hasThree = false;
        boolean hasPair = false;
        for (int c : count) {
            if (c == 3) hasThree = true;
            if (c == 2) hasPair = true;
        }
        return hasThree && hasPair;
    }

    private boolean isThreeCard(int[] count) { // 트리플 체크
        for (int c : count) {
            if (c == 3) return true;
        }
        return false;
    }

    private boolean isTwoPair(int[] count) { // 투페어 체크
        int pairCount = 0;
        for (int c : count) {
            if (c == 2) pairCount++;
        }
        return pairCount == 2;
    }

    private boolean isOnePair(int[] count) { // 원페어 체크
        for (int c : count) {
            if (c == 2) return true;
        }
        return false;
    }

}

//
int compareDeck(Card[] deck1, Card[] deck2) {
    Arrays.sort(deck1, new Descending()); // 내림차순 정렬
    Arrays.sort(deck2, new Descending());

    // 핸드 타입 판별
    String hand1 = cardCheckDealer.cardCheck(deck1);
    String hand2 = cardCheckDealer.cardCheck(deck2);

    // 포카드 판별 및 비교
    if (hand1.equals("포카드") && hand2.equals("포카드")) {
        int deck1FourOfAKindSum = getFourOfAKindSum(deck1);
        int deck2FourOfAKindSum = getFourOfAKindSum(deck2);

        if (deck1FourOfAKindSum != deck2FourOfAKindSum) {
            return deck2FourOfAKindSum - deck1FourOfAKindSum; // 합이 큰 사람이 우승
        }
    }

    // 기본 카드 비교 (하이카드, 스트레이트 등)
    for (int i = 0; i < deck1.length; i++) {
        int card1Value = (deck1[i].number == 1) ? 14 : deck1[i].number; // Ace를 14로 처리
        int card2Value = (deck2[i].number == 1) ? 14 : deck2[i].number;

        if (card1Value != card2Value) {
            return card2Value - card1Value;
        }

        // 같은 값이면 무늬 비교
        if (deck1[i].kind != deck2[i].kind) {
            return deck1[i].kind - deck2[i].kind;
        }
    }

    return 0; // 완전히 동일한 경우
}

int getFourOfAKindSum(Card[] deck) {
    // 첫 네 장의 합 계산
    return deck[0].number + deck[1].number + deck[2].number + deck[3].number;
}

