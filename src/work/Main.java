package work;

import java.util.ArrayList;

public class Main {

	/*
	 * データベースから投稿メッセージを取得して出力する
	 */
	public static void main(String[] args) {

		// Daoクラスの呼び出し
		EmployeeDao dao = new EmployeeDao();
		ArrayList<EmployeeDto> list = dao.select();

		// 拡張for文で1行ずつ取り出して出力
		for (EmployeeDto dto : list) {
			System.out.println("=====================================");
			System.out.println("id         : " + dto.getId());
			System.out.println("name       : " + dto.getName());
			System.out.println("=====================================");
			System.out.println();
		}
	}
}
