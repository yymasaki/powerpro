package com.example.form;
import javax.validation.constraints.Size;

import com.example.NotSpace;

import lombok.Data;

@Data
public class CreateInformationForm {

	/** お知らせId */
	private Integer informationId;
	
	/** 部署ID */
	private String departmentId;

	/** タイトル */
	@NotSpace(message = "タイトルを入力してください")
	private String title;

	/** 掲載終了日 */
	@Size(min=1,message = "掲載終了日を入力してください")
	private String endPostedOn;

	/** コンテンツ */
	@NotSpace(message = "コンテンツを入力してください")
	private String content;

}
