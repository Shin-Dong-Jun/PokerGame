package PokerGame;

import java.util.*;

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
            if (compareDeck(player.playerDeck, lastWinPlayer.playerDeck) > 0) { // 두 사람의 카드 덱이 더 높은 사람이 lastWinPlayer에 들어감
                lastWinPlayer = player;
            }
        }
        return lastWinPlayer;
    }

    int compareDeck(Card[] deck1, Card[] deck2) { // 카드 비교.

        Arrays.sort(deck1, new Descending()); // 역순 comparator // 투페어일 때 정렬한다음에 포카드일때는 4번째까지의 합만 더하면되잖아
        Arrays.sort(deck2, new Descending());
        System.out.println(Arrays.toString(deck1));
        System.out.println(Arrays.toString(deck2));


        for (int i = 0; i < deck1.length; i++) { // 하이 카드일때와 스트레이트일 때는 결과가 정확함.
            int card1Value = (deck1[i].number == 1) ? 14 : deck1[i].number; // a를 1로 저장해서 변환
            int card2Value = (deck2[i].number == 1) ? 14 : deck2[i].number;


            if (card1Value != card2Value) {
                return card2Value - card1Value;
            }
            if (deck1[i].kind != deck2[i].kind) {
                return deck1[i].kind - deck2[i].kind;
            }

        }
        return 0;
    }
    /*
    이 메서드는 하이 카드일 때랑 스트레이트일떄는 잘 비교가 되는데 원페어, 투페어, 트리플카드, 포카드일 때는 오류가 발생할 수 있어
    왜냐하면 덱1의 카드가 1, 3, 3, 5, 5, 일때랑 3, 3, 4, 4, 5일 때는 덱 1이 이겨야 되는데 이럴경우 덱 2가 이기는 경우가 발생하기 때문이지
    예를 들면 투 페어 동점자가 나왔을 때 두 플레이어의 덱중에서 더 높은 숫자의 페어를 가지고 있는 사람이 이겨야 된단말이야.
    트리플 카드도 3, 3, 3, 4, 5 랑 4, 6, 5, 5, 5면은 5, 5, 5 인사람이 이겨야 하는거지. 포카드도 마찬가지.
     */





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

