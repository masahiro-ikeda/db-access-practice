package work;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.BiFunction;

/**
 * ファイル出力を行う専用クラス
 */
public class FileWriter {

	// 対象ファイルのパス
	private final Path path;

	/**
	 * コンストラクタ
	 *
	 * @param path パス
	 */
	public FileWriter(String path) {
		this.path = Paths.get(path);
	}

	// ファイル描画用の定数
	private static final String SEPARATOR = ",";
	private static final String YEAR = "年";
	private static final String MALE = "男性";
	private static final String FEMALE = "女性";
	private static final String FRESH = "新卒";
	private static final String MID_CAREER = "中途";
	private static final String HEADER = "# 社員名,ふりがな,性別,生年月日,入社日,勤続年数,入社区分,部署名";

	private static final int MALE_VAL = 1;
	private static final int FLAG_ON = 1;

	// 勤続年数計算用の定数
	private static final long ONE_YEAR = 1000 * 60 * 60 * 24 * 365L;
	private static final String BASE_DATE = "2019/04/01";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

	// ファイル書き込み用の文字コード
	private static final Charset CHAR_SET_UTF8 = Charset.forName("UTF-8");

	/**
	 * ファイル書き込み
	 *
	 * @return List<String> ファイルに格納されたデータ
	 */
	public void outputFile(List<EmployeeDto> list) throws IOException {

		BufferedWriter writer = Files.newBufferedWriter(this.path, CHAR_SET_UTF8);

		// 出力ファイルのヘッダを出力する
		writer.append(HEADER);
		writer.newLine();

		for (EmployeeDto employee : list) {

			// 社員名
			writer.append(employee.getName());
			writer.append(SEPARATOR);

			// ふりがな
			writer.append(employee.getPhonetic());
			writer.append(SEPARATOR);

			// 性別 (三項演算子を使っています)
			writer.append((employee.getGender() == MALE_VAL) ? MALE : FEMALE);
			writer.append(SEPARATOR);

			// 生年月日
			writer.append(DATE_FORMAT.format(employee.getBirthDay()).toString());
			writer.append(SEPARATOR);
			// 入社日
			writer.append(DATE_FORMAT.format(employee.getEnteringDate()).toString());
			writer.append(SEPARATOR);

			// 勤続年数 (ラムダ式で実装)
			BiFunction<Long, Long, Long> calculateLengthOfService = (entering, base) -> {
				return (base - entering) / ONE_YEAR;
			};
			// 勤続年数計算用の基準日付を用意
			long enteringDate = employee.getEnteringDate().getTime();
			long baseDate = 0;
			try {
				baseDate = DATE_FORMAT.parse(BASE_DATE).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			writer.append(calculateLengthOfService.apply(enteringDate, baseDate).toString());
			writer.append(YEAR);
			writer.append(SEPARATOR);

			// 入社区分 (三項演算子を使っています)
			writer.append((employee.getFresherFlag() == FLAG_ON) ? FRESH : MID_CAREER);
			writer.append(SEPARATOR);

			// 部署名
			writer.append(employee.getDivisionName());

			// 改行
			writer.newLine();
		}

		// 最後は必ずcloseする
		writer.close();
	}
}
