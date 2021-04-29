package wordapp.gui;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class QuizRangeSetPanel extends ReferenceRangeSetPanel {

	JLabel quizFormLabel, quizNumberLabel;
	JRadioButton choice4Button, jap2EngButton;
	JRadioButton allNumberButton, number10Button, number30Button, number50Button, number100Button;

	/**
	 * Create the panel.
	 */
	public QuizRangeSetPanel() {
		initComponents();
	}

	private void initComponents() {
		// 以下、問題形式を決めるためのコンポーネントの設定
		quizFormLabel = new JLabel("問題形式");
		quizFormLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 16));
		quizFormLabel.setBounds(37, 133, 73, 27);
		this.add(quizFormLabel);

		choice4Button = new JRadioButton("4択問題");
		choice4Button.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		choice4Button.setBounds(47, 180, 90, 21);
		choice4Button.setSelected(true);
		this.add(choice4Button);

		jap2EngButton = new JRadioButton("英訳問題");
		jap2EngButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		jap2EngButton.setBounds(159, 180, 105, 21);
		this.add(jap2EngButton);

		ButtonGroup quizFormGroup = new ButtonGroup();
		quizFormGroup.add(choice4Button);
		quizFormGroup.add(jap2EngButton);

		// 以下、学習範囲の中から何題出題するかを決めるためのコンポーネントの設定
		quizNumberLabel = new JLabel("問題数");
		quizNumberLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 16));
		quizNumberLabel.setBounds(37, 229, 73, 27);
		this.add(quizNumberLabel);

		allNumberButton = new JRadioButton("全て");
		allNumberButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		allNumberButton.setBounds(47, 270, 90, 21);
		allNumberButton.setSelected(true);
		this.add(allNumberButton);

		number10Button = new JRadioButton("10題");
		number10Button.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		number10Button.setBounds(139, 270, 90, 21);
		this.add(number10Button);

		number30Button = new JRadioButton("30題");
		number30Button.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		number30Button.setBounds(234, 270, 90, 21);
		this.add(number30Button);

		number50Button = new JRadioButton("50題");
		number50Button.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		number50Button.setBounds(326, 270, 90, 21);
		this.add(number50Button);

		number100Button = new JRadioButton("100題");
		number100Button.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		number100Button.setBounds(420, 270, 90, 21);
		this.add(number100Button);

		ButtonGroup quizNumberGroup = new ButtonGroup();
		quizNumberGroup.add(allNumberButton);
		quizNumberGroup.add(number10Button);
		quizNumberGroup.add(number30Button);
		quizNumberGroup.add(number50Button);
		quizNumberGroup.add(number100Button);
	}

}
