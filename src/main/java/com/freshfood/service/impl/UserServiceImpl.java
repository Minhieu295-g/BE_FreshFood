package com.freshfood.service.impl;

import com.freshfood.dto.request.UserAdminRequestDTO;
import com.freshfood.dto.request.UserRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.dto.response.UserResponseDTO;
import com.freshfood.model.Cart;
import com.freshfood.model.Role;
import com.freshfood.model.User;
import com.freshfood.repository.CartRepository;
import com.freshfood.repository.RoleRepository;
import com.freshfood.repository.UserRepository;
import com.freshfood.service.UserService;
import com.freshfood.util.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static com.freshfood.util.UserRole.fromString;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetailsService userDetails() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public int addUser(UserRequestDTO userRequestDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Role role = roleRepository.findByName(UserRole.customer).orElseThrow(() -> new RuntimeException("Role not found"));
        User user = User.builder()
                .username(userRequestDTO.getUsername())
                .password(encoder.encode(userRequestDTO.getPassword()))
                .email(userRequestDTO.getEmail())
                .numberPhone(userRequestDTO.getPhone())
                .fullName(userRequestDTO.getFullName())
                .provider("LOCAL")
                .roles(new HashSet<>())
                .build();
        user.getRoles().add(role);
        userRepository.save(user).getId();
        cartRepository.save(new Cart(user, new HashSet<>()));
        return user.getId();
    }

    @Override
    public int addUserByAdmin(UserAdminRequestDTO userAdminRequestDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .username(userAdminRequestDTO.getUsername())
                .password(encoder.encode(userAdminRequestDTO.getPassword()))
                .email(userAdminRequestDTO.getEmail())
                .numberPhone(userAdminRequestDTO.getNumberPhone())
                .fullName(userAdminRequestDTO.getFullName())
                .provider(userAdminRequestDTO.getProvider().toUpperCase())
                .providerId((userAdminRequestDTO.getProviderId() != null) ? userAdminRequestDTO.getProviderId() : null)
                .roles(new HashSet<>())
                .build();
        UserRole userRole = UserRole.fromString(userAdminRequestDTO.getRole().toUpperCase());
        Role role = roleRepository.findByName(userRole).orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);

        userRepository.save(user).getId();
        cartRepository.save(new Cart(user, new HashSet<>()));
        return user.getId();
    }

    @Override
    public void updateUserByAdmin(int id, UserAdminRequestDTO userAdminRequestDTO) {
        User user = findByUserId(id);
        if(userAdminRequestDTO.getFullName()!=null && !userAdminRequestDTO.getFullName().isEmpty()) user.setFullName(userAdminRequestDTO.getFullName());
        if(userAdminRequestDTO.getEmail()!=null && !userAdminRequestDTO.getEmail().isEmpty()) user.setFullName(userAdminRequestDTO.getFullName());
        if(userAdminRequestDTO.getNumberPhone()!=null && !userAdminRequestDTO.getNumberPhone().isEmpty()) user.setFullName(userAdminRequestDTO.getFullName());
        if(userAdminRequestDTO.getPassword()!=null && !userAdminRequestDTO.getPassword().isEmpty()) user.setFullName(userAdminRequestDTO.getFullName());
        UserRole userRole = UserRole.fromString(userAdminRequestDTO.getRole().toUpperCase());
        String roleName = user.getRoles().stream().toList().get(0).getName().toString();

        if(!roleName.equalsIgnoreCase(userAdminRequestDTO.getRole())){
            UserRole userrole = fromString(userAdminRequestDTO.getRole().toUpperCase());
            Role role = roleRepository.findByName(userrole).orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRoles(new HashSet<>());
            user.getRoles().add(role);
        }
        userRepository.save(user);

    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUserId(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public PageResponse getUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);

        List<UserResponseDTO> userResponseDTOS = users.stream().map(user -> UserResponseDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .numberPhone(user.getNumberPhone())
                .fullName(user.getFullName())
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .role((user.getRoles().size() == 0) ? null : user.getRoles().stream().toList().get(0).getName().toString().toUpperCase())
                .build()
        ).toList();
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(users.getTotalPages())
                .items(userResponseDTOS)
                .build();
    }
}
