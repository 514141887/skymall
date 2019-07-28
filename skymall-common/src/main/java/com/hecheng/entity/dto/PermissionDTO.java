package com.hecheng.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "权限信息")
@Data
public class PermissionDTO extends BaseDTO{

	private static final long serialVersionUID = 2838657090811353285L;

	private Integer id;
	
	@ApiModelProperty(value = "权限名称")
	private String permissionName;
	
	@ApiModelProperty(value = "菜单id")
	private Integer menuId;
    
	private Date createTime;
	
	@ApiModelProperty(value = "角色id")
	private Integer[] authorityIds;


}
