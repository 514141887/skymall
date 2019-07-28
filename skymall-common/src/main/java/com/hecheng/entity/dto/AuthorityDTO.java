package com.hecheng.entity.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AuthorityDTO extends BaseDTO{


	private static final long serialVersionUID = 1673562957835198925L;

	private Integer id;

    private String authorityName;
    
    private AuthorityName authorityCode;
    
    @ApiModelProperty(value = "等级")
    private Integer authorityLevel;
    
    @ApiModelProperty(value = "当前用户ID")
    private Integer userId;
    
    @ApiModelProperty(value = "当前用户姓名")
    private String name;
    
    @ApiModelProperty(value = "权限列表")
    private List<PermissionDTO> permissions;
    


}
