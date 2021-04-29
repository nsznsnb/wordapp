
-- データベースwordappの作成
DROP DATABASE IF EXISTS wordapp;
CREATE DATABASE wordapp;

-- データベースwordappを選択
\c wordapp;

-- 単語登録用のテーブルを作成
CREATE TABLE Cardslist (
card_title VARCHAR(100) NOT NULL,
card_meaning VARCHAR(100) NOT NULL,
torokubi DATE NOT NULL,
PRIMARY KEY (card_title, card_meaning));

