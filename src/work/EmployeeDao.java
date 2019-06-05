package work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDao {

	// 共通コネクション
	private Connection con = null;

	// コンストラクタ
	EmployeeDao(Connection con) {
		this.con = con;
	}

	/**
	 * 従業員の一覧データを取得する
	 *
	 * @return 従業員一覧
	 */
	public ArrayList<EmployeeDto> select() throws SQLException {

		ArrayList<EmployeeDto> list = new ArrayList<>();

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
		builder.append("INNER JOIN division_training div       ");
		builder.append("  ON emp.division_cd = div.division_cd ");
		builder.append("ORDER BY                               ");
		builder.append("  employee_id                          ");

		// ステートメントにSQL文を格納
		PreparedStatement ps = con.prepareStatement(builder.toString());

		// SQLを実行して取得結果をリザルトセットに格納
		ResultSet rs = ps.executeQuery();

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

		// メモリの開放
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}

		// 呼び出し元に取得結果を返却
		return list;
	}
}
