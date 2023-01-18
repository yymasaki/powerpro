package com.example.form;

import lombok.Data;

@Data
public class RequestPresentationForm {

	private Integer presentationId;
	private Integer stage;
	private String adminComment;
	private String presentationDate;
}
