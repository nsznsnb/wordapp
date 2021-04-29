package wordapp.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import wordapp.entity.Card;
import wordapp.entity.Deck;

public class CSVReader {
	public static List<Card> csvToCardList(String filename) {
    	String format = "[yyyy/M/d][yyyy/MM/dd][yyyy-M-d][yyyy-MM-dd]";
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        List<Card> cards = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(
                                    new FileReader(filename));) {
            String line;
            String[] data;
            while((line = br.readLine()) != null) {
                data = line.split(",");
                cards.add(new Card(data[0],data[1],LocalDate.parse(data[2],formatter)));
            }
            return cards;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
    public static Deck csv2Deck(String filename) {
    	String format = "[yyyy/M/d][yyyy/MM/dd][yyyy-M-d][yyyy-MM-dd]";
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        Deck deck = new Deck();
        try(BufferedReader br = new BufferedReader(
                                    new FileReader(filename));) {
            String line;
            String[] data;
            while((line = br.readLine()) != null) {
                data = line.split(",");
                deck.add(new Card(data[0],data[1],LocalDate.parse(data[2],formatter)));
            }
            return deck;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
