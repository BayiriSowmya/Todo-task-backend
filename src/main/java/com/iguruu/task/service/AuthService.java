package com.iguruu.task.service;

import org.springframework.stereotype.Service;

import com.iguruu.task.dto.AuthResponseDto;
import com.iguruu.task.dto.LoginDto;
import com.iguruu.task.dto.UserDto;
@Service
public interface AuthService {

	 String register(UserDto userDto);
	    AuthResponseDto login(LoginDto loginDto);  // 🔥 FIX: Return AuthResponseDto instead of String
	    String registerAdmin(UserDto userDto, Long adminId);
   
}
