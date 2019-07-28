package com.hecheng.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDTO implements Serializable {

	private static final long serialVersionUID = 9062948608397679821L;

	private int startPage = 1;

	private int pageSize = 10;

}
