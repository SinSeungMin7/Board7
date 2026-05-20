package com.green.pds.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.green.pds.dto.FilesDto;
import com.green.pds.dto.PdsDto;

public interface PdsService {

	List<PdsDto> getPdsList(HashMap<String, Object> map);

	void setWrite(HashMap<String, Object> map, MultipartFile[] uploadfiles);

	void setReadCountUpdate(HashMap<String, Object> map);

	PdsDto getPds(HashMap<String, Object> map);

	List<FilesDto> getFileList(HashMap<String, Object> map);

	FilesDto getFileInfo(Long file_num);

	void setDelete(HashMap<String, Object> map);

	void setUpdate(HashMap<String, Object> map, MultipartFile[] uploadfiles);

}
