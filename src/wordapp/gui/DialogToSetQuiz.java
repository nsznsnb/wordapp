package wordapp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import wordapp.dao.CardsDAO;
import wordapp.entity.Choice4QuizMaker;
import wordapp.entity.Deck;
import wordapp.entity.Jap2EngQuizMaker;

public class DialogToSetQuiz extends JDialog {

	MainFrame mainFrame;
	QuizRangeSetPanel quizRangeSetPanel;
	JPanel buttonPane;
	JButton startButton, cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*		try {
					DialogToSetQuiz dialog = new DialogToSetQuiz();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				*/
	}

	/**
	 * Create the dialog.
	 */
	public DialogToSetQuiz(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();

	}

	private void initComponents() {
		setBounds(100, 100, 600, 400);
		setTitle("クイズの設定");
		setModal(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		quizRangeSetPanel = new QuizRangeSetPanel();
		getContentPane().add(quizRangeSetPanel, BorderLayout.CENTER);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				// スタートボタンの設定
				startButton = new JButton("START");
				startButton.setActionCommand("OK");
				startButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				buttonPane.add(startButton);
				getRootPane().setDefaultButton(startButton);
			}
			{
				// キャンセルボタンの設定
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("ホーム画面");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelButtonActionPerformed(e);
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

	// OKボタンを押したときの処理
	private void okButtonActionPerformed(ActionEvent e) {
		// 指定された学習範囲の単語カードをデータベースからデッキにセットする
		mainFrame.deck = new Deck();
		LocalDate selectedTorokubi = (LocalDate)quizRangeSetPanel.torokubiComboBox.getSelectedItem();
		if(quizRangeSetPanel.torokubiComboBox.getSelectedItem() != null) {
			mainFrame.deck.addCards(CardsDAO.findCardsRegisteredDateX(selectedTorokubi));
		}
		if(quizRangeSetPanel.aDayAgoRadioButton.isSelected()) {
			mainFrame.deck.addCards(CardsDAO.findCardsRegistered1DayAgo(selectedTorokubi));
		}
		if(quizRangeSetPanel.aWeekAgoRadioButton.isSelected()) {
			mainFrame.deck.addCards(CardsDAO.findCardsRegistered1WeekAgo(selectedTorokubi));
		}
		if(quizRangeSetPanel.aMonthAgoRadioButton.isSelected()) {
			mainFrame.deck.addCards(CardsDAO.findCardsRegistered1MonthAgo(selectedTorokubi));
		}
		if(quizRangeSetPanel.allRadioButton.isSelected()) {
			mainFrame.deck.addCards(CardsDAO.findAllCards());
		}
		DialogToSetQuiz.this.setVisible(false);

		// 指定された問題数に応じて、学習範囲のカードを集めたデッキから、さらに数を絞ったデッキを作る
		if(quizRangeSetPanel.number10Button.isSelected()) {
			mainFrame.deck = mainFrame.deck.createNewDeck(10);
		}
		if(quizRangeSetPanel.number30Button.isSelected()) {
			mainFrame.deck = mainFrame.deck.createNewDeck(30);
		}
		if(quizRangeSetPanel.number50Button.isSelected()) {
			mainFrame.deck = mainFrame.deck.createNewDeck(50);
		}
		if(quizRangeSetPanel.number100Button.isSelected()) {
			mainFrame.deck = mainFrame.deck.createNewDeck(100);
		}

		// 指定された問題形式に応じてクイズ画面を表示して、クイズを始める
		if(mainFrame.deck.isEmpty()) {
			JOptionPane.showMessageDialog(null, "単語が登録されていません");
		} else {
			if(quizRangeSetPanel.choice4Button.isSelected()) {
				mainFrame.wordQuizPanel.layout.show(mainFrame.wordQuizPanel,"４択問題パネル");
				mainFrame.wordQuizPanel.choice4Panel.quizMaker = new Choice4QuizMaker(mainFrame.deck);
				mainFrame.wordQuizPanel.choice4Panel.displayQuestion();
			} else {
				mainFrame.wordQuizPanel.layout.show(mainFrame.wordQuizPanel,"英訳問題パネル");
				mainFrame.wordQuizPanel.jap2EngPanel.quizMaker = new Jap2EngQuizMaker(mainFrame.deck);
				mainFrame.wordQuizPanel.jap2EngPanel.displayQuestion();
			}

		}
	}



	// キャンセルボタンを押したときの処理
	private void cancelButtonActionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		DialogToSetQuiz.this.setVisible(false);
		mainFrame.layout.show(mainFrame.contentPane, cmd);
	}


}
