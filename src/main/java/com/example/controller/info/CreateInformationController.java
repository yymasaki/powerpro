package com.example.controller.info;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.InformationsDepartmentLink;
import com.example.domain.LoginUser;
import com.example.form.CreateInformationForm;
import com.example.service.info.AddInformationService;
import com.example.service.info.AddInformationsDepartmentLinkService;

@Controller
@RequestMapping("/info")
public class CreateInformationController {

	@Autowired
	private AddInformationService addInformationService;

	@Autowired
	private AddInformationsDepartmentLinkService addInformationsDepartmentLinkService;

	@ModelAttribute
	public CreateInformationForm setUpForm() {
		return new CreateInformationForm();
	}

	/**
	 * 
	 * お知らせ作成画面に遷移する
	 * 
	 * @return お知らせ作成画面
	 */
	@RequestMapping("/create")
	public String toCreateInfomation() {
		return "info/info-create";
	}

	/**
	 * 
	 * お知らせを作成する
	 * 
	 * @param form      お知らせ作成画面の入力情報
	 * @param loginUser ログインユーザー
	 * @return トップ画面
	 * @throws ParseException
	 */
	@PostMapping("/create/do")
	public String createInfomation(@Validated CreateInformationForm form, BindingResult result,
			@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) throws ParseException {
		if (result.hasErrors()) {
			return toCreateInfomation();
		}

		int informationId = addInformationService.addInformation(form, loginUser);
		// 部署IDをカンマ 区切りで受け取っているため、spilitでリストに格納
		List<String> departmentIdList = Arrays.asList(form.getDepartmentId().split(","));
		List<InformationsDepartmentLink> informationsDepartmentLinkList = new ArrayList<>();
		departmentIdList.forEach(s -> {
			InformationsDepartmentLink informationsDepartmentLink = new InformationsDepartmentLink();
			informationsDepartmentLink.setDepartmentId(Integer.parseInt(s));
			informationsDepartmentLink.setInformationId(informationId);
			informationsDepartmentLinkList.add(informationsDepartmentLink);
		});
		addInformationsDepartmentLinkService.addInformationsDepartmentLink(informationsDepartmentLinkList);
		redirectAttributes.addFlashAttribute("message", "お知らせの作成が完了しました");
		redirectAttributes.addFlashAttribute("CreateInformationForm", form);

		return "redirect:/";
	}

}
