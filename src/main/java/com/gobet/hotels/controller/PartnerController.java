package com.gobet.hotels.controller;

import com.gobet.hotels.dto.partner.PartnerApplicationRequest;
import com.gobet.hotels.entity.PartnerApplication;
import com.gobet.hotels.service.PartnerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Partner Controller", description = "Partner APIs")
@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @PostMapping("/apply")
    public PartnerApplication apply(@RequestBody PartnerApplicationRequest request) {
        return partnerService.submitApplication(request);
    }

    @GetMapping("/applications")
    public List<PartnerApplication> getAll() {
        return partnerService.getAllApplications();
    }

    @PostMapping("/approve/{id}")
    public void approve(@PathVariable Long id) {
        partnerService.approve(id);
    }

    @PostMapping("/reject/{id}")
    public void reject(@PathVariable Long id) {
        partnerService.reject(id);
    }
}