package wordapp.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import wordapp.dao.CardsDAO;
import wordapp.entity.Card;
import wordapp.entity.Deck;

public class WordReferencePanel extends JPanel {

	MainFrame mainFrame;
	JScrollPane scrollPane;
	JList<Card> wordsList;
	JPanel wordDisplayPanel;
	JLabel titleLabel, meaningLabel;
	JButton nextButton, previousButton, returnButton, rangeChangeButton, wordDeleteButton;
	DefaultListModel<Card> model = new DefaultListModel<>();

	/**
	 * Create the panel.
	 */
	public WordReferencePanel(MainFrame mainFrame) {

		this.mainFrame = mainFrame;
		initComponents();

	}
	private void initComponents() {
		this.setLayout(null);
		this.setBounds(100,100,800,600);

		// 単語ディスプレイの設定
		wordDisplayPanel = new JPanel();
		wordDisplayPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		wordDisplayPanel.setBackground(Color.WHITE);
		wordDisplayPanel.setBounds(234, 65, 477, 284);
		this.add(wordDisplayPanel);
		wordDisplayPanel.setLayout(null);


		// 単語ラベルの設定
		titleLabel = new JLabel("");
		titleLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 22));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 53, 477, 77);
		wordDisplayPanel.add(titleLabel);

		// 日本語訳ラベルの設定
		meaningLabel = new JLabel("");
		meaningLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 22));
		meaningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		meaningLabel.setBounds(0, 140, 477, 77);
		wordDisplayPanel.add(meaningLabel);

		// 単語リスト表示部分の設定
		scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 47, 170, 455);
		this.add(scrollPane);

		wordsList = new JList<Card>();
		wordsList.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		wordsList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				wordsListKeyPressed(e);
			}
		});
		wordsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				wordsListMouseClicked(e);
			}
		});
		wordsList.setToolTipText("複数選択:Ctrl + click or Shift+click");
		wordsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(wordsList);

		// 次へボタンの設定
		nextButton = new JButton("次へ");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextButtonActionPerformed(e);
			}
		});
		nextButton.setBounds(488, 397, 147, 33);
		this.add(nextButton);

		// 前へボタンの設定
		previousButton = new JButton("前へ");
		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousButtonActionPerformed(e);

			}
		});
		previousButton.setBounds(310, 397, 147, 33);
		this.add(previousButton,"単語の復習");

		// 戻るボタンの設定
		returnButton = new JButton("戻る");
		returnButton.setActionCommand("ホーム画面");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnButtonActionPerformed(e);
			}
		});
		returnButton.setBounds(564, 452, 147, 33);
		this.add(returnButton);

		// 閲覧範囲変更ボタンの設定
		rangeChangeButton = new JButton("閲覧範囲の変更");
		rangeChangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rangeChangeButtonActionPerformed(e);
			}
		});
		rangeChangeButton.setBounds(234, 452, 147, 33);
		add(rangeChangeButton);

		// 登録抹消ボタンの設定
		wordDeleteButton = new JButton("登録抹消");
		wordDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordDeleteButtonActionPerformed(e);
			}
		});
		wordDeleteButton.setToolTipText("左のリストから選択してください");
		wordDeleteButton.setBounds(402, 452, 147, 33);
		add(wordDeleteButton);
	}

	// 単語リスト一覧をPgUpキーまたはPgDnキーで操作するときの処理
	private void wordsListKeyPressed(KeyEvent e) {
		// カードを一枚戻し、ディスプレイに表示
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(wordsList.getSelectedIndex() != 0) {
				mainFrame.deck.drawFromBottomAndSetToTop();
				Card card = mainFrame.deck.peek();
				setWordDisplay(card);
			}
		}
		// カードを一枚めくり、ディスプレイに表示
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(wordsList.getSelectedIndex() != wordsList.getModel().getSize()-1) {
				mainFrame.deck.drawAndSetToBottom();
				Card card = mainFrame.deck.peek();
				setWordDisplay(card);
			}
		}
	}

	// 単語リスト一覧をクリックしたときの処理
	private void wordsListMouseClicked(MouseEvent e) {
		// クリックした単語カードがデッキのトップにくるまでめくり、ディスプレイに表示
		Card selectedCard = (Card)wordsList.getSelectedValue();
		for(int i = 0; i < mainFrame.deck.size(); i++) {
			Card card = mainFrame.deck.peek();
			if(card.equals(selectedCard)) {
				setWordDisplay(card);
				break;
			}
			mainFrame.deck.drawAndSetToBottom();
		}

	}

	// 次へボタンを押したときの処理
	private void nextButtonActionPerformed(ActionEvent e) {
		// 単語カードを一枚めくり、ディスプレイに表示
		try {
			mainFrame.deck.drawAndSetToBottom();
			Card card = mainFrame.deck.peek();
			setWordDisplay(card);
			wordsList.setSelectedValue(card, true);
		} catch (NoSuchElementException e2) {
			JOptionPane.showMessageDialog(null, "リストに単語が設定されていません。");
		}
	}
	// 前へボタンを押したときの処理
	private void previousButtonActionPerformed(ActionEvent e) {
		// 単語カードを一枚戻し、ディスプレイに表示
		try {
			mainFrame.deck.drawFromBottomAndSetToTop();
			Card card = mainFrame.deck.peek();
			setWordDisplay(card);
			wordsList.setSelectedValue(card, true);
		} catch (NoSuchElementException e2) {
			JOptionPane.showMessageDialog(null, "リストに単語が設定されていません。");
		}
	}
	// 終了ボタンを押したときの処理
	private void returnButtonActionPerformed(ActionEvent e) {

		clearAllDisplay();
		// ホーム画面に戻る
		String cmd = e.getActionCommand();
		mainFrame.layout.show(mainFrame.contentPane, cmd);
	}
	// 閲覧範囲変更ボタンを押したときの処理
	private void rangeChangeButtonActionPerformed(ActionEvent e) {
		try {
			clearAllDisplay();
			DialogToSetDeck dialog = new DialogToSetDeck(mainFrame);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	// 登録抹消ボタンを押したときの処理
	private void wordDeleteButtonActionPerformed(ActionEvent e) {
		// 単語リストで選択されたカードを元のデッキから削除し、削除するカードだけを集めたデッキを作る
		List<Card> cards = wordsList.getSelectedValuesList();
		Deck deckToDelete = new Deck();
		for(Card card: cards) {
			mainFrame.deck.remove(card);
			deckToDelete.add(card);
		}

		if(wordsList.isSelectionEmpty()) {
			JOptionPane.showMessageDialog(null, "登録抹消する単語が選択されていません");
		} else {
			String comment = "選択した単語を削除しますか？";
			int option = JOptionPane.showConfirmDialog(null, comment, "単語の削除",JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				// データベースから単語カードを削除
				int count = CardsDAO.deleteCards(deckToDelete);
				// データベースから削除された単語カードは単語リストに表示されないようにする
				model = (DefaultListModel<Card>)wordsList.getModel();
				for(Card card: cards) {
					model.removeElement(card);
				}
				wordsList.setModel(model);
				// 残ったデッキの一番上のカードを画面に表示して、単語リストの方でも選択状態にする
				Card card = mainFrame.deck.peek();
				if(card != null) {
					wordsList.setSelectedValue(mainFrame.deck.peek(), true);
					setWordDisplay(mainFrame.deck.peek());
				} else {
					setWordDisplay(new Card("",""));
				}
				JOptionPane.showMessageDialog(null, count + "件登録を抹消しました");
			}
		}
	}

	// 単語表示ディスプレイに単語カードを表示する
	private void setWordDisplay(Card card) {
		String title = card.getTitle();
		String meaning = card.getMeaning();
		titleLabel.setText(title);
		meaningLabel.setText(meaning);
	}

	// 画面に表示されているものを全てクリア
	private void clearAllDisplay( ) {
				setWordDisplay(new Card("",""));
				model.clear();
				wordsList.setModel(model);
	}
}
