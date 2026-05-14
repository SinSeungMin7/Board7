package com.green.pds.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.pds.dto.PdsDto;

@Mapper
public interface PdsMapper {

	int count(HashMap<String, Object> map);

	List<PdsDto> getPdsList(HashMap<String, Object> map);

}
