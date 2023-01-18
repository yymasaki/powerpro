package com.example.service.spec;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AppSpecsheet;
import com.example.domain.DevExperience;
import com.example.service.spec.util.SpecStringUtils;

@Service
@Transactional
public class CreateSpecsheetXlsxService {

	@Autowired
	private SpecStringUtils specStringUtils;
	
	private String path = System.getProperty("user.dir") + "/document/excel/AP_template.xlsx";
	
	/**
	 * スペックシートからワークブックを作成するメソッド.
	 * 
	 * @param specsheet スペックシート
	 * @return ワークブック
	 * @throws Exception 例外
	 */
	public Workbook createXlsx(AppSpecsheet specsheet) throws Exception {
		//既存のファイルからWorkbook作成
		Workbook workbook = WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet = workbook.getSheet("スペックシート");
		
		//各値の埋め込み
		//基本情報
		getCell(sheet, 3, getColumnIndex("G")).setCellValue(specsheet.getStaffIdForSpec());
		getCell(sheet, 3, getColumnIndex("R")).setCellValue(specsheet.getGeneration());
		getCell(sheet, 3, getColumnIndex("AA")).setCellValue(specsheet.getGenderName());
		getCell(sheet, 3, getColumnIndex("AH")).setCellValue(specsheet.getNearestStation());
		getCell(sheet, 3, getColumnIndex("AW")).setCellValue(specsheet.getStartBusinessDate());
		getCell(sheet, 4, getColumnIndex("G")).setCellValue(specsheet.getPeriod(specsheet.getEngineerPeriod()));
		getCell(sheet, 4, getColumnIndex("AA")).setCellValue(specsheet.getPeriod(specsheet.getSePeriod()));
		getCell(sheet, 5, getColumnIndex("AA")).setCellValue(specsheet.getPeriod(specsheet.getPgPeriod()));
		getCell(sheet, 4, getColumnIndex("AR")).setCellValue(specsheet.getPeriod(specsheet.getItPeriod()));
		
		//スキル要約
		List<StringBuilder> htStringBuilderList = specStringUtils.divideHadTechnicalSkillsForTag(specsheet);
		getCell(sheet, 8, getColumnIndex("K")).setCellValue(htStringBuilderList.get(0).toString().replace(",", ", "));
		getCell(sheet, 9, getColumnIndex("K")).setCellValue(htStringBuilderList.get(1).toString().replace(",", ", "));
		getCell(sheet, 10, getColumnIndex("K")).setCellValue(htStringBuilderList.get(2).toString().replace(",", ", "));
		getCell(sheet, 11, getColumnIndex("K")).setCellValue(htStringBuilderList.get(3).toString().replace(",", ", "));
		getCell(sheet, 12, getColumnIndex("K")).setCellValue(htStringBuilderList.get(4).toString().replace(",", ", "));
		getCell(sheet, 13, getColumnIndex("K")).setCellValue(htStringBuilderList.get(5).toString().replace(",", ", "));
		getCell(sheet, 14, getColumnIndex("K")).setCellValue(htStringBuilderList.get(6).toString().replace(",", "、"));
		
		//アピールポイント等
		getCell(sheet, 17, getColumnIndex("B")).setCellValue(specsheet.getAppeal());
		getCell(sheet, 19, getColumnIndex("B")).setCellValue(specsheet.getEffort());
		getCell(sheet, 21, getColumnIndex("B")).setCellValue(specsheet.getCertification());
		getCell(sheet, 23, getColumnIndex("B")).setCellValue(specsheet.getPreJob());
		
		//改ページ設定
		sheet.setRowBreak(23);
		
		//開発経験
		specsheet = specStringUtils.divideUsedTechnicalSkillsForTag(specsheet);
		List<DevExperience> devExperienceList = specsheet.getDevExperienceList();
		if(devExperienceList.size() == 0) {
			return null;
		}
		int i = 0;
		for(DevExperience de: devExperienceList) {
			//改ページ設定
			if(i != 0 && i % 2 == 0) {
				sheet.setRowBreak(14 * i + 24);
			}
			
			getCell(sheet, 14 * i + 25, getColumnIndex("G")).setCellValue(de.getStartedOn());
			getCell(sheet, 14 * i + 26, getColumnIndex("G")).setCellValue(de.getProjectPeriod());
			getCell(sheet, 14 * i + 25, getColumnIndex("T")).setCellValue(de.getProjectName());
			getCell(sheet, 14 * i + 25, getColumnIndex("AO")).setCellValue(de.getRole());
			getCell(sheet, 14 * i + 25, getColumnIndex("AZ")).setCellValue(de.getTeamMembers());
			getCell(sheet, 14 * i + 26, getColumnIndex("AZ")).setCellValue(de.getProjectMembers());
			//使用テクニカルスキル
			getCell(sheet, 14 * i + 27, getColumnIndex("K"))
				.setCellValue(de.getUsedTechnicalSkillSBList().get(0).toString().replace(",", ", "));
			getCell(sheet, 14 * i + 27, getColumnIndex("AO"))
				.setCellValue(de.getUsedTechnicalSkillSBList().get(6).toString().replace(",", "、"));
			getCell(sheet, 14 * i + 28, getColumnIndex("K"))
				.setCellValue(de.getUsedTechnicalSkillSBList().get(1).toString().replace(",", ", "));
			getCell(sheet, 14 * i + 29, getColumnIndex("K"))
				.setCellValue(de.getUsedTechnicalSkillSBList().get(2).toString().replace(",", ", "));
			getCell(sheet, 14 * i + 30, getColumnIndex("K"))
				.setCellValue(de.getUsedTechnicalSkillSBList().get(3).toString().replace(",", ", "));
			getCell(sheet, 14 * i + 31, getColumnIndex("K"))
				.setCellValue(de.getUsedTechnicalSkillSBList().get(4).toString().replace(",", ", "));
			getCell(sheet, 14 * i + 32, getColumnIndex("K"))
				.setCellValue(de.getUsedTechnicalSkillSBList().get(5).toString().replace(",", ", "));
			//プロジェクト詳細等
			getCell(sheet, 14 * i + 34, getColumnIndex("D")).setCellValue(de.getProjectDetails());
			getCell(sheet, 14 * i + 36, getColumnIndex("D")).setCellValue(de.getTasks());
			getCell(sheet, 14 * i + 38, getColumnIndex("D")).setCellValue(de.getAcquired());
			
			i++;
		}
		
		//印刷範囲を設定、不要な開発経験欄を削除
		int removeRowIndex = i * 14 + 25;
		if(i != 10) {
			workbook.setPrintArea(0, "$B$1:$BB$"+ removeRowIndex);
		}
		for(; removeRowIndex <= 164; removeRowIndex++) {
			sheet.removeRow(sheet.getRow(removeRowIndex));
		}
		
		return workbook;
	}
	
	/**
     * <p>
     * 引数で指定されたシートの、行番号、列番号で指定したセルを取得して返却する
     * <p>
     * 行番号、列番号は0から開始する
     * <p>
     * Excelテンプレートで該当のセルを操作していない場合、NullPointerExceptionになる
     * @param sheet シート
     * @param rowIndex 行番号
     * @param colIndex 列番号
     * @return セル
     */
    private Cell getCell(Sheet sheet, int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        return row.getCell(colIndex);
    }
	
	/**
	 * 指定した列を表す文字のインデックスを返すメソッド.
	 * 
	 * @param columnAlphabet 列を表す文字
	 * @return 列番号
	 */
	private int getColumnIndex(String columnAlphabet) {
		final String alphabets = "abcdefghijklmnopqrstuvwxyz";
		int columnIndex = 0;
		if(columnAlphabet.length() == 1) {
			columnIndex = alphabets.indexOf(columnAlphabet.toLowerCase());
		}else {
			columnIndex = (alphabets.indexOf(columnAlphabet.toLowerCase().charAt(0)) + 1) * 26 
					+ alphabets.indexOf(columnAlphabet.toLowerCase().charAt(1));
		}
		return columnIndex;
	}
	
}
