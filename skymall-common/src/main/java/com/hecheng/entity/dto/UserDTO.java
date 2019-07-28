package com.hecheng.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(value = "用户信息")
@Data
public class UserDTO extends BaseDTO{

	private static final long serialVersionUID = -8884796415014742960L;

	private Integer id;
	
	@ApiModelProperty(value = "用户名称")
	private String username;

	@ApiModelProperty(value = "用户密码")
    private String password;
	
	@ApiModelProperty(value = "是否可用")
    private Boolean enabled;

    @ApiModelProperty(value = "姓名")
    private String realName;
    
    @ApiModelProperty(value = "用户类型", notes = "1、医生，2、项目经理")
    private Integer userType;

    private Date createTime;
    
    private Date lastPasswordResetDate;
    
    @ApiModelProperty(value = "地址")
    private String address;
    
    @ApiModelProperty(value = "邮箱")
    private String email;
    
    @ApiModelProperty(value = "名")
    private String firstName;
    
    @ApiModelProperty(value = "姓")
    private String lastName;
    
    @ApiModelProperty(value = "头像地址")
    private String header;
    
    @ApiModelProperty(value = "手机")
    private String mobile;
    
	@ApiModelProperty(value = "是否已删除：0、未删除，1、已删除")
    private Integer deleted;
    
	@ApiModelProperty(value = "角色列表")
    private List<AuthorityDTO> authorities;
    
    @ApiModelProperty(value = "角色id列表")
    private Integer[] authoritieIds;
    
    private List<String> products;

    @ApiModelProperty(value = "创建类型")
    private Integer createType;
    
    @ApiModelProperty(value = "当前用户ID")
    private Integer userId;
    
    @ApiModelProperty(value = "当前用户姓名")
    private String name;
    
    private Integer[] ids;


}
