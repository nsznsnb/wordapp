package wordapp.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Choice4QuizMaker extends QuizMaker {
	public Choice4QuizMaker(Deck deck) {
		super(deck);
	}

	public String getQuiz() {
		Card card = getCurrentQuizCard();
		String quiz = card.getTitle();
		return quiz;
	}

	public String getNextQuiz() {
		Card card = getNextQuizCard();
		String nextQuiz = card.getTitle();
		return nextQuiz;
	}

	public String getCorrectAnswer() {
		Card card = getCurrentQuizCard();
		String answer = card.getMeaning();
		return answer;
	}
	/** 4択問題用の選択肢を得る */
    public String[] get4Choices() {
        Random rand = new Random();
        // 問題数が４以下の場合には以下の候補から選択肢を追加する
        String[] str = {"最もありそうな","扇動者", "困難な時期", "医療従事者", "多くの、多方面にわたる",
        		"アシカ、アザラシ", "特定の", "正当性", "～という価値で", "～を緩める", "飛翔する、空高く舞い上がる",
        		"巨大な、大した", "共同声明", "～を選び出す", "霊長類", "壊滅的な", "月着陸船", "~の中心にある",
        		"軍関係者", "標本", "毒性の", "盟友、見方", "～を引き起こす、誘発する", "～に制限を加える"};

        ArrayList<String> choices = new ArrayList<>(); // ４つの選択肢
        String answer = getCorrectAnswer();
        Deck deck = this.getDeck();

        int n = deck.size();
        if(n <= 4) {
        	for(int i = 0; i < n; i++)
        		choices.add(deck.get(i).getMeaning());
        	for(int i = n; i < 4; i++) {
        		int rn = rand.nextInt(str.length);
        		choices.add(str[rn]);
        	}
        } else {
        	// あらかじめ正解を選択肢に含めて、必ず正解が選べるようにする
        	choices.add(answer);
            for (int i = 1; i < 4; i++) {
                int rn;
                do {
                    rn = rand.nextInt(n);
                } while(choices.contains(deck.get(rn).getMeaning()));
                choices.add(deck.get(rn).getMeaning());
            }
        }

        Collections.shuffle(choices);
        return choices.toArray(new String[4]);

    }



}