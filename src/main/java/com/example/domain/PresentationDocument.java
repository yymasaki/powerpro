package com.example.domain;

import lombok.Data;

@Data
public class PresentationDocument {
    
    /** 発表資料ID */
    private Integer presentationDocumentId;
    /** 発表資料パス */
    private String documentPath;
    /** 発表ID */
	private Integer DocumentTablePresentationId;
    
}
