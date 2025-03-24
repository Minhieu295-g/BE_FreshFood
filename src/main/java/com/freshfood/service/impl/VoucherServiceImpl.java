package com.freshfood.service.impl;

import com.freshfood.model.Voucher;
import com.freshfood.repository.VoucherRepository;
import com.freshfood.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    @Override
    public Voucher getVoucherById(int id) {
        return voucherRepository.findById(id).orElse(null);
    }
}
