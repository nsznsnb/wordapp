package wordapp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import wordapp.dao.CardsDAO;
import wordapp.entity.Card;
import wordapp.entity.Deck;


public class DialogToSetDeck extends JDialog {

	MainFrame mainFrame;
	JPanel contentPanel = new JPanel();
	ReferenceRangeSetPanel rangeSetPanel;
	JLabel rangeLabel;
	JRadioButton  aDayAgoRadioButton, aWeekAgoRadioButton, aMonthAgoRadioButton, allRadioButton, myFavoriteButton;
	//JComboBox<LocalDate> torokubiComboBox;
	JPanel buttonPane;
	JButton okButton,cancelButton;
	DefaultListModel<Card> wordListModel = new DefaultListModel<>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			/*			DialogToSetDeck dialog = new DialogToSetDeck();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogToSetDeck(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();
	}

	private void initComponents() {
		setBounds(100, 100, 550, 300);
		setTitle("閲覧範囲の設定");
		setModal(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		rangeSetPanel = new ReferenceRangeSetPanel();
		getContentPane().add(rangeSetPanel, BorderLayout.CENTER);




		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				// OKボタン設定
				okButton = new JButton("OK");
				okButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				// キャンセルボタンの設定
				cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
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
		LocalDate selectedTorokubi = (LocalDate)rangeSetPanel.torokubiComboBox.getSelectedItem();
		if(rangeSetPanel.torokubiComboBox.getSelectedItem() != null) {
			mainFrame.deck.addCards(CardsDAO.findCardsRegisteredDateX(selectedTorokubi));
		}
		if(rangeSetPanel.aDayAgoRadioButton.isSelected()) {
			mainFrame.deck.addCards(CardsDAO.findCardsRegistered1DayAgo(selectedTorokubi));
		}
		if(rangeSetPanel.aWeekAgoRadioButton.isSelected()) {
			mainFrame.deck.addCards(CardsDAO.findCardsRegistered1WeekAgo(selectedTorokubi));
		}
		if(rangeSetPanel.aMonthAgoRadioButton.isSelected()) {
			mainFrame.deck.addCards(CardsDAO.findCardsRegistered1MonthAgo(selectedTorokubi));
		}
		if(rangeSetPanel.allRadioButton.isSelected()) {
			mainFrame.deck.addCards(CardsDAO.findAllCards());
		}
		DialogToSetDeck.this.setVisible(false);

		// デッキを単語で昇順に並べ替える
		mainFrame.deck.sortDeckInAsc();

		// デッキを単語リストにセットして、閲覧画面に表示
		for(int i = 0; i < mainFrame.deck.size(); i++) {
			Card card = mainFrame.deck.drawAndSetToBottom();
			wordListModel.addElement(card);

		}
		mainFrame.wordReferencePanel.wordsList.setModel(wordListModel);

		if(mainFrame.deck.isEmpty()) {
			JOptionPane.showMessageDialog(null, "単語が登録されていません");
		} else {
			// デッキのトップにあるカードを単語表示ディスプレイに表示
			Card card = mainFrame.deck.peek();
			String title = card.getTitle();
			String meaning = card.getMeaning();
			mainFrame.wordReferencePanel.wordsList.setSelectedValue(card, true);
			mainFrame.wordReferencePanel.titleLabel.setText(title);
			mainFrame.wordReferencePanel.meaningLabel.setText(meaning);
		}
	}

	// キャンセルボタンを押したときの処理
	private void cancelButtonActionPerformed(ActionEvent e) {
		// ホーム画面に戻る
		String cmd = e.getActionCommand();
		DialogToSetDeck.this.setVisible(false);
		mainFrame.layout.show(mainFrame.contentPane, cmd);
	}


}
