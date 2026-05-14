package com.green.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.green.user.dto.UserDto;

@Mapper
public interface UserMapper {

	void insertUser(UserDto userDto);

	List<UserDto> getUserList();

	void deleteUser(UserDto userDto);

	UserDto getUser(UserDto userDto);

	void updateUser(UserDto userDto, String oldpwd);

	UserDto getIdDupCheck(UserDto userDto);

	void updateUser2(Map<String, Object> map);

	UserDto getLogin(UserDto userDto);


}






