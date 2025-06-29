package com.freshfood.service;

import com.freshfood.dto.request.ChangePasswordRequestDTO;
import com.freshfood.dto.request.UserAdminRequestDTO;
import com.freshfood.dto.request.UserInforRequestDTO;
import com.freshfood.dto.request.UserRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.dto.response.UserResponseDTO;
import com.freshfood.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetails();
    int addUser(UserRequestDTO userRequestDTO);
    int addUserByAdmin(UserAdminRequestDTO userAdminRequestDTO);
    void updateUserByAdmin(int id, UserAdminRequestDTO userAdminRequestDTO);
    void updateUser(int id, UserInforRequestDTO userInforRequestDTO);
    void deleteUser(int id);
    User findByUserId(int id);
    UserResponseDTO getUser(int id);
    PageResponse getUsers(int pageNo, int pageSize);

    void changePassword(int id, ChangePasswordRequestDTO changePassword);
}
