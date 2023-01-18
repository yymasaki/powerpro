package com.example.domain;
import lombok.Data;

@Data
public class InformationsDepartmentLink {

    /**お知らせと部署関係Id*/
    private Integer informationsDepartmentLinkId;

    /**お知らせId*/
    private Integer informationId;

    /**部署Id*/
    private Integer departmentId;

}