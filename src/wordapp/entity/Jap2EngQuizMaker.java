package wordapp.entity;

import java.util.ArrayList;
import java.util.Random;

public class Jap2EngQuizMaker extends QuizMaker {
	private String hint;
	public Jap2EngQuizMaker(Deck deck) {
		super(deck);
	}

	public String getQuiz() {
		Card card = getCurrentQuizCard();
		String quiz = card.getMeaning();
		return quiz;
	}

	public String getNextQuiz() {
		Card card = getNextQuizCard();
		String nextQuiz = card.getMeaning();
		return nextQuiz;
	}

	public String getCorrectAnswer() {
		Card card = getCurrentQuizCard();
		String answer = card.getTitle().trim();
		return answer;
	}

	/** ヒントの構成要素を全て-に置き換えたものを作る
	 * 例)word -> ----
	 * */
	public String makeEmptyHint() {
        this.hint = "";
        String answer = this.getCorrectAnswer();
        char [] elems = answer.toCharArray();
        for(int i = 0; i < elems.length; i++) {
            switch (elems[i]) {
                case ',':
                case '.':
                case '!': this.hint += elems[i]; break;
                case ' ': this.hint += ' '; break;
                default : this.hint += '-'; break;
            }
        }
        return this.hint;
    }

	// ヒントを出す
	public String makeHint() {
		String answer = this.getCorrectAnswer();
		int n = answer.length();
		ArrayList<Integer> idxList = new ArrayList<>();	// ヒントとして既に出した文字の添え字のリスト

		for(int i = 0; i < n; i++) {
			char c = hint.charAt(i);
			if(c != '-') {
				idxList.add(i);
			}
		}
		if(idxList.size() == n) {
			return answer;
		}

		// ヒントを出す添え字を決める
		// 既にヒントとして出した添え字と重複しないようにする
		int idx;
		Random rand = new Random();
		do {
			idx = rand.nextInt(n);
		} while (idxList.contains(idx));

		char[] chars = hint.toCharArray();
		chars[idx] = answer.charAt(idx);

		this.hint = new String(chars);

		return this.hint;
	}
}
