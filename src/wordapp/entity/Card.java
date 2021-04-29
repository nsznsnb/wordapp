package wordapp.entity;
import java.time.LocalDate;

public class Card implements Comparable<Card> {
    /** 見出し */
    private String title;
    /** 日本語訳 */
    private String meaning;
    /** 登録日 */
    private LocalDate torokubi = LocalDate.now();

    public Card() {
    }

    public Card(String title, String meaning) {
        this.title = title;
        this.meaning = meaning;
    }
    public Card(String title, String meaning, LocalDate torokubi) {
        this(title, meaning);
        this.torokubi = torokubi;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setTorokubi(LocalDate torokubi) {
        this.torokubi = torokubi;
    }

    public String getTitle() { return this.title; }
    public String getMeaning() { return this.meaning; }
    public LocalDate getTorokubi() { return this.torokubi; }

    public boolean equals(Object o) {
    	if(o == this) return true;
    	if(o == null) return false;
    	if(!(o instanceof Card)) return false;
    	Card card = (Card) o;
    	if(!this.title.trim().equals(card.getTitle().trim())) {
    		return false;
    	}
    	if(!this.meaning.trim().equals(card.getMeaning().trim())) {
    		return false;
    	}
    	return true;
    }

    public String toString() {
    	return this.title + " / " + this.meaning + " / " + this.torokubi;
    }

    public int compareTo(Card card) {
    	return this.title.compareTo(card.getTitle());
    }

}
