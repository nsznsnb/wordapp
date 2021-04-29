package wordapp.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import wordapp.entity.Jap2EngQuizMaker;

public class Jap2EngPanel extends JPanel {

	MainFrame mainFrame;
	JPanel wordDisplayPanel;
	JTextField wordInputField;
	JButton nextButton, endButton, hintButton;
	JLabel numberLabel,wordMeaningLabel, wordTitleLabel, correctMarkLabel, inCorrectMarkLabel;
	JTextField wordInputHelpField;
	JButton answerButton;
	Jap2EngQuizMaker quizMaker;
	/**
	 * Create the panel.
	 */
	public Jap2EngPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();

	}

	private void initComponents() {
		setLayout(null);
		this.setBounds(100, 100, 800, 600);

		// 単語ディスプレイの設定
		wordDisplayPanel = new JPanel();
		wordDisplayPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		wordDisplayPanel.setBackground(Color.WHITE);
		wordDisplayPanel.setBounds(160, 29, 467, 306);
		this.add(wordDisplayPanel);
		wordDisplayPanel.setLayout(null);

		// クイズ番号ラベルの設定
		numberLabel = new JLabel("");
		numberLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		numberLabel.setBounds(24, 29, 102, 31);
		wordDisplayPanel.add(numberLabel);

		// 単語ラベルの設定
		wordTitleLabel = new JLabel("");
		wordTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordTitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 22));
		wordTitleLabel.setBounds(0, 180, 467, 50);
		wordDisplayPanel.add(wordTitleLabel);

		// 日本語訳ラベルの設定
		wordMeaningLabel = new JLabel("");
		wordMeaningLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 22));
		wordMeaningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordMeaningLabel.setBounds(0, 91, 467, 50);
		wordDisplayPanel.add(wordMeaningLabel);

		// 正解マークラベルの設定
		correctMarkLabel = new JLabel("○");
		correctMarkLabel.setBounds(-52, -43, 237, 207);
		wordDisplayPanel.add(correctMarkLabel);
		correctMarkLabel.setForeground(Color.GREEN);
		correctMarkLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 99));
		correctMarkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		correctMarkLabel.setVisible(false);

		// 不正解マークラベルの設定
		inCorrectMarkLabel = new JLabel("×");
		inCorrectMarkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inCorrectMarkLabel.setForeground(Color.RED);
		inCorrectMarkLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 99));
		inCorrectMarkLabel.setBounds(-52, -43, 237, 207);
		inCorrectMarkLabel.setVisible(false);
		wordDisplayPanel.add(inCorrectMarkLabel);

		// 入力フィールドの設定
		wordInputField = new JTextField();
		wordInputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordInputFieldActionPerformed(e);
			}
		});
		wordInputField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
		wordInputField.setBounds(159, 366, 468, 39);
		this.add(wordInputField);
		wordInputField.setColumns(10);

		// 入力補助フィールドの設定
		wordInputHelpField = new JTextField();
		wordInputHelpField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
		wordInputHelpField.setColumns(10);
		wordInputHelpField.setBounds(160, 404, 467, 21);
		wordInputHelpField.setEditable(false);
		add(wordInputHelpField);

		// ヒントボタンの設定
		hintButton = new JButton("ヒント");
		hintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hintButtonActionPerformed(e);
			}
		});
		hintButton.setBounds(637, 369, 107, 39);
		add(hintButton);

		// 解答ボタンの設定
		answerButton = new JButton("解答");
		answerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answerButtonActionPerformed(e);
			}
		});
		answerButton.setBounds(282, 432, 208, 39);
		add(answerButton);

		// 次へボタンの設定
		nextButton = new JButton("次へ");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextButtonActionPerformed(e);
			}
		});
		nextButton.setBounds(282, 432, 208, 39);
		nextButton.setVisible(false);
		nextButton.setActionCommand("次へボタン");
		this.add(nextButton);


		// 終了ボタンの設定
		endButton = new JButton("終了");
		endButton.setActionCommand("ホーム画面");
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endButtonActionPerformed(e);
			}
		});
		endButton.setBounds(616, 494, 107, 39);
		add(endButton);

	}

	// 入力フィールドでEnterキーを押したときの処理
	private void wordInputFieldActionPerformed(ActionEvent e) {
		// 解答後は解答できなくする
		setAnswerable(false);

		// 正誤判定に応じて○×を表示
		String input = wordInputField.getText().trim();
		if(quizMaker.isCorrectAnswer(input)) {
			correctMarkLabel.setVisible(true);
		} else {
			inCorrectMarkLabel.setVisible(true);
		}
		// 正解の表示
		wordTitleLabel.setText(quizMaker.getCorrectAnswer());

		answerButton.setVisible(false);
		nextButton.setVisible(true);
		nextButton.setEnabled(true);

	}

	// 次へボタンを押したときの処理
	private void nextButtonActionPerformed(ActionEvent e) {
		// 現在の画面表示をクリアする
		allClear();

		// 全問解答した場合は、クイズ結果を表示する
		if(quizMaker.getQuizNo() >= quizMaker.getQuizNum()) {
			displayQuizResult();
			return;

		}

		// 次の問題を表示する
		quizMaker.getNextQuiz();
		displayQuestion();

	}

	// 終了ボタンを押したときの処理
	private void endButtonActionPerformed(ActionEvent e) {
		setAnswerable(false);
		nextButton.setEnabled(false);
		allClear();
		mainFrame.deck.clear();
		String cmd = e.getActionCommand();
		mainFrame.layout.show(mainFrame.contentPane, cmd);
	}
	// ヒントボタンを押したときの処理
	private void hintButtonActionPerformed(ActionEvent e) {
		// 入力補助フィールドにヒントを出す
		wordInputHelpField.setText(quizMaker.makeHint());
	}
	// 解答ボタンを押したときの処理
	private void answerButtonActionPerformed(ActionEvent e) {
		// 解答後は解答できなくする
		setAnswerable(false);

		// 正誤判定に応じて○×を表示
		String input = wordInputField.getText().trim();
		if(quizMaker.isCorrectAnswer(input)) {
			correctMarkLabel.setVisible(true);
		} else {
			inCorrectMarkLabel.setVisible(true);
		}

		// 正解の表示
		wordTitleLabel.setText(quizMaker.getCorrectAnswer());

		answerButton.setVisible(false);
		nextButton.setVisible(true);
		nextButton.setEnabled(true);
	}

	// 問題を画面に表示
	public void displayQuestion() {
		numberLabel.setText(quizMaker.getQuizNo() + " / " + quizMaker.getQuizNum());
		wordMeaningLabel.setText(quizMaker.getQuiz());
		wordInputHelpField.setText(quizMaker.makeEmptyHint());
		setAnswerable(true);
	}

	// クイズの結果を表示し、クイズを繰り返すかどうか尋ねる
	private void displayQuizResult() {
		String message = quizMaker.getQuizNum() + "問中" + quizMaker.getPoint() + "問正解しました。\n";
		message += "もう一度クイズをしますか？\n";
		String title = "クイズの結果";

		// 選択に応じて、デッキを作り直してクイズを再開するか、終了するかを決める
		if (quizMaker.getQuizNum() > quizMaker.getPoint()) { // 間違いが１問でもあった場合
			String[] choices = {"全て", "間違った単語のみ", "終了" };
			int val = JOptionPane.showOptionDialog(null,
					message,
					title,
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					choices,
					choices[0]
					);
			switch(val) {
				case 0: // 全て
					quizMaker = new Jap2EngQuizMaker(quizMaker.getDeck());
					displayQuestion();
					break;
				case 1: // 間違えた問題のみ
					quizMaker = new Jap2EngQuizMaker(quizMaker.getInCorrectDeck());
					displayQuestion();
					break;
				case 2: // 終了
					mainFrame.layout.show(mainFrame.contentPane, "ホーム画面");
					break;
			}
		} else if (quizMaker.getQuizNum() == quizMaker.getPoint()) { // 全問正解の場合
			String[] choices = {"全て",  "終了" };
			int val = JOptionPane.showOptionDialog(null,
					message,
					title,
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					choices,
					choices[0]
					);
			switch(val) {
				case 0: // 全て
					quizMaker = new Jap2EngQuizMaker(quizMaker.getDeck());
					displayQuestion();
					break;
				case 1: // 終了
					mainFrame.layout.show(mainFrame.contentPane, "ホーム画面");
					break;
			}
		}
	}

	// 解答できる状態かどうかを定める
	private void setAnswerable(boolean b) {
		wordInputField.setEditable(b);
		hintButton.setEnabled(b);
		answerButton.setEnabled(b);
	}

	// 単語ディスプレイをクリアする
	private void clearWordDisplay() {
		correctMarkLabel.setVisible(false);
		inCorrectMarkLabel.setVisible(false);
		numberLabel.setText("");
		wordTitleLabel.setText("");
		wordMeaningLabel.setText("");
	}

	//
	private void clearInputComponent() {
		wordInputField.setText("");
		wordInputHelpField.setText("");
	}

	// 画面の表示をクリアする
	public void allClear() {
		clearWordDisplay();
		clearInputComponent();
		setAnswerable(false);
		wordInputField.requestFocus();
		nextButton.setVisible(false);
		answerButton.setVisible(true);
	}

}
