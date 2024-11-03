### 1: 클래스 다이어그램
![PokerGame.png](images%2FPokerGame.png)

[클래스 설명]
1. Card : 카드의 정보를 담고 있는 클래스입니다.
2. Dealer : 카드를 초기화하고 셔플, 배분해주는 딜러입니다.
3. Player : 플레이어의 정보를 담고있는 클래스입니다
             getName : 이름을 입력받는 메서드
4. CardCheckDeaelr : 배분받은 카드의 조합을 판단해주는 딜러입니다.
5. CheckScore : 판단된 카드의 조합을 바탕으로 족보를 보고 점수로 환산해주는 클래스입니다.
6. WinnerCheck : 승리자를 판단해주는 기능입니다. 
                checkWinner : 위너를 체크하는 메서드
                betterHighCard : 동점자 발생시 우위를 가려주는 메서드
                compareDeck : 우위를 가리기 위해 두 플레이어의 덱을 비교하는 메서드
                lastWinner : 최종 승리자를체크하는 메서드