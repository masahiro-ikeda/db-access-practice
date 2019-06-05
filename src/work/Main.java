package work;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

	// データベース接続に必要なデータ(接続文字列と呼ばれる)
	private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
	// データベースの接続ユーザID
	private static final String USER_ID = "imuser";
	// データベースの接続パスワード
	private static final String USER_PASS = "impass";

	/*
	 * データベースから社員データを取得してcsv出力する
	 */
	public static void main(String[] args) {

		// 処理終了の旨を出力
		System.out.println("[INFO] 出力開始");

		// データベースから社員データを取得
		ArrayList<EmployeeDto> list = null;
		try (Connection con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS)) {

			EmployeeDao dao = new EmployeeDao(con);
			list = dao.select();

		} catch (SQLException e) {
			// 例外発生時の処理
			e.printStackTrace();
			System.out.println("[ERROR] データベース接続においてエラーが発生しました。");
			System.exit(1);

		}

		// ファイル出力クラスの呼び出し
		final String OUTPUT_PATH = "output/employees.csv";
		FileWriter fileWriter = new FileWriter(OUTPUT_PATH);

		try {
			fileWriter.outputFile(list);
		} catch (IOException e) {
			// 例外発生時の処理
			System.out.println("[ERROR] ファイルの書き込みに失敗しました。パス : " + OUTPUT_PATH);
			e.printStackTrace();
			System.exit(1);
		}

		// 処理終了の旨を出力
		System.out.println("[INFO] 出力終了");
	}
}
