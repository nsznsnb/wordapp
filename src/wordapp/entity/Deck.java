package wordapp.entity;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private LinkedList<Card> cards;
    public Deck() {
        cards = new LinkedList<>();
    }
    public Deck(LinkedList<Card> cards) {
        this.cards = cards;
    }
    /** カードをデッキに加える */
    public void add(Card card) {
        cards.add(card);
    }

    /** カードの束をデッキに加える */
    public void addCards(LinkedList<Card> cards) {
    	this.cards.addAll(cards);
    }

    /** デッキの上のカードを見る */
    public Card peek() {
    	return cards.peek();
    }

    /** デッキの上からカードを引く */
    public Card draw() {
        return cards.pop();
    }

    /** デッキの底からカードを引く */
    public Card drawFromBottom() {
    	return cards.removeLast();
    }

    /** デッキの上からi番目を得る */
    public Card get(int i) {
        return cards.get(i);
    }

    /** カードをデッキの上に置く */
    public Card setToTop(Card card) {
    	cards.push(card);
    	return card;
    }

    /** カードをデッキの底に回す */
    public Card setToBottom(Card card) {
        cards.addLast(card);
        return card;
    }

    /** カードを一枚底から抜いて、デッキの上に置く */
    public Card drawFromBottomAndSetToTop() {
    	return setToTop(drawFromBottom());
    }

    /** デッキの上にあるカードをデッキの底に回す */
    public Card drawAndSetToBottom() {
    	return setToBottom(draw());
    }


    public Card search(String targetTitle) {
    	for(int i = 0; i < this.size(); i++) {
    		Card card = this.drawAndSetToBottom();
    		String title = card.getTitle();
    		if(title.equals(targetTitle)) {
    			return card;
    		}
    	}
    	return null;
    }

    /** デッキからカードを削除する */
    public void remove(Card card) {
    	cards.remove(card);
    }
    /** デッキを単語見出しで昇順に並べ替える */
    public void sortDeckInAsc() {
    	Collections.sort(cards);
    }

    /** デッキを単語見出しで降順に並べ替える */
    public void sortDeckInDesc() {
    	Collections.sort(cards, Collections.reverseOrder());
    }

    /** デッキをシャッフルする */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /** 上からカードを複数枚とり、新たなデッキを作る */
    public Deck createNewDeck(int num) {
    	if(num > this.size()) {
    		num = this.size();
    	}
    	this.shuffle();
    	Deck deck = new Deck();
    	for(int i = 0; i < num; i++) {
    		deck.add(this.draw());
    	}

    	return deck;
    }


    /** デッキを指定の枚数まで減らす */
    public void decreaseDeck(int num) {
    	while(this.size() > num) {
    		this.draw();
    	}
    }

    /** すべてのカード見出しを得る */
    public String[] getAllTitles() {
    	String[] titles = new String[this.size()];
    	for(int i = 0; i < this.size(); i++) {
    		Card card = drawAndSetToBottom();
    		titles[i] = card.getTitle();
    	}
    	return titles;
    }

    /** 全カードの表示*/
    public void show() {
        for(Card card: cards) {
            System.out.println(card);
        }
    }

    /** デッキのカードをすべて破棄する */
    public void clear() {
    	cards.clear();
    }

    /** デッキの枚数を取得 */
    public int size() {
        return cards.size();
    }

    /** デッキが空かどうかを取得  */
    public boolean isEmpty() {
    	return cards.isEmpty();
    }
}
