package wordapp.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import wordapp.dao.CardsDAO;

public class ReferenceRangeSetPanel extends JPanel {
	JLabel rangeLabel;
	JRadioButton  aDayAgoRadioButton, aWeekAgoRadioButton, aMonthAgoRadioButton, allRadioButton; //myFavoriteButton;
	JComboBox<LocalDate> torokubiComboBox;

	/**
	 * Create the panel.
	 */
	public ReferenceRangeSetPanel() {
		initComponents();
	}

	private void initComponents() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// 範囲指定ラベルの設定
		rangeLabel = new JLabel("範囲指定");
		rangeLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 16));
		rangeLabel.setBounds(37, 15, 73, 27);
		this.add(rangeLabel);


		//「１日前」ボタンの設定
		aDayAgoRadioButton = new JRadioButton("1日前");
		aDayAgoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aDayAgoRadioButtonActionPerformed(e);
			}
		});
		aDayAgoRadioButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		aDayAgoRadioButton.setSelected(false);
		aDayAgoRadioButton.setBounds(208, 51, 90, 21);
		this.add(aDayAgoRadioButton);

		// 「１週間前」ボタンの設定
		aWeekAgoRadioButton = new JRadioButton("１週間前");
		aWeekAgoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aWeekAgoRadioButtonActionPerformed(e);
			}
		});
		aWeekAgoRadioButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		aWeekAgoRadioButton.setSelected(false);
		aWeekAgoRadioButton.setBounds(300, 51, 105, 21);
		this.add(aWeekAgoRadioButton);

		// 「１ヶ月前」ボタンの設定
		aMonthAgoRadioButton = new JRadioButton("１ヶ月前");
		aMonthAgoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aMonthAgoRadioButtonActionPerformed(e);
			}
		});
		aMonthAgoRadioButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		aMonthAgoRadioButton.setSelected(false);
		aMonthAgoRadioButton.setBounds(407, 51, 90, 21);
		this.add(aMonthAgoRadioButton);

		// 「全て」ボタンの設定
		allRadioButton = new JRadioButton("全て");
		allRadioButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
		allRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allRadioButtonActionPerformed(e);
			}
		});
		allRadioButton.setBounds(47, 96, 90, 21);
		this.add(allRadioButton);

		/*		myFavoriteButton = new JRadioButton("お気に入り");
				myFavoriteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						myFavoriteButtonActionPerformed(e);
					}
				});
				myFavoriteButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 16));
				myFavoriteButton.setBounds(159, 96, 105, 21);
				this.add(myFavoriteButton);*/
		// 登録日コンボボックスの設定
		torokubiComboBox = new JComboBox<>();
		torokubiComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				torokubiComboBoxActionPerformed(e);
			}
		});
		torokubiComboBox.setBounds(37, 52, 143, 21);
		// データベースに登録されている単語カードの登録日を過不足なく表示する
		List<LocalDate> torokubis = CardsDAO.findAllTorokubi();
		for(LocalDate torokubi: torokubis) {
			torokubiComboBox.addItem(torokubi);
		}
		this.add(torokubiComboBox);

	}

	//「１日前」ボタンが選択された時の処理
	private void aDayAgoRadioButtonActionPerformed(ActionEvent e) {
		if(torokubiComboBox.getSelectedItem() == null) {
			aDayAgoRadioButton.setSelected(false);
			JOptionPane.showMessageDialog(null, "登録日が選択されていません。");
		} else {
			allRadioButton.setSelected(false);
//			myFavoriteButton.setSelected(false);
		}
	}

	//「１週間前」ボタンが選択された時の処理
	private void aWeekAgoRadioButtonActionPerformed(ActionEvent e) {
		if(torokubiComboBox.getSelectedItem() == null) {
			aWeekAgoRadioButton.setSelected(false);
			JOptionPane.showMessageDialog(null, "登録日が選択されていません。");
		} else {
			allRadioButton.setSelected(false);
//			myFavoriteButton.setSelected(false);
		}
	}

	//「１ヶ月前」ボタンが選択された時の処理
	private void aMonthAgoRadioButtonActionPerformed(ActionEvent e) {
		if(torokubiComboBox.getSelectedItem() == null) {
			aMonthAgoRadioButton.setSelected(false);
			JOptionPane.showMessageDialog(null, "登録日が選択されていません。");
		} else {
			allRadioButton.setSelected(false);
//			myFavoriteButton.setSelected(false);
		}
	}

	// 「全て」ラジオボタンが選択された時の処理
	private void allRadioButtonActionPerformed(ActionEvent e) {
		// 「全て」以外のラジオボタンをクリアする
		torokubiComboBox.setSelectedItem(null);
		aDayAgoRadioButton.setSelected(false);
		aWeekAgoRadioButton.setSelected(false);
		aMonthAgoRadioButton.setSelected(false);
		allRadioButton.setSelected(true);
//		myFavoriteButton.setSelected(false);
	}

	/*	private void myFavoriteButtonActionPerformed(ActionEvent e) {
			torokubiComboBox.setSelectedItem(null);
			aDayAgoRadioButton.setSelected(false);
			aWeekAgoRadioButton.setSelected(false);
			aMonthAgoRadioButton.setSelected(false);
			allRadioButton.setSelected(false);
			myFavoriteButton.setSelected(true);
		}*/

	// コンボボックスで日付が選択された時の処理
	private void torokubiComboBoxActionPerformed(ActionEvent e) {
		// 「１日前」ボタン～「１ヶ月前」ボタンを選択状態にする
		aDayAgoRadioButton.setSelected(true);
		aWeekAgoRadioButton.setSelected(true);
		aMonthAgoRadioButton.setSelected(true);
		allRadioButton.setSelected(false);
		//myFavoriteButton.setSelected(false);
	}
}
