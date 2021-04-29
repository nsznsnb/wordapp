package wordapp.gui;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class WordQuizPanel extends JPanel {

	MainFrame mainFrame;
	Jap2EngPanel jap2EngPanel;
	Choice4Panel choice4Panel;
	CardLayout layout;
	/**
	 * Create the panel.
	 */
	public WordQuizPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();
	}

	private void initComponents() {
		this.setBounds(100, 100, 800, 600);
		layout = new CardLayout(0, 0);
		setLayout(layout);

		// ４択問題パネルの設定
		choice4Panel = new Choice4Panel(mainFrame);
		this.add(choice4Panel,"４択問題パネル");

		// 英訳問題パネルの設定
		jap2EngPanel = new Jap2EngPanel(mainFrame);
		this.add(jap2EngPanel,"英訳問題パネル");

	}
}
