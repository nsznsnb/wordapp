package wordapp.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultFormatter;

import wordapp.csv.CSVReader;
import wordapp.dao.CardsDAO;
import wordapp.entity.Card;
import wordapp.entity.Deck;

public class WordRegisterPanel extends JPanel implements ActionListener {

	MainFrame mainFrame;
	JLabel wordTitleLabel, wordMeaningLabel, torokubiLabel;
	JTextField wordTitleField, wordMeaningField;
	JButton addButton, csvImportButton, clearButton, deleteButton, registerButton, backButton;
	JScrollPane scrollPane;
	JList<Card> wordsList;
	DefaultListModel<Card> wordsListModel = new DefaultListModel<>();
	JSpinner spinner;
	/**
	 * Create the panel.
	 */
	public WordRegisterPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();

	}

	private void initComponents() {
		this.setLayout(null);
		this.setBounds(0, 0, 800, 600);

		// 単語ラベルの設定
		wordTitleLabel = new JLabel("単語名");
		wordTitleLabel.setFont(new Font("MS UI Gothic", Font.BOLD | Font.ITALIC, 12));
		wordTitleLabel.setBounds(34, 51, 77, 24);
		this.add(wordTitleLabel);

		// 日本語訳ラベルの設定
		wordMeaningLabel = new JLabel("日本語訳");
		wordMeaningLabel.setFont(new Font("MS UI Gothic", Font.BOLD | Font.ITALIC, 12));
		wordMeaningLabel.setBounds(31, 142, 57, 13);
		add(wordMeaningLabel);

		// 単語入力フィールドの設定
		wordTitleField = new JTextField();
		wordTitleField.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		wordTitleField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				wordTitleFieldKeyPressed(e);
			}
		});
		wordTitleField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordTitleFieldActionPerformed(e);
			}
		});
		wordTitleField.setBounds(34, 85, 247, 36);
		this.add(wordTitleField);
		wordTitleField.setColumns(10);

		// 日本語訳入力フィールドの設定
		wordMeaningField = new JTextField();
		wordMeaningField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordMeaningFieldActionPerformed(e);
			}
		});
		wordMeaningField.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		wordMeaningField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				wordMeaningFieldKeyPressed(e);
			}
		});
		wordMeaningField.setBounds(34, 166, 247, 36);
		this.add(wordMeaningField);
		wordMeaningField.setColumns(10);

		// 登録日ラベルの設定
		torokubiLabel = new JLabel("登録日");
		torokubiLabel.setFont(new Font("MS UI Gothic", Font.BOLD | Font.ITALIC, 12));
		torokubiLabel.setBounds(34, 223, 57, 13);
		add(torokubiLabel);

		// 以下、登録日スピナーの設定
		Calendar calendar = Calendar.getInstance();
		Date initDate = calendar.getTime();
		calendar.set(2020, 0, 0);
		Date startDate = calendar.getTime();

		SpinnerModel dateModel = new SpinnerDateModel(initDate, startDate, null, Calendar.DAY_OF_MONTH);
		spinner = new JSpinner(dateModel);
		String dateFormat = "yyyy-MM-dd";
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, dateFormat);
		DefaultFormatter formatter = (DefaultFormatter) editor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false);
		spinner.setEditor(editor);
		editor.getTextField().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				EventQueue.invokeLater(() -> {
					// はじめから年月日のうち日を選択状態にする
					int i = dateFormat.lastIndexOf("dd");
					editor.getTextField().select(i, i + 2);
				});
			}
		});
		spinner.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		editor.getTextField().setEditable(false);
		spinner.setBounds(66, 246, 176, 36);
		add(spinner);

		// 以下、単語リスト表示部分の設定
		scrollPane = new JScrollPane();
		scrollPane.setBounds(331, 43, 411, 398);
		this.add(scrollPane);

		wordsList = new JList<>();
		wordsList.setFont(new Font("MS UI Gothic", Font.PLAIN, 20));
		scrollPane.setViewportView(wordsList);

		// csvファイルインポートボタンの設定
		csvImportButton = new JButton("CSVファイルからのインポート");
		csvImportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				csvImportButtonactionPerformed(e);
			}
		});
		csvImportButton.setBounds(49, 385, 218, 36);
		csvImportButton.setActionCommand("CSVファイルからのインポート");
		this.add(csvImportButton);

		// 追加ラベルの設定
		addButton = new JButton("追加");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addButtonActionPerformed(e);
			}
		});
		addButton.setBounds(66, 320, 176, 29);
		addButton.setActionCommand(null);
		this.add(addButton);

		// 編集ボタンの設定
		clearButton = new JButton("クリア");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearButtonActionPerformed(e);
			}
		});
		clearButton.setBounds(477, 461, 119, 21);
		add(clearButton);

		// 削除ボタンの設定
		deleteButton = new JButton("削除");
		deleteButton.setToolTipText("複数選択:Ctrl + click or Shift+click");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButtonActionPerformed(e);
			}
		});
		deleteButton.setBounds(331, 461, 109, 21);
		deleteButton.setActionCommand(null);
		this.add(deleteButton);

		// 登録ボタンの設定
		registerButton = new JButton("登録");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerButtonActionPerformed(e);
			}
		});
		registerButton.setBounds(633, 461, 109, 21);
		this.add(registerButton);

		// 戻るボタンの設定
		backButton = new JButton("戻る");
		backButton.addActionListener(this);
		backButton.setBounds(623, 505, 119, 29);

		backButton.setActionCommand("ホーム画面");
		add(backButton);


	}

	// 単語名入力フィールドでEnterキーを押したときの処理
	private void wordTitleFieldActionPerformed(ActionEvent e) {
		wordMeaningField.requestFocus();
	}
	// 単語名入力フィールドでPgDnキーを押したときの処理
	private void wordTitleFieldKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			wordMeaningField.requestFocus();
		}
	}
	// 日本語訳入力フィールドでEnterキーを押したときの処理
	private void wordMeaningFieldActionPerformed(ActionEvent e) {
		// フィールドに入っている単語カードを単語リストに載せる
		String title = wordTitleField.getText();
		String meaning = wordMeaningField.getText();
		if(title.equals("")) {
			JOptionPane.showMessageDialog(null, "単語名が入力されていません");
		} else if (meaning.equals("")) {
			JOptionPane.showMessageDialog(null, "日本語訳が入力されていません");
		} else {
			Card card = new Card(title, meaning);
			wordsListModel.addElement(card);
			wordsList.setModel(wordsListModel);
			wordTitleField.setText("");
			wordMeaningField.setText("");
		}
	}
	// 日本語訳入力フィールドでPgUpキーを押したときの処理
	private void wordMeaningFieldKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			wordTitleField.requestFocus();
		}
	}
	// 追加ボタンを押したときの処理
	private void addButtonActionPerformed(ActionEvent e) {
		// フィールドに入っている単語カードを単語リストに載せる
		String title = wordTitleField.getText();
		String meaning = wordMeaningField.getText();
		LocalDate torokubi = convertDateToLocalDate((Date)spinner.getValue());
		if(title.equals("")) {
			JOptionPane.showMessageDialog(null, "単語名が入力されていません");
		} else if (meaning.equals("")) {
			JOptionPane.showMessageDialog(null, "日本語訳が入力されていません");
		} else {
			Card card = new Card(title, meaning, torokubi);
			wordsListModel.addElement(card);
			wordsList.setModel(wordsListModel);
			wordTitleField.setText("");
			wordMeaningField.setText("");
		}
	}

	private LocalDate convertDateToLocalDate(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
	}

	// [csvファイルからのインポート]ボタンを押したときの処理
	private void csvImportButtonactionPerformed(ActionEvent e) {
		// ファイル選択画面を開く
		JFileChooser chooser = new JFileChooser(new File("."));
		// 選択できるファイルをcsv形式のファイルのみに限定する
		FileFilter filter = new FileNameExtensionFilter("CSVファイル", "csv");
		chooser.addChoosableFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		// デフォルトの選択ファイル
		//chooser.setSelectedFile(new File("wordlist.csv"));

		int selected = chooser.showOpenDialog(null);
		if(selected == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if(!file.exists()) {
				String message = file.getName() + "は" + chooser.getCurrentDirectory().getName() + "内にはありません";
				JOptionPane.showMessageDialog(null, message);
				return;
			}
			// cdvファイルから単語カードを読み取り、デッキにセットする
			Deck deck = CSVReader.csv2Deck(file.toString());
			Card card = deck.drawAndSetToBottom();
			removeBOMFromCardTitle(card);

			// 画面の単語リストに表示する
			wordsListModel.addElement(card);
			for(int i = 1; i < deck.size(); i++) {
				card = deck.drawAndSetToBottom();
				wordsListModel.addElement(card);
			}
			wordsList.setModel(wordsListModel);
		}
	}

	// クリアボタンを押したときの処理
	private void clearButtonActionPerformed(ActionEvent e) {
		wordsListModel.clear();
	}

	// 削除ボタンを押したときの処理
	private void deleteButtonActionPerformed(ActionEvent e) {
		if(wordsList.isSelectionEmpty()) {
			JOptionPane.showMessageDialog(null, "削除する単語が選択されていません");
		} else {
			// 選択された単語を画面の単語リストから削除する
			List<Card> cards = (List<Card>)wordsList.getSelectedValuesList();
			for(Card card: cards)
				wordsListModel.removeElement(card);
		}

	}

	// 登録ボタンを押したときの処理
	private void registerButtonActionPerformed(ActionEvent e) {
		if(wordsListModel.isEmpty()) {
			JOptionPane.showMessageDialog(null, "登録する単語が設定されていません");
		} else {
			// 単語リストをデータベースに登録する
			Deck deck = setDefaultListModelToDeck();
			int num = CardsDAO.registerCards(deck);
			String message = num + "件登録しました\n";
			if(num < deck.size())
				message += "すでに登録済みの単語は件数に含まれません";
			JOptionPane.showMessageDialog(null, message);
			wordsListModel.clear();
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();


		if(cmd.equals("ホーム画面")) {
			wordTitleField.setText("");
			wordMeaningField.setText("");
			this.mainFrame.layout.show(mainFrame.contentPane, cmd);
		}
	}

	// EXCELでファイルをutf-8のcsvとして保存するときに先頭に挿入されるBOMを取り除く
	private void removeBOMFromCardTitle(Card card) {
		final String BOM = "\ufeff";
		String title = card.getTitle();
		if(title.startsWith(BOM)) {
			card.setTitle(title.substring(1));
		}
	}

	// 画面の単語リストをデッキにセットする
	private Deck setDefaultListModelToDeck() {
		Deck deck = new Deck();
		for(int i = 0; i < wordsListModel.size(); i++) {
			deck.add(wordsListModel.get(i));
		}
		return deck;
	}
}
