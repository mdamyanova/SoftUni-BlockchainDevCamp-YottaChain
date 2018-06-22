package com.faucet.services;

import com.faucet.entities.Transaction;
import com.faucet.repositories.TransactionDao;
import com.faucet.services.interfaces.TransactionService;
import com.faucet.utils.Config;
import com.faucet.utils.CryptoUtil;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Override
    public void sendTransaction(String recipientPrivateKey) {
        try {
            String recipientAddress = CryptoUtil.getRecipientAddressFromPrivateKey(recipientPrivateKey);

            Transaction lastRequest = TransactionDao.findTransactionByAddressTo(recipientAddress);
            Date date = new Date();

            // Do nothing is request is less the 10 minutes
            if (lastRequest == null || lastRequest.getLastRequest().getTime() - date.getTime() < Config.MAX_REQUEST_TIME) {
                return;
            }

            Timestamp ts = new Timestamp(date.getTime());

            Transaction transaction = CryptoUtil.signAndVerifyTransaction(
                    TransactionDao.getFaucetAddress(),
                    Config.VALUE,
                    Config.FEE,
                    ts.toString(),
                    TransactionDao.getFaucetPrivateKey());

            TransactionDao.saveTransaction(transaction);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
