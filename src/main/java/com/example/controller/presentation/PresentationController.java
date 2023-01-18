package com.example.controller.presentation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.LoginUser;
import com.example.domain.Presentation;
import com.example.domain.PresentationComment;
import com.example.domain.PresentationDocument;
import com.example.domain.PresentationFavorite;
import com.example.domain.User;
import com.example.form.PresentationCommentForm;
import com.example.form.PresentationForm;
import com.example.service.presentation.PresentationService;

@Controller
@RequestMapping("/presentation")
public class PresentationController {

	/** 1ページに表示する発表数 */
	private static final int VIEW_SIZE = 3;

	@Autowired
	private PresentationService presentationService;

	@ModelAttribute
	public PresentationForm setPresentationForm() {
		return new PresentationForm();
	}

	@ModelAttribute
	public PresentationCommentForm setPresentationCommentForm() {
		return new PresentationCommentForm();
	}

	/**
	 * presentationTOP画面を表示
	 * 
	 * @param loginUser
	 * @param model
	 * @return 発表会TOP画面
	 */
	@RequestMapping("")
	public String toPresentation(@AuthenticationPrincipal LoginUser loginUser, Integer page, Model model) {

		System.out.println("================TOP画面表示=================");

		// loginUserがエンジニアかそれ以外（管理者・営業）で場合わけ
		if (loginUser.getUser().getDepartmentId() == 4 || loginUser.getUser().getDepartmentId() == 5) {
			model.addAttribute("loginStatus", 1);
		} else {
			if(Objects.isNull(page)){
				page = 1;
			}
			List<Presentation> allPresentationList = presentationService.toPresentation(loginUser);
			System.out.println("===allPresentationList=== "+allPresentationList);
			// ページング処理
			Page<Presentation> presentationList = createPaging(page, VIEW_SIZE, allPresentationList);
			System.out.println("===presentationList=== "+presentationList);
			Integer pagingNumbers = presentationList.getTotalPages();
			System.out.println("===pagingNumbers=== "+pagingNumbers);
			if(allPresentationList.size() == 0){
				model.addAttribute("nullPresentation", "nullPresentation");
			}


			model.addAttribute("presentationList", presentationList);
			model.addAttribute("pagingNumbers", pagingNumbers);
			model.addAttribute("loginStatus", 0);
			model.addAttribute("pageNumber", page);
		}        
		return "presentation/presentation";
	}

   /**
	* ページング処理.
	* 
	* @param page     ページ数
	* @param size     1ページ当の表示数
	* @param presentationList 全発表情報
	* @return 発表一覧（1ページ分）
	*/
   public Page<Presentation> createPaging(int page, int size, List<Presentation> presentationList) {
	   page--;
	   int indexOfTopData = page * size;

	   List<Presentation> subList;
	   int toIndex = Math.min(indexOfTopData + size, presentationList.size());
	   subList = presentationList.subList(indexOfTopData, toIndex);

	   Page<Presentation> presentationListOnThisPage = new PageImpl<Presentation>(subList, PageRequest.of(page, size), presentationList.size());
	   return presentationListOnThisPage;
   }


	/**
	 * 発表希望期間のセット *
	 * 
	 * @param model
	 */
	public void setTerm(Model model) {
		List<String> termList = new ArrayList<>(Arrays.asList("上旬", "中旬", "下旬"));
		model.addAttribute("termList", termList);
	}

	/**
	 * 【新規データ】 発表会応募フォームを表示
	 * 
	 * @param model
	 * @return 発表会応募フォーム
	 * 
	 */
	@RequestMapping("toApply")
	public String toApply(Model model) {
		setTerm(model);
		// , @RequestBody List<EditRequestComment> list
		// System.out.println("############"+list);
		// 新規データのためversionに1をセット
		model.addAttribute("version", 1);
		return "presentation/presentation-apply";
	}

    /**
     * 発表詳細画面を表示
     * @param loginUser ログインユーザー
     * @param presentationId 発表ID
     * @param model リクエストスコープ
     * @return 発表詳細画面
     */
    @RequestMapping("/detail")
    public String detail(@AuthenticationPrincipal LoginUser loginUser, @ModelAttribute("returnFlg") Integer returnFlg,
	 @ModelAttribute("presentationId") Integer presentationId, Model model){
		System.out.println("returnFlg : "+returnFlg);

        Presentation presentation = presentationService.showPresentationDetail(presentationId);
        model.addAttribute("presentation", presentation);
        model.addAttribute("userId", loginUser.getUser().getUserId());

		if(returnFlg == 1){
			model.addAttribute("returnFlg", 1);
		} else{
			model.addAttribute("returnFlg", 2);
		}
		
        return "presentation/presentation-detail";
	}



    /**
     * 該当発表のいいねListを取得
     * @param presentationId 発表ID
     * @return いいねList
     */
    @ResponseBody
	@RequestMapping(value = "/searchFavorite", method = RequestMethod.GET)
	public List<PresentationFavorite> searchFavorite(Integer presentationId) {
		List<PresentationFavorite> presentationFavoriteList = presentationService.showPresentationFavorite(presentationId);
		return presentationFavoriteList;
	}

