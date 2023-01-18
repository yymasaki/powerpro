package com.example.controller.skill;

import java.io.OutputStream;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.AppSpecsheet;
import com.example.domain.LoginUser;
import com.example.form.DownloadSpecsheetForm;
import com.example.form.ProcessSpecsheetForm;
import com.example.service.spec.CreateSpecsheetXlsxService;
import com.example.service.spec.GetSpecsheetForDownloadService;

@Controller
@RequestMapping("/skill")
public class DownloadSpecsheetXlsxController {
	
	@Autowired
	private GetSpecsheetForDownloadService getSpecsheetForDownloadService;
	
	@Autowired
	private CreateSpecsheetXlsxService createSpecsheetXlsxService;
	
	@Autowired
	private ShowSpecsheetController showSpecsheetController;
	
	@ModelAttribute
	public DownloadSpecsheetForm setUpDownloadSpecsheetForm() {
		return new DownloadSpecsheetForm();
	}
	
	@ModelAttribute
	public ProcessSpecsheetForm setUpProcessSpecsheetForm() {
		return new ProcessSpecsheetForm();
	}
	
	/**
	 * エクセルファイルをダウンロードするメソッド.
	 * 
	 * @param form スペックシートダウンロード用フォーム
	 * @param model リクエストスコープ
	 * @param loginUser ログインユーザー
	 * @param response HTTPレスポンス
	 * @return エラーの場合はスペックシート表示画面、エラー出ない場合はnull
	 */
	@PostMapping("/download/xlsx")
	public String downloadSpecsheetXlsx(DownloadSpecsheetForm form, Model model, 
			@AuthenticationPrincipal LoginUser loginUser, HttpServletResponse response) {

		Integer userId = form.getUserId();
		if(form.getDevExperienceIdList().size() > 10) {
			throw new IndexOutOfBoundsException();
		}
		AppSpecsheet specsheet = getSpecsheetForDownloadService.getSpecsheetForDownload(
				form.getSpecsheetId(), userId, form.getDevExperienceIdList(), form.getVersion());
		if(Objects.isNull(specsheet)) {
			String message = "ダウンロード失敗";
			model.addAttribute("message", message);
			return showSpecsheetController.showSpecsheet(userId, model, loginUser);
		}
		

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + specsheet.getStaffIdForSpec() + ".xlsx\"");
		response.setCharacterEncoding("UTF-8");

		try (Workbook workbook = createSpecsheetXlsxService.createXlsx(specsheet);
				OutputStream out = response.getOutputStream();) {
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
			return "error/X00(500)";
		}

		return null;
	}

}
