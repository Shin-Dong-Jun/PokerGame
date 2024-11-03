package PokerGame;

import java.util.*;

public class CheckScore{

    HashMap score = new HashMap();

    int scoreCheck(Player p) {
        registerScore();
        for (int i = 0; i < 5; i++) {
            if (score.containsKey(p.jokbo)) { //score에 jokbo("스트레이트")와 같은 점수가 포함되어있는지 확인한다 포함되어있으면(true)
               return (int)score.get(p.jokbo); // jokbo 키 값의 value 객체를 반환. 객체를 반환하는거니까 이걸 어디에 저장해야할까? int로 저장되는줄 알았는데.
            }
        }

        return -1; // 없으면 -1반환
    }

    void registerScore () {
        score.put("로얄 스트레이트 플러시", 9);
        score.put("스트레이트 플러시", 8);
        score.put("포카드", 7);
        score.put("풀 하우스", 6);
        score.put("플러시", 5);
        score.put("스트레이트", 4);
        score.put("트리플", 3);
        score.put("투 페어", 2);
        score.put("원 페어", 1);
        score.put("하이 카드", 0);
    }
}
