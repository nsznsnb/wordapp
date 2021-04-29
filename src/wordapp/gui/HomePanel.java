package wordapp.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class HomePanel extends JPanel implements ActionListener {

	MainFrame mainFrame;
	JPanel appTitlePanel;
	JLabel appTitleLabel;
	JButton wordReferenceButton,wordTestButton,wordRegisterButton,btnNewButton_3;
	private JButton closeButton;
	/**
	 * Create the panel.
	 */
	public HomePanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();
	}

	private void initComponents() {
		this.setBounds(100, 100, 800, 600);

		this.setLayout(null);

		// アプリタイトル用パネルの設定
		appTitlePanel = new JPanel();
		appTitlePanel.setBackground(SystemColor.activeCaption);
		appTitlePanel.setBounds(0, 0, 800, 97);
		this.add(appTitlePanel);
		appTitlePanel.setLayout(new BorderLayout(0, 0));

		// アプリタイトルラベルの設定
		appTitleLabel = new JLabel("MY ENGLISH APP");
		appTitleLabel.setForeground(SystemColor.text);
		appTitleLabel.setFont(new Font("MS UI Gothic", Font.ITALIC, 40));
		appTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		appTitlePanel.add(appTitleLabel, BorderLayout.CENTER);

		// 単語管理ボタンの設定
		wordReferenceButton = new JButton("登録単語の管理");
		wordReferenceButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 20));
		wordReferenceButton.setBounds(166, 167, 427, 42);
		wordReferenceButton.setActionCommand("英単語の管理画面");
		wordReferenceButton.addActionListener(this);
		this.add(wordReferenceButton);

		// 単語テストボタンの設定
		wordTestButton = new JButton("単語クイズ");
		wordTestButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 20));
		wordTestButton.setBounds(166, 265, 427, 42);
		wordTestButton.setActionCommand("英単語のクイズ画面");
		wordTestButton.addActionListener(this);
		this.add(wordTestButton);

		// 単語登録ボタンの設定
		wordRegisterButton = new JButton("単語の登録");
		wordRegisterButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 20));
		wordRegisterButton.setBounds(166, 362, 427, 47);
		wordRegisterButton.setActionCommand("英単語の登録画面");
		wordRegisterButton.addActionListener(this);
		this.add(wordRegisterButton);

		// 「閉じる」ボタンの設定
		closeButton = new JButton("閉じる");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		closeButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 20));
		closeButton.setBounds(260, 463, 247, 47);
		add(closeButton);
	}

	public void actionPerformed(ActionEvent e) {
		// 押されたボタンに応じて画面を遷移する
		String cmd = e.getActionCommand();
		if(cmd == null) {
			return;
		}
		mainFrame.layout.show(mainFrame.contentPane, cmd);
		if(cmd == "英単語の管理画面") {
			try {
				DialogToSetDeck dialog = new DialogToSetDeck(mainFrame);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(cmd == "英単語のクイズ画面") {
			try {
				DialogToSetQuiz dialog = new DialogToSetQuiz(mainFrame);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(cmd == "英単語の登録画面") {
			mainFrame.wordRegisterPanel.wordTitleField.requestFocus();
		}

	}
}
