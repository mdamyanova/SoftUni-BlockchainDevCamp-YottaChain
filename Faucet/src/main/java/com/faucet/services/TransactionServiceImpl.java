package com.faucet.services;

import com.faucet.entities.Transaction;
import com.faucet.exceptions.TransactionRequestException;
import com.faucet.models.viewModels.TransactionViewModel;
import com.faucet.repositories.TransactionDao;
import com.faucet.services.interfaces.TransactionService;
import com.faucet.utils.Config;
import com.faucet.utils.CryptoUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionViewModel sendTransaction(String recipientPrivateKey) {
        Transaction transaction = null;
        try {
            String recipientAddress = CryptoUtil.getRecipientAddressFromPrivateKey(recipientPrivateKey);

            Transaction lastRequest = TransactionDao.findTransactionByAddressTo(recipientAddress);
            Date date = new Date();

            // Do nothing is request is less the 10 minutes
            if (lastRequest != null && lastRequest.getLastRequest().getTime() - date.getTime() < Config.MAX_REQUEST_TIME) {
                throw new TransactionRequestException();
            }

            Timestamp ts = new Timestamp(date.getTime());

            transaction = CryptoUtil.signAndVerifyTransaction(
                    TransactionDao.getFaucetAddress(),
                    Config.VALUE,
                    Config.FEE,
                    ts.toString(),
                    TransactionDao.getFaucetPrivateKey());

            TransactionDao.saveTransaction(transaction);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (TransactionRequestException e) {
            e.printStackTrace();
        }

        return modelMapper.map(transaction, TransactionViewModel.class);
    }
}
