package com.busticketpro.service;

import com.busticketpro.dto.ProfileRequest;
import com.busticketpro.entity.AppUser;
import com.busticketpro.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository userRepository;

    @Transactional(readOnly = true)
    public AppUser getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
    }

    @Transactional
    public void updateProfile(String username, ProfileRequest request) {
        AppUser user = getByUsername(username);
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        userRepository.save(user);
    }
}