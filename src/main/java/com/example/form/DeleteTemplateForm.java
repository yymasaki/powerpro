package com.example.form;

import lombok.Data;

@Data
public class DeleteTemplateForm {
	/** テンプレートid */
	private Integer templateId;
	/** バージョン */
	private Integer version;
}
