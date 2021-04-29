package wordapp.entity;

public abstract class QuizMaker {
    private Deck deck; // 出題する単語カードのデッキ
    private Deck inCorrectDeck; // クイズで間違えたカードを集めたデッキ
    private Card currentQuizCard; // 現在出題中の単語カード
    private int quizNo; // 問題番号
    private int quizNum; // 問題数
    private int point; // 正解した点数
    public QuizMaker(Deck deck) {
        this.deck = deck;
        this.deck.shuffle();
        this.inCorrectDeck = new Deck();
        this.currentQuizCard = deck.drawAndSetToBottom();
        this.quizNo = 1;
        this.quizNum = this.deck.size();
        this.point = 0;
    }

    public Deck getDeck() {
    	return this.deck;
    }

    public int getQuizNo() {
    	return this.quizNo;
    }

    public int getQuizNum() {
    	return this.quizNum;
    }

    public int getPoint() {
    	return point;
    }

    public Deck getInCorrectDeck(){
    	return this.inCorrectDeck;
    }

    /** 現在出題中の単語カードを得る */
    public Card getCurrentQuizCard() {
    	return this.currentQuizCard;
    }

    /** 次に出題する単語カードを得る */
    public Card getNextQuizCard() {
    	this.quizNo++;
    	this.currentQuizCard = this.deck.drawAndSetToBottom();
    	return this.currentQuizCard;
    }

    /** 問題を得る */
    public abstract String getQuiz();

    /** 次の問題を得る */
    public abstract String getNextQuiz();

    /** 答えを得る */
    public abstract String getCorrectAnswer();

    /** 正誤のチェック */
    public boolean isCorrectAnswer(String input) {
		String answer = this.getCorrectAnswer().trim();
		input = input.trim();
		if(input.equals(answer)) {
			point++;
			return true;
		} else {
			inCorrectDeck.add(currentQuizCard);
			return false;
		}
    }


}
