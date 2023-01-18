package com.example.form;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.example.domain.User;

import lombok.Data;

@Data
public class PresentationForm {

	
	/** 発表タイトル */
	@NotBlank(message = "タイトルを入力してください")
	private String title;

	/** 概要 */
	@NotBlank(message = "発表の概要を入力してください")
	private String content;

	/** 追々名前も入力するようにする 一旦コメントアウト */
	private String name1;
	private String name2;
	private String name3;
	private String name4;
	private String name5;

	/**
	 * 発表ユーザーのユーザーＩＤ
	 */
	@NotNull(message = "発表者を1名以上登録してください")
	private Integer userId1;
	private Integer userId2;
	private Integer userId3;
	private Integer userId4;
	private Integer userId5;

	private String document;

	/** 発表希望月 */
	@NotBlank(message = "発表希望月を選択してください")
	private String preferredMonth;

	/** 発表希望時期 */
	@NotBlank(message = "発表希望時期を選択してください")
	private String preferredTerm;

	private Integer stage;
	private Integer version;

	/** 一時保存済データ用 */
	private Integer presentationId;

	/** 一時保存データフラグ 1であれば一時保存データ 2であれば再申請 */
	private Integer savedPresentation;

	/** 発表会資料 */
	private MultipartFile file1;
	private MultipartFile file2;
	private MultipartFile file3;

	/** 発表会資料有無フラグ */
	private String fileFlag1;
	private String fileFlag2;
	private String fileFlag3;

	/** 発表者のリスト */
	private List<User> userList;



}
