package com.gobet.hotels.controller;

import com.gobet.hotels.dto.user.UserRequest;
import com.gobet.hotels.dto.user.UserResponse;
import com.gobet.hotels.entity.User;
import com.gobet.hotels.enums.UserRole;
import com.gobet.hotels.security.SecurityUser;
import com.gobet.hotels.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "User Controller", description = "User Management APIs")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAll(Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        if (currentUser.getRole() == UserRole.SUPER_ADMIN) {
            return userService.getAllUsers();
        } else if (currentUser.getRole() == UserRole.HOTEL_ADMIN) {
            if (currentUser.getHotel() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel Admin is not assigned to a hotel");
            }
            return userService.getHotelUsers(currentUser.getHotel().getId());
        } else {
            throw new AccessDeniedException("Access denied. Admin privileges required.");
        }
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        UserResponse response = userService.getUserById(id);

        if (currentUser.getRole() != UserRole.SUPER_ADMIN) {
            if (currentUser.getRole() == UserRole.HOTEL_ADMIN) {
                if (currentUser.getHotel() == null || !currentUser.getHotel().getId().equals(response.getHotelId())) {
                    throw new AccessDeniedException("Access denied. You can only view users of your own hotel.");
                }
            } else {
                throw new AccessDeniedException("Access denied. Admin privileges required.");
            }
        }
        return response;
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest request, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        if (currentUser.getRole() != UserRole.SUPER_ADMIN) {
            if (currentUser.getRole() == UserRole.HOTEL_ADMIN) {
                if (currentUser.getHotel() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel Admin is not assigned to a hotel");
                }
                // Force user to belong to their hotel
                request.setHotelId(currentUser.getHotel().getId());
                // Prevent creating other SUPER_ADMIN or HOTEL_ADMIN accounts if not super admin
                if (request.getRole() == UserRole.SUPER_ADMIN) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create a Super Admin user");
                }
            } else {
                throw new AccessDeniedException("Access denied. Admin privileges required.");
            }
        } else {
            // For SUPER_ADMIN, role and hotelId are honored as requested
        }

        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest request, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        UserResponse existingUser = userService.getUserById(id);

        if (currentUser.getRole() != UserRole.SUPER_ADMIN) {
            if (currentUser.getRole() == UserRole.HOTEL_ADMIN) {
                if (currentUser.getHotel() == null || !currentUser.getHotel().getId().equals(existingUser.getHotelId())) {
                    throw new AccessDeniedException("Access denied. You can only update users of your own hotel.");
                }
                // Force user to belong to their hotel
                request.setHotelId(currentUser.getHotel().getId());
                // Prevent upgrading anyone to SUPER_ADMIN
                if (request.getRole() == UserRole.SUPER_ADMIN) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot set user role to Super Admin");
                }
            } else {
                throw new AccessDeniedException("Access denied. Admin privileges required.");
            }
        }

        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        UserResponse existingUser = userService.getUserById(id);

        if (currentUser.getRole() != UserRole.SUPER_ADMIN) {
            if (currentUser.getRole() == UserRole.HOTEL_ADMIN) {
                if (currentUser.getHotel() == null || !currentUser.getHotel().getId().equals(existingUser.getHotelId())) {
                    throw new AccessDeniedException("Access denied. You can only delete users of your own hotel.");
                }
            } else {
                throw new AccessDeniedException("Access denied. Admin privileges required.");
            }
        }

        userService.deleteUser(id);
    }

    private User getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof SecurityUser)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
        }
        return ((SecurityUser) authentication.getPrincipal()).getUser();
    }
}
