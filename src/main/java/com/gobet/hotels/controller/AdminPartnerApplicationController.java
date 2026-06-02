package com.gobet.hotels.controller;

import com.gobet.hotels.entity.PartnerApplication;
import com.gobet.hotels.service.IPartnerApplicationAdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin Partner Controller")
@RestController
@RequestMapping("/api/admin/partner-applications")
@RequiredArgsConstructor
@PostAuthorize("ADMIN")
public class AdminPartnerApplicationController {

    private final IPartnerApplicationAdminService service;

    @GetMapping
    public List<PartnerApplication> getAll() {
        return service.getAll();
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        service.approve(id);
        return "Application approved";
    }

    @PostMapping("/{id}/reject")
    public String reject(
            @PathVariable Long id,
            @RequestParam String reason
    ) {
        service.reject(id, reason);
        return "Application rejected";
    }
}
