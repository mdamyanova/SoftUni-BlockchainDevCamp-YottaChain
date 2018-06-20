package com.faucet.services;

import com.faucet.services.interfaces.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceImpl implements TransactionService {

    private final ModelMapper modelMapper;

    @Autowired
    public CertificateServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
