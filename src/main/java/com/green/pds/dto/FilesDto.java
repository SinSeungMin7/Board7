package com.green.pds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilesDto {
	private  int      file_num;
	private  int      idx;
	private  String   filename;
	private  String   fileext;
	private  String   sfilename;
}
