package com.bizboar.superCoolBatchProgram.config;


import com.bizboar.superCoolBatchProgram.domain.BankLoanData;
import org.springframework.batch.item.ItemProcessor;

public class BankLoanDataProcessor implements ItemProcessor<BankLoanData, BankLoanData> {

    @Override
    public BankLoanData process(BankLoanData bankLoanData) throws Exception {
        if (bankLoanData.getBank_state().equals("RI")) {
            return bankLoanData;
        } else {
            return null;
        }

    }
}
