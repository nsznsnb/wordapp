package wordapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import wordapp.entity.Card;
import wordapp.entity.Deck;

public class CardsDAO {
	private static final String serverName = "localhost";
    private static final String portNumber = "5432";
    private static final String dbName = "wordapp";
    private static final String url = "jdbc:postgresql://"+ serverName + ":" + portNumber +"/" + dbName;
    // postgreSQLのユーザー名を自分のものに書き換えてください
    private static final String user = "postgres";
    // postgreSQLのパスワードを自分のものに書き換えてください
    private static final String password = "";


    /** 日付xの１日前のカードを見つける */
    public static LinkedList<Card> findCardsRegistered1DayAgo(LocalDate x) {
        return findCardsRegisteredDateX(x.minusDays(1));
    }
    /** １週間前のカードを見つける */
    public static LinkedList<Card> findCardsRegistered1WeekAgo(LocalDate x) {
        return findCardsRegisteredDateX(x.minusDays(7));
    }
    /** １ヶ月前のカードを見つける */
    public static LinkedList<Card> findCardsRegistered1MonthAgo(LocalDate x) {
        return findCardsRegisteredDateX(x.minusMonths(1));
    }
    /** 日付xのカードを見つける */
    public static LinkedList<Card> findCardsRegisteredDateX(LocalDate x) {


        try(Connection con = DriverManager.getConnection(url, user, password);) {

            PreparedStatement pstmt = con.prepareStatement
                        ("SELECT * FROM Cardslist WHERE torokubi = ?");
            pstmt.setDate(1,java.sql.Date.valueOf(x));
            ResultSet rs = pstmt.executeQuery();
            LinkedList<Card> cards = new LinkedList<>();
            while(rs.next()) {
                Card card = new Card();
                card.setTitle(rs.getString("card_title"));
                card.setMeaning(rs.getString("card_meaning"));
                card.setTorokubi(rs.getDate("torokubi").toLocalDate());
                cards.add(card);
            }
            rs.close();
            pstmt.close();
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 全てのカードを見つける */
    public static LinkedList<Card> findAllCards()  {

        try(Connection con = DriverManager.getConnection(url, user, password);) {

            PreparedStatement pstmt = con.prepareStatement
                        ("SELECT * FROM Cardslist ORDER BY torokubi");
            ResultSet rs = pstmt.executeQuery();
            LinkedList<Card> cards = new LinkedList<>();
            while(rs.next()) {
                Card card = new Card();
                card.setTitle(rs.getString("card_title"));
                card.setMeaning(rs.getString("card_meaning"));
                card.setTorokubi(rs.getDate("torokubi").toLocalDate());
                cards.add(card);
            }
            rs.close();
            pstmt.close();
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 複数のカードを登録 */
    public static int registerCards(Deck deck) {


        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            // データベースに存在しないデータのみ登録する
            String sql = "INSERT INTO Cardslist"
            			+ " SELECT * FROM (VALUES (?, ?, cast(? as Date))) AS tcl(card_title, card_meaning, torokubi)"
            			+ " WHERE NOT EXISTS (SELECT * FROM Cardslist AS cl"
            					+ " WHERE cl.card_title = tcl.card_title)";
            PreparedStatement pstmt = con.prepareStatement(sql);
           int count = 0;
            for(int i = 0; i < deck.size(); i++) {
            	Card card = deck.drawAndSetToBottom();
                pstmt.setString(1,card.getTitle());
                pstmt.setString(2,card.getMeaning());
                pstmt.setDate(3,java.sql.Date.valueOf(card.getTorokubi()));
                count += pstmt.executeUpdate();
            }
            con.commit();
            pstmt.close();
            return count;
        } catch (SQLException e) {
            try {
                con.rollback();
                e.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            return 0;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /** １枚のカードを登録 */
    public static void registerCard(Card card) {

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement
                        ("INSERT INTO Cardslist VALUES (?,?,?)");
            pstmt.setString(1,card.getTitle());
            pstmt.setString(2,card.getMeaning());
            pstmt.setDate(3,java.sql.Date.valueOf(card.getTorokubi()));
            pstmt.executeUpdate();
            con.commit();
            pstmt.close();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 複数枚のカードを削除 */
    public static int deleteCards(Deck deck) {

    	int count = 0;
        try(Connection con = DriverManager.getConnection(url, user, password);) {
        	String sql = "DELETE FROM Cardslist WHERE card_title = ? AND card_meaning = ?";
    		PreparedStatement pstmt = con.prepareStatement(sql);
        	for(int i = 0; i < deck.size(); i++) {
        		Card card = deck.drawAndSetToBottom();
        		pstmt.setString(1,card.getTitle());
        		pstmt.setString(2,card.getMeaning());
        		count += pstmt.executeUpdate();
        	}
            pstmt.close();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }

    }

    /** １枚のカードを削除 */
    public static void deleteCard(Card card) {

    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}

        try(Connection con = DriverManager.getConnection(url, user, password);) {

            String sql = "DELETE FROM Cardslist WHERE card_title = ? AND card_meaning = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,card.getTitle());
            pstmt.setString(2,card.getMeaning());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /** 登録日を重複無しで全て取得する */
    public static List<LocalDate> findAllTorokubi()  {
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}

        try(Connection con = DriverManager.getConnection(url, user, password);) {

            PreparedStatement pstmt = con.prepareStatement
                        ("SELECT DISTINCT torokubi FROM Cardslist ORDER BY torokubi DESC");
            ResultSet rs = pstmt.executeQuery();
            List<LocalDate> torokubis = new ArrayList<>();
            while(rs.next()) {
                torokubis.add(rs.getDate("torokubi").toLocalDate());
            }
            rs.close();
            pstmt.close();
            return  torokubis;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
