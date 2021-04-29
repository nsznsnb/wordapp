package wordapp.gui;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import wordapp.entity.Deck;

public class MainFrame extends JFrame {

	JPanel contentPane;
	HomePanel homePanel;
	WordReferencePanel wordReferencePanel;
	WordQuizPanel wordQuizPanel;
	WordRegisterPanel wordRegisterPanel;

	CardLayout layout;
	Deck deck = new Deck();
	JTextField textField;
	JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//System.setProperty("file.encoding",  "UTF-8");
					MainFrame frame = new MainFrame();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("英単語学習アプリ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		layout = new CardLayout(0,0);
		contentPane.setLayout(layout);

		homePanel = new HomePanel(this);
		contentPane.add(homePanel, "ホーム画面");

		wordReferencePanel = new WordReferencePanel(this);
		contentPane.add(wordReferencePanel, "英単語の管理画面");


		wordQuizPanel = new WordQuizPanel(this);
		contentPane.add(wordQuizPanel, "英単語のクイズ画面");

		wordRegisterPanel = new WordRegisterPanel(this);
		contentPane.add(wordRegisterPanel, "英単語の登録画面");

	}
}
