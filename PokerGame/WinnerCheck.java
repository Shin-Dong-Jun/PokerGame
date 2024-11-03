package PokerGame;

import java.util.*;

import static java.lang.Integer.compare;

public class WinnerCheck {
    Player winner;

    Player checkWinner(Player[] players) { // 위너를 체크하는 메서드
        winner = players[0]; // 위너를 일단 첫번째 플레이어로 초기화
        List<Player> tiePlayers = new ArrayList<Player>(); //플레이어 타입만 리스트에 들어올 수 있는 참조변수 tiePlayers 생성
        tiePlayers.add(winner); // 여기에 플레이어 0을 일단 집어넣는다.

        for (int i = 1; i < players.length; i++) {
            if (players[i].score > winner.score) { // 플레이어 1과 플레이어0(기존에 리스트에 초기화했던)의 점수를 먼저 비교
                winner = players[i]; // 기존에 있던 플레이어보다 점수 높으면 그 플레이어가 위너에 저장
                tiePlayers.clear(); // 기존에 있던 제일 점수가 높은 플레이어는 초기화 하고
                tiePlayers.add(winner); // 거기에 새로운 플레이어를 추가 시킴
            } else if (players[i].score == winner.score) { // 동점자 발생시
                tiePlayers.add(players[i]); // tiePlayers에 추가로 저장.
            }
        }

        if (tiePlayers.size() > 1) { // 동점자가 발생했을 시
            System.out.println("동점자가 발생했습니다. 동점자의 카드를 비교하겠습니다.");
            winner = betterHighCard(tiePlayers); // 더 높은 카드를 비교하여 그 사람이 위너가 됨.
        }

        return winner;
    }

    Player betterHighCard(List<Player> tiePlayers) { // 동점자 발생 시 우열을 가려줄 메서드
        Player lastWinPlayer = tiePlayers.get(0); // 최종 위너를 일단 0번째로 초기화.

        for (int i = 1; i < tiePlayers.size(); i++) { // tie player list의 수만큼 반복문을 돌린다음
            Player player = tiePlayers.get(i);
            if (compareDeck(player, lastWinPlayer) > 0) { // 두 사람의 카드 덱이 더 높은 사람이 lastWinPlayer에 들어감
                lastWinPlayer = player;
            }
        }
        return lastWinPlayer;
    }

