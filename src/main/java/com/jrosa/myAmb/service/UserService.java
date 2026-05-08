package com.jrosa.myAmb.service;

import com.jrosa.myAmb.dto.UserDTO;
import com.jrosa.myAmb.model.User;
import com.jrosa.myAmb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Gets all the users from the same fire dept.
    public List<UserDTO> getFireDepartmentUsersByUsername(String username) {
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)); // I think this is impossible but just to be sure

        return userRepository.findByFireDepartmentId(currentUser.getFireDepartmentId())
                .stream()
                .map(user -> new UserDTO(
                        user.getUsername(),
                        user.getName(),
                        user.getInternalId(),
                        user.getRole()))
                .toList();
    }

    public void setUserRoleByUsername(String username, String role){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found" + username));

        user.setRole(role);

        userRepository.save(user);
    }
}