    /**
     * いいねボタンを押下した時の処理
     * @param loginUser ログインユーザー
     * @param presentationId 発表ID
     * @return いいねList
     */
    @ResponseBody
	@RequestMapping(value = "/favoriteDo", method = RequestMethod.GET)
	public List<PresentationFavorite> favoriteDo(@AuthenticationPrincipal LoginUser loginUser, Integer presentationId) {
		presentationService.insertPresentationFavorite(loginUser.getUser().getUserId(), presentationId);
        List<PresentationFavorite> presentationFavoriteList = presentationService.showPresentationFavorite(presentationId);
		return presentationFavoriteList;
	}

    /**
     * いいね解除ボタンを押下した時の処理
     * @param loginUser ログインユーザー
     * @param presentationId 発表ID
     * @return いいねList
     */
    @ResponseBody
	@RequestMapping(value = "/favoriteUnDo", method = RequestMethod.GET)
	public List<PresentationFavorite> favoriteUnDo(@AuthenticationPrincipal LoginUser loginUser, Integer presentationId) {
        presentationService.deletePresentationFavorite(loginUser.getUser().getUserId(), presentationId);
        List<PresentationFavorite> presentationFavoriteList = presentationService.showPresentationFavorite(presentationId);
		return presentationFavoriteList;
	}


	/**
	 * コメントを投稿する
	 * 
	 * @param loginUser ログインユーザー
	 * @param form      コメントフォーム
	 * @param result    エラー格納オブジェクト
	 * @param model     リクエストスコープ
	 * @return 発表詳細画面表示メソッドへ
	 */
	@RequestMapping("/detail/comment/do")
	public String commentDo(@AuthenticationPrincipal LoginUser loginUser, @Validated PresentationCommentForm form,
			BindingResult result, RedirectAttributes redirectAttributes, Model model) {
				if (result.hasErrors()) {
			return detail(loginUser, form.getReturnFlg(), form.getPresentationCommentPresentationId(), model);
		}
		PresentationComment presentationComment = new PresentationComment();
		BeanUtils.copyProperties(form, presentationComment);
		presentationService.insertPresentationComment(presentationComment, loginUser);
		redirectAttributes.addFlashAttribute("returnFlg", form.getReturnFlg());
		redirectAttributes.addFlashAttribute("presentationId", form.getPresentationCommentPresentationId());
		redirectAttributes.addFlashAttribute("message", "コメントを投稿しました");

		return "redirect:/presentation/detail";
	}

	/**
	 * 自身の投稿したコメントを削除する
	 * 
	 * @param loginUser             ログインユーザー
	 * @param presentationId        発表ID
	 * @param presentationCommentId 発表コメントID
	 * @param model                 リクエストスコープ
	 * @return 発表詳細画面表示メソッドへ
	 */
	@RequestMapping("/detail/comment/delete")
	public String commentDetail(Integer presentationId, Integer presentationCommentId, Integer returnFlg, 
	RedirectAttributes redirectAttributes) {
		presentationService.deletePresentationComment(presentationCommentId);
		redirectAttributes.addFlashAttribute("returnFlg", returnFlg);
		redirectAttributes.addFlashAttribute("message", "コメントを削除しました");
		redirectAttributes.addFlashAttribute("presentationId",presentationId);
		
		return "redirect:/presentation/detail";
	}

	/**
	 * userセットのメソッド切り出し
	 * 
	 * @param form
	 * @param userList
	 * @param setForm
	 */
	public PresentationForm setUser(PresentationForm form, List<User> userList) {
		PresentationForm setForm = form;
		if (userList.size() >= 1) {
			setForm.setUserId1(userList.get(0).getUserId());
			setForm.setName1(userList.get(0).getName());
		}
		if (userList.size() >= 2) {
			setForm.setUserId2(userList.get(1).getUserId());
			setForm.setName2(userList.get(1).getName());
		}
		if (userList.size() >= 3) {
			setForm.setUserId3(userList.get(2).getUserId());
			setForm.setName3(userList.get(2).getName());
		}
		if (userList.size() >= 4) {
			setForm.setUserId4(userList.get(3).getUserId());
			setForm.setName4(userList.get(3).getName());
		}
		if (userList.size() >= 5) {
			setForm.setUserId5(userList.get(4).getUserId());
			setForm.setName5(userList.get(4).getName());
		}
		return setForm;
	}

	/**
	 * 発表会資料のセット メソッド切り出し
	 */
	public List<Path> setDocument(List<PresentationDocument> documentList) {
		List<Path> fileNameList = new ArrayList<>();

		if (documentList.size() > 0) {
			Path path = Paths.get(documentList.get(0).getDocumentPath());
			fileNameList.add(path.getFileName());
		}
		if (documentList.size() > 1) {
			Path path = Paths.get(documentList.get(1).getDocumentPath());
			fileNameList.add(path.getFileName());
		}
		if (documentList.size() > 2) {
			Path path = Paths.get(documentList.get(2).getDocumentPath());
			fileNameList.add(path.getFileName());
		}
		return fileNameList;
	}