    int compareDeck(Player p1, Player p2) { // 카드 비교.

        Arrays.sort(p1.playerDeck, new Descending()); // 역순 comparator // 투페어일 때 정렬한다음에 포카드일때는 4번째까지의 합만 더하면되잖아
        Arrays.sort(p2.playerDeck, new Descending());
        System.out.println(p1.nickName + "의 카드 : " + Arrays.toString(p1.playerDeck)); // 정렬
        System.out.println(p2.nickName + "의 카드 : " + Arrays.toString(p2.playerDeck));

        int countPair1[] = new int[14];
        int countPair2[] = new int[14];
        int[] kind = new int[4];         // 카드 종류 (Spade, Heart, Diamond, Clover)

        for (Card card : p1.playerDeck) { // 플레이어1의 덱에서 무슨 숫자가 나왔는지 다시 확인
            countPair1[card.number]++;     // 숫자 카운트
            kind[card.kind - 1]++;        // 종류 카운트
        }
        for (Card card : p2.playerDeck) { // 플레이어 2의 덱에서 무슨 숫자가 나왔는지 다시 확인.
            countPair2[card.number]++;     // 숫자 카운트
            kind[card.kind - 1]++;        // 종류 카운트
        }
// 포카드 비교
        int fourCard1 = -1, fourCard2 = -1;
        for (int i = 13; i >= 1; i--) {
            if (countPair1[i] == 4) {
                fourCard1 = i;
                break;
            }
        }
        for (int i = 13; i >= 1; i--) {
            if (countPair2[i] == 4) {
                fourCard2 = i;
                break;
            }
        }
        if (fourCard1 != -1 || fourCard2 != -1) {
            return compare(fourCard1, fourCard2);
        }

        // 트리플 비교
        int threeCard1 = -1, threeCard2 = -1;
        for (int i = 13; i >= 1; i--) {
            if (countPair1[i] == 3) {
                threeCard1 = i;
                break;
            }
        }
        for (int i = 13; i >= 1; i--) {
            if (countPair2[i] == 3) {
                threeCard2 = i;
                break;
            }
        }
        if (threeCard1 != -1 || threeCard2 != -1) {
            return compare(threeCard1, threeCard2);
        }

        // 투페어 비교
        int[] twoPair1 = new int[2];
        int[] twoPair2 = new int[2];
        int pairCount1 = 0, pairCount2 = 0;

        for (int i = 13; i >= 1; i--) {
            if (countPair1[i] == 2) {
                if (pairCount1 < 2) {
                    twoPair1[pairCount1++] = i;
                }
            }
            if (countPair2[i] == 2) {
                if (pairCount2 < 2) {
                    twoPair2[pairCount2++] = i;
                }
            }
        }
        if (pairCount1 == 2 || pairCount2 == 2) {
            if (twoPair1[0] != twoPair2[0]) {
                return compare(twoPair1[0], twoPair2[0]);
            }
            if (twoPair1[1] != twoPair2[1]) {
                return compare(twoPair1[1], twoPair2[1]);
            }
        }

        // 원페어 비교
        int onePair1 = -1, onePair2 = -1;
        for (int i = 13; i >= 1; i--) {
            if (countPair1[i] == 2) {
                onePair1 = i;
                break;
            }
        }
        for (int i = 13; i >= 1; i--) {
            if (countPair2[i] == 2) {
                onePair2 = i;
                break;
            }
        }
        if (onePair1 != onePair2) {
            return compare(onePair1, onePair2);
        }

        for (int i = 0; i < p1.playerDeck.length; i++) { // 하이 카드일때와 스트레이트일 때는 결과가 정확함.
            int card1Value = (p1.playerDeck[i].number == 1) ? 14 : p1.playerDeck[i].number; // a를 1로 저장해서 변환
            int card2Value = (p2.playerDeck[i].number == 1) ? 14 : p2.playerDeck[i].number;


            if (card1Value != card2Value) {
                return card2Value - card1Value;
            }
            if (p1.playerDeck[i].kind != p2.playerDeck[i].kind) {
                return p1.playerDeck[i].kind - p2.playerDeck[i].kind;
            }

        }
        return 0;
    }


    void winMoney(Player[] player) {
        for (int i = 0; i < player.length; i++) {
            if (player[i] == winner) {
                winner.money += 100;
                winner.winRate++;
            } else
                player[i].loseRate++;
        }
    }

    Player lastWinner(Player[] player) { // 최종 승리자 체크
        Player lastWinner = player[0];
        List<Integer> gameRank = new ArrayList<Integer>(); // 게임 최종 랭크 저장 리스트

        for (int i = 0; i < player.length; i++) {
            gameRank.add(player[i].winRate);
        }
        gameRank.sort(new DescCard());

        int bestWinRate = gameRank.get(0);

        for (int i = 0; i < player.length; i++) {
            Player p = player[i];
            if (p.winRate == bestWinRate) {
                lastWinner = player[i];
            }
        }
        return lastWinner;
    }
    //우승 순서를 어떻게 저장할까.제일 높은 순서(역순)로 정렬 한다음에. 그 winRate와 같은 사람의 이름을 출력하면되잖아

    void printFinalRank(Player[] player) {
        List<Player> playerRank = new ArrayList<Player>();

        for (int i = 0; i < player.length; i++) {
            playerRank.add(player[i]);
        }

        playerRank.sort(new DescWinRate());

        int rank = 1;
        for (int i = 0; i < playerRank.size(); i++) {
            Player p = playerRank.get(i);
            System.out.println(rank + "위: " + p.nickName + " 승: " + p.winRate + " 패:" + p.loseRate);
            rank++;
        }
    }

    public String toString() {
        return "승리자는 : " + winner.nickName + "입니다." + "보유머니는 : " + winner.money + "이며, " + "승리 횟수는 : " + winner.winRate + "입니다.";
    }
}

