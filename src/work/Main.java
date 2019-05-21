package work;

import java.util.ArrayList;

public class Main {

	/*
	 * データベースから社員データを取得して出力する
	 */
	public static void main(String[] args) {

		// 処理終了の旨を出力
		System.out.println("[INFO]出力処理 : 開始");

		// Daoクラスの呼び出し
		EmployeeDao dao = new EmployeeDao();
		ArrayList<EmployeeDto> list = dao.select();

		// ファイル出力クラスの呼び出し
		String outputFilePath = "output/employees.csv";
		FileWriter fileWriter = new FileWriter(outputFilePath);
		fileWriter.outputFile(list);

		// 処理終了の旨を出力
		System.out.println("[INFO]出力処理 : 終了");
	}
}