	/**
	 * 【一時保存データ】発表会応募フォームを表示
	 * 
	 * @param model
	 * @param loginUser
	 * @param form
	 * @return 発表会応募フォーム
	 */
	@RequestMapping("editSaveData")
	public String editSaveData(Model model, @AuthenticationPrincipal LoginUser loginUser, PresentationForm form) {
		setTerm(model);

		Presentation presentation = presentationService.getSavedPresentation(loginUser);
		System.out.println("とってきた一時保存のpresentation" + presentation);

		// 一時保存レコードがなかった場合の挙動
		if (presentation.getStage() == 6) {
			model.addAttribute("message", "一時保存中のデータがありません");
			model.addAttribute("version", 1);
			return "presentation/presentation-apply";
		}
		BeanUtils.copyProperties(presentation, form);
		// ユーザをセット
		form = setUser(form, presentation.getPresenterList());

		// 発表月
		int index = presentation.getPreferredDate().indexOf("月");
		String month = presentation.getPreferredDate().substring(0, index);
		form.setPreferredMonth(month);

		// 発表資料をセット
		List<Path> list = setDocument(presentation.getPresentationDocumentList());
		if (list.size() > 0) {
			model.addAttribute("fileName1", list.get(0));
		}
		if (list.size() > 1) {
			model.addAttribute("fileName2", list.get(1));
		}
		if (list.size() > 2) {
			model.addAttribute("fileName3", list.get(2));
		}

		// 一時保存データフラグを設定
		form.setSavedPresentation(1);
		System.out.println("これからセットするform" + form);
		return "presentation/presentation-apply";
	}

	/**
	 * 一度申請したものを修正して再申請する
	 */
	@RequestMapping("reApply")
	public String reapply(Integer presentationId, Model model, PresentationForm form) {
		setTerm(model);

		// presentationIdで取得する
		Presentation presentation = presentationService.getPresentation(presentationId);
		BeanUtils.copyProperties(presentation, form);
		form = setUser(form, presentation.getPresenterList());

		// 発表月
		int index = presentation.getPreferredDate().indexOf("月");
		String month = presentation.getPreferredDate().substring(0, index);
		form.setPreferredMonth(month);

		// 再申請データフラグ
		form.setSavedPresentation(2);

		// 資料セット
		List<Path> list = setDocument(presentation.getPresentationDocumentList());
		if (list.size() > 0) {
			model.addAttribute("fileName1", list.get(0));
		}
		if (list.size() > 1) {
			model.addAttribute("fileName2", list.get(1));
		}
		if (list.size() > 2) {
			model.addAttribute("fileName3", list.get(2));
		}

		model.addAttribute("editRequestCommentList", presentation.getEditRequestCommentList());
		// 再応募フラグ
		model.addAttribute("reApply", 1);
		return "presentation/presentation-apply";
	}

	/**
	 * 発表会応募情報を一時保存 or 応募する
	 * 
	 * @param model
	 * @param form
	 * @param result
	 * @param loginUser
	 * @return 発表会応募フォーム
	 * 
	 */
	@RequestMapping("apply")
	public String apply(Model model, @Validated PresentationForm form, BindingResult result,
			@AuthenticationPrincipal LoginUser loginUser) {
		System.out.println(form);

		// 何も入力せずに一時保存を押した場合の挙動
		if (form.getUserId1() == null && form.getUserId2() == null && form.getUserId3() == null
				&& form.getUserId4() == null && form.getUserId5() == null && form.getTitle().isBlank()
				&& form.getContent().isBlank() && form.getDocument() == null && form.getPreferredMonth().isBlank()
				&& form.getPreferredTerm().equals("--")) {
			model.addAttribute("message", "1項目以上入力してください");
			return "forward:/presentation/toApply";
		}

		// 一時保存を行う場合
		if (form.getStage() == 1) {
			presentationService.registerPresentationData(form, loginUser);
			model.addAttribute("message", "一時保存が完了しました。");
			return "forward:/presentation/toApply";
		}

		// 応募する場合
		if (result.hasErrors()) {

			if (form.getSavedPresentation() != null && form.getSavedPresentation() == 1) {
				return editSaveData(model, loginUser, form);
			} else if (form.getSavedPresentation() != null && form.getSavedPresentation() == 2) {
				return reapply(form.getPresentationId(), model, form);
			}
			return toApply(model);
		}
		presentationService.registerPresentationData(form, loginUser);
		model.addAttribute("message", "応募が完了しました");
		return "forward:/presentation/toApply";
	}
	
	/**
	 * 発表者検索
	 */
	@ResponseBody
	@RequestMapping(value = "/searchPresenter", method = RequestMethod.GET)
	public List<User> search(String name) {
		List<User> userList = presentationService.getUserByName(name);
		return userList;
	}
	
	/**
	 * 修正して再申請する際のドキュメント削除
	 * */
	@ResponseBody
	@RequestMapping(value = "/deleteDocument", method = RequestMethod.GET)
	public Integer deleteDocument(String fileName, Integer presentationId) {
		Integer count = presentationService.deletePresentationDocument(fileName, presentationId);
		return count;
	}


}
