package work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.datasource.OracleDataSource;
import oracle.jdbc.replay.OracleDataSourceImpl;

public class EmployeeDao {

	// データベース接続に必要なデータ(接続文字列と呼ばれる)
	private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";

	// データベースの接続ユーザID
	private static final String USER_ID = "imuser";

	// データベースの接続パスワード
	private static final String USER_PASS = "impass";

	/**
	 * 従業員の一覧データを取得する
	 *
	 * @return 従業員一覧
	 */
	public ArrayList<EmployeeDto> select() {

		ArrayList<EmployeeDto> list = new ArrayList<>();

		// データベースへの接続管理を行うクラス
		OracleDataSource ds = null;

		// データベースとの接続状態を保持するクラス
		Connection con = null;

		// SQLの保持・実行を行うクラス
		PreparedStatement ps = null;

		// SELECT文の取得結果を受け取るクラス
		ResultSet rs = null;

		// データベース接続処理は検査例外を伴うので例外処理を必ず実装する
		try {
			// データソースをインスタンス化
			ds = new OracleDataSourceImpl();

			// 接続文字列をデータソースにセットする
			ds.setURL(JDBC_URL);

			// ユーザIDとパスワードをセットして接続実施
			con = ds.getConnection(USER_ID, USER_PASS);

			// SQL文をStringBuilderクラスを使って用意する
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT                                 ");
			builder.append("  emp.employee_id,                     ");
			builder.append("  emp.employee_name,                   ");
			builder.append("  emp.phonetic,                        ");
			builder.append("  emp.gender,                          ");
			builder.append("  emp.birthday,                        ");
			builder.append("  emp.entering_date,                   ");
			builder.append("  emp.fresher_flag,                    ");
			builder.append("  emp.division_cd,                     ");
			builder.append("  div.division_name                    ");
			builder.append("FROM                                   ");
			builder.append("  employee_training emp                ");
			builder.append("  INNER JOIN division_training div     ");
			builder.append("  ON emp.division_cd = div.division_cd ");
			builder.append("ORDER BY                               ");
			builder.append("  employee_id                          ");

			// ステートメントクラスにSQL文を格納
			ps = con.prepareStatement(builder.toString());

			// SQLを実行して取得結果をリザルトセットに格納
			rs = ps.executeQuery();

			// リザルトセットから1レコードずつデータを取り出す
			while (rs.next()) {
				// 取得結果を格納するDtoをインスタンス化
				EmployeeDto dto = new EmployeeDto();
				// Dtoに取得結果を格納
				dto.setId(rs.getString("employee_id"));
				dto.setName(rs.getString("employee_name"));
				dto.setPhonetic(rs.getString("phonetic"));
				dto.setGender(rs.getInt("gender"));
				dto.setBirthDay(rs.getDate("birthday"));
				dto.setEnteringDate(rs.getDate("entering_date"));
				dto.setFresherFlag(rs.getInt("fresher_flag"));
				dto.setDivisionCd(rs.getString("division_cd"));
				dto.setDivisionName(rs.getString("division_name"));

				// Dtoに格納された1レコード分のデータをリストに詰める
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// con, ps, rsインスタンスが使っているメモリを開放
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 呼び出し元に取得結果を返却
		return list;
	}
}
