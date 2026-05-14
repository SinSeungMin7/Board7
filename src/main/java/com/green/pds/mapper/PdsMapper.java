package com.green.pds.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PdsMapper {

	int count(HashMap<String, Object> map);

}
