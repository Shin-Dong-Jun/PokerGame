package PokerGame;

import java.util.*;
import java.util.Scanner;

public class PokerGame {
    public static void main(String[] args) {
        int gameCnt = 0;

        WinnerCheck winCheck = new WinnerCheck(); // 승리체크하는 딜러 불러줘

        int playerNum = setPlayer(); // 플레이어 인원 결정
        Player[] player = new Player[playerNum]; // 인원 수만큼 플레이어 객체 생성.
        Set<String> sameName = new HashSet<String>(); // 중복방지

        for (int i = 0; i < playerNum; i++) {
            player[i] = new Player();
            player[i].getName(sameName);
        }
        do {
            gameCnt++;
            Dealer d = new Dealer(); // 딜러를 부르고, 딜러가 카드를 초기화 해놓음
            d.shuffle(); // 카드를 섞음

            System.out.println(gameCnt + "번째 게임 시작하겠습니다." + "카드를 배분하겠습니다.");

            for (int i = 0; i < playerNum; i++) {
                for (int j = 0; j < 5; j++) {
                    player[i].playerDeck[j] = d.giveCard(); // 카드 덱 배분하기
                }
            }
            CardCheckDealer cardCheckDealer = new CardCheckDealer(); // 카드 체크하는딜러

            for (int i = 0; i < playerNum; i++) { //
                Player p = player[i]; // 현재 플레이어 인스턴스
                System.out.println(p); // 플레이어 카드 출력
                p.jokbo = cardCheckDealer.cardCheck(p); // 카드 조합 평가 // player 인스턴스의 score에 저장하고, 그 점수를 가지고 비교
                System.out.println(p.nickName + "의 조합: " + p.jokbo); // 조합 출력
            }

            CheckScore scoreDealer = new CheckScore();

            int[] scoreArr = new int[playerNum];

            for (int i = 0; i < playerNum; i++) {
                Player p = player[i];
                p.score = scoreDealer.scoreCheck(p); // 플레이어 족보에 대한 점수 저장.
                scoreArr[i] = (p.score); // 배열에 점수 넣기
            }
            System.out.println();

            for (int i = 0; i < playerNum; i++) {
                System.out.println(player[i].nickName + "의 점수 : " + player[i].score);
            }

            System.out.println();

            winCheck.checkWinner(player); // 승리자 체크

            winCheck.winMoney(player); // 승리자 돈과 승수 +
            System.out.println(winCheck);

            System.out.println();
        } while (gameCnt != 100); // 100번 돌리게

        // 최종 승리자는 player의 winRate가 가장 높은사람
        System.out.println("최종 승리자는 " + winCheck.lastWinner(player).winRate + "번 승리한 " + winCheck.lastWinner(player).nickName + "입니다.");

        winCheck.printFinalRank(player);

    }

    static int setPlayer() {
        Scanner sc = new Scanner(System.in);
        int playNum = 0;
        boolean checkNum = false;

        while (!checkNum) {
            System.out.print("플레이어 수를 입력하시오(2~4인) : ");
            try {
                playNum = sc.nextInt();

                if (playNum >= 2 && playNum <= 4) {
                    System.out.println("플레이어 " + playNum + "명입니다. Game Start");
                    checkNum = true;
                } else {
                    System.out.println("다시 입력하세요(2~4인) : ");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력하세요.");
                sc.next();
            }
        }
        return playNum;
    }
}
