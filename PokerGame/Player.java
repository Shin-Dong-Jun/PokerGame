package PokerGame;

import java.util.Scanner;
import java.util.*;

public class Player {
    String nickName; // 고유한 닉네임
    int money = 10000; // 플레이어 시드머니
    int score = 0; // 플레이어의 게임 점수
    int winRate = 0; // 플레이어가 게임을 이긴 횟수
    int loseRate = 0;
    String jokbo = ""; // 플레이어의 족보(카드조합)
    Card[] playerDeck = new Card[5]; // 플레이어의 5장의 카드

    Scanner sc = new Scanner(System.in);


    Player() {
    }

    ;

    void getName(Set<String> sameName) { // 닉네임을 입력받는 메서드
        while (true) {
            System.out.print("닉네임을 입력하시오 (20글자 이내): ");
            nickName = sc.nextLine();

            if (nickName.length() > 20) {
                System.out.println("지정된 길이를 초과했습니다. 다시 입력하세요.");
                continue;
            }
            if (sameName.contains(nickName)) {
                System.out.println("이미 등록된 닉네임입니다. 다시 입력하세요.");
            } else {
                sameName.add(nickName);
                break;
            }
        }
    }

    public String toString() {
        return nickName + "의 카드 : " + "(" + playerDeck[0] + "), (" + playerDeck[1] + "), (" + playerDeck[2] + "), (" + playerDeck[3] + "), (" + playerDeck[4] + ")";
    }

}
