package wordapp.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import wordapp.entity.Choice4QuizMaker;

public class Choice4Panel extends JPanel {

	MainFrame mainFrame;
	JPanel wordDisplayPanel;
	JToggleButton choice1ToggleButton,choice2ToggleButton,
				choice3ToggleButton, choice4ToggleButton;
	JButton nextButton, endButton, startButton;
	JLabel numberLabel, wordTitleLabel, wordMeaningLabel, correctMarkLabel, inCorrectMarkLabel;
	Choice4QuizMaker quizMaker;

	/**
	 * Create the panel.
	 */
	public Choice4Panel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();

	}

	private void initComponents() {
		setBounds(100, 100, 800, 600);
		setLayout(null);

		// 単語ディスプレイの設定
		wordDisplayPanel = new JPanel();
		wordDisplayPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		wordDisplayPanel.setLayout(null);
		wordDisplayPanel.setBackground(Color.WHITE);
		wordDisplayPanel.setBounds(161, 10, 467, 285);
		this.add(wordDisplayPanel);

		// クイズ番号ラベルの設定
		numberLabel = new JLabel("");
		numberLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		numberLabel.setBounds(24, 29, 102, 31);
		wordDisplayPanel.add(numberLabel);

		// 単語ラベルの設定
		wordTitleLabel = new JLabel("");
		wordTitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 22));
		wordTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordTitleLabel.setBounds(0, 91, 467, 50);
		wordDisplayPanel.add(wordTitleLabel);

		// 日本語訳ラベルの設定
		wordMeaningLabel = new JLabel("");
		wordMeaningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordMeaningLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 22));
		wordMeaningLabel.setBounds(0, 180, 467, 50);
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

		// 次へボタンの設定
		nextButton = new JButton("次へ");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextButtonActionPerformed(e);
			}
		});
		nextButton.setBounds(292, 513, 208, 31);
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
		endButton.setBounds(620, 513, 107, 31);
		add(endButton);

		// 選択肢1トグルボタンの設定
		choice1ToggleButton = new JToggleButton("choice1");
		choice1ToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choiceToggleButtonActionPerformed(e);
			}
		});
		choice1ToggleButton.setBounds(182, 316, 424, 31);
		choice1ToggleButton.setEnabled(false);
		add(choice1ToggleButton);

		// 選択肢2トグルボタンの設定
		choice2ToggleButton = new JToggleButton("choice2");
		choice2ToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choiceToggleButtonActionPerformed(e);
			}
		});
		choice2ToggleButton.setBounds(182, 368, 424, 31);
		choice2ToggleButton.setEnabled(false);
		add(choice2ToggleButton);

		// 選択肢3トグルボタンの設定
		choice3ToggleButton = new JToggleButton("choice3");
		choice3ToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choiceToggleButtonActionPerformed(e);
			}
		});
		choice3ToggleButton.setBounds(182, 421, 424, 31);
		choice3ToggleButton.setEnabled(false);
		add(choice3ToggleButton);

		// 選択肢4トグルボタンの設定
		choice4ToggleButton = new JToggleButton("choice4");
		choice4ToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choiceToggleButtonActionPerformed(e);
			}
		});
		choice4ToggleButton.setBounds(182, 472, 424, 31);
		choice4ToggleButton.setEnabled(false);
		add(choice4ToggleButton);
	}

	// 次へボタンを押したときの処理
	private void nextButtonActionPerformed(ActionEvent e) {
		// 現在の画面表示をクリアする
		clearWordDisplay();
		nextButton.setVisible(false);
		clear4ChoiceButtons();

		// 全問解答した場合は、クイズ結果を表示する
		if(quizMaker.getQuizNo() >= quizMaker.getQuizNum()) {
			clearAll();
			displayQuizResult();
			return;

		}
		// 次の問題を表示する
		quizMaker.getNextQuiz();
		displayQuestion();

	}

	// 終了ボタンを押したときの処理
	private void endButtonActionPerformed(ActionEvent e) {
		clearAll();
		mainFrame.deck.clear();
		// ホーム画面に戻る
		String cmd = e.getActionCommand();
		mainFrame.layout.show(mainFrame.contentPane, cmd);
	}

	// 選択肢トグルボタンを押したときの処理
	private void choiceToggleButtonActionPerformed(ActionEvent e) {
		// 解答後は解答できなくする
		setEnabled4ChoiceButtons(false);

		// 正誤判定に応じて○×を表示
		String input = e.getActionCommand();
		if(quizMaker.isCorrectAnswer(input)) {
			correctMarkLabel.setVisible(true);
		} else {
			inCorrectMarkLabel.setVisible(true);
		}

		// 正解を表示する
		wordMeaningLabel.setText(quizMaker.getCorrectAnswer());

		nextButton.setEnabled(true);
		nextButton.setVisible(true);
	}

	// 問題を画面に表示する
	public void displayQuestion() {
		numberLabel.setText(quizMaker.getQuizNo() + " / " + quizMaker.getQuizNum());
		wordTitleLabel.setText(quizMaker.getQuiz());
		setEnabled4ChoiceButtons(true);
		set4ChoiceButtons(quizMaker.get4Choices());
	}

	// クイズの結果を表示し、クイズを繰り返すかどうか尋ねる
	private void displayQuizResult() {
		String message = quizMaker.getQuizNum() + "問中" + quizMaker.getPoint() + "問正解しました。\n";
		message += "もう一度クイズをしますか？\n";
		String title = "クイズの結果";

		// 選択に応じて、デッキを作り直してクイズを再開するか、終了するかを決める
		if (quizMaker.getQuizNum() > quizMaker.getPoint()) {	// 間違いが１問でもあった場合
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
				case 0:	// 全て
					quizMaker = new Choice4QuizMaker(quizMaker.getDeck());
					displayQuestion();
					break;
				case 1: // 間違った単語のみ
					quizMaker = new Choice4QuizMaker(quizMaker.getInCorrectDeck());
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
					quizMaker = new Choice4QuizMaker(quizMaker.getDeck());
					displayQuestion();
					break;
				case 1: // 終了
					mainFrame.layout.show(mainFrame.contentPane, "ホーム画面");
					break;
			}
		}
	}


	// ４つの選択肢を４つのボタンに設定する
    private void set4ChoiceButtons(String[] choices) {
    	choice1ToggleButton.setText(choices[0]);
    	choice2ToggleButton.setText(choices[1]);
    	choice3ToggleButton.setText(choices[2]);
    	choice4ToggleButton.setText(choices[3]);
    	// ボタンが押されたときに解答した選択肢をアクションコマンドとして受け取るようにする
    	choice1ToggleButton.setActionCommand(choices[0]);
    	choice2ToggleButton.setActionCommand(choices[1]);
    	choice3ToggleButton.setActionCommand(choices[2]);
    	choice4ToggleButton.setActionCommand(choices[3]);
    }

    // 4つの選択肢ボタンを押せないようにする
    public void setEnabled4ChoiceButtons(boolean b) {
    	choice1ToggleButton.setEnabled(b);
    	choice2ToggleButton.setEnabled(b);
    	choice3ToggleButton.setEnabled(b);
    	choice4ToggleButton.setEnabled(b);
    }

    // ４つの選択肢ボタンのon.off状態を一斉に変更する
    private void setSelected4ChoiceButtons(boolean b) {
    	choice1ToggleButton.setSelected(b);
    	choice2ToggleButton.setSelected(b);
    	choice3ToggleButton.setSelected(b);
    	choice4ToggleButton.setSelected(b);
    }

    // ４つの選択肢ボタンの表示をリセットする
    private void clear4ChoiceButtons() {
    	String[] empty = {"", "", "", ""};
    	set4ChoiceButtons(empty);
    	setEnabled4ChoiceButtons(true);
    	setSelected4ChoiceButtons(false);
    }

    // 単語ディスプレイの表示をクリアする
    private void clearWordDisplay() {
    	correctMarkLabel.setVisible(false);
		inCorrectMarkLabel.setVisible(false);
		numberLabel.setText("");
		wordTitleLabel.setText("");
		wordMeaningLabel.setText("");
    }

    // 画面全てをクリアする
    public void clearAll() {
    	clearWordDisplay();
    	clear4ChoiceButtons();
    	setEnabled4ChoiceButtons(false);
    	nextButton.setEnabled(false);
    	nextButton.setVisible(false);
    }

}
