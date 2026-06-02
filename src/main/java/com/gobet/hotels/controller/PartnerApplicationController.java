package com.gobet.hotels.controller;

import com.gobet.hotels.dto.partner.CreatePartnerApplicationRequest;
import com.gobet.hotels.service.PartnerApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Partner Application")
@RestController
@RequestMapping("/api/partner-applications")
@RequiredArgsConstructor
public class PartnerApplicationController {

    private final PartnerApplicationService service;

    @PostMapping
    public String apply(
            @Valid @RequestBody CreatePartnerApplicationRequest request
    ) {
        service.createApplication(request);
        return "Application submitted successfully";
    }
}