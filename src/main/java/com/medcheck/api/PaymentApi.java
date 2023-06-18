package com.medcheck.api;

import com.medcheck.db.service.PaymentService;
import com.medcheck.dto.request.PaymentRequest;
import com.medcheck.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Payment API")
public class PaymentApi {
    private final PaymentService paymentService;

    @PostMapping("/charge")
    @PreAuthorize("isAuthenticated()")
    public SimpleResponse chargeCreditCard(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.chargeCreditCard(paymentRequest);
    }
}
