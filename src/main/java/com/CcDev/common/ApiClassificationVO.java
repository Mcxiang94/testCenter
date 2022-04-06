package com.CcDev.common;

import java.util.List;

import com.CcDev.pojo.Api;
import com.CcDev.pojo.ApiClassification;

import lombok.Data;

@Data
public class ApiClassificationVO extends ApiClassification{

	private List<Api> apis;	//接口信息
	
}
