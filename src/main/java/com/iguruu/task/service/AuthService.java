package com.iguruu.task.service;

import com.iguruu.task.dto.AuthResponseDto;
import com.iguruu.task.dto.LoginDto;
import com.iguruu.task.dto.UserDto;

public interface AuthService {
    String register(UserDto userDto);
    AuthResponseDto login(LoginDto loginDto);  // 🔥 FIX: Return AuthResponseDto instead of String
    String registerAdmin(UserDto userDto, Long adminId);
}