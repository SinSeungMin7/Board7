package com.green.pds.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.pds.dto.FilesDto;
import com.green.pds.dto.PdsDto;

@Mapper
public interface PdsMapper {

	int count(HashMap<String, Object> map);

	List<PdsDto> getPdsList(HashMap<String, Object> map);

	void setWrite(HashMap<String, Object> map);

	void setFileWriter(HashMap<String, Object> map);

	void setReadConuntUpdate(HashMap<String, Object> map);

	PdsDto getPds(HashMap<String, Object> map);

	List<FilesDto> getFileList(HashMap<String, Object> map);

	FilesDto getFileInfo(long file_num);

	void deleteUploadFile(HashMap<String, Object> map);

	void setDelete(HashMap<String, Object> map);

}
