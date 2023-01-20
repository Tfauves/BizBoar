package com.bizboar.superCoolBatchProgram.processor;

import com.bizboar.superCoolBatchProgram.domain.LoanData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class LoanDataProcessor implements ItemProcessor<LoanData, LoanData> {


    private static final Logger logger = LoggerFactory.getLogger(LoanDataProcessor.class);

    @Override
    public LoanData process(final LoanData item) throws Exception {
        final Long id = item.getId();
        final String loannr_chkdgt = item.getLoannr_chkdgt();
        final String name = item.getName();
        final String city = item.getCity();
        final String state = item.getState();
        final String zip = item.getZip();
        final String bank = item.getBank();
        final String bank_state = item.getBank_state();
        final String naics = item.getNaics();
        final String approval_date = item.getApproval_date();
        final String approval_fy = item.getApproval_fy();
        final String term = item.getTerm();
        final String no_emp = item.getNo_emp();
        final String new_exist = item.getNew_exist();
        final String create_job = item.getCreate_job();
        final String retained_job = item.getRetained_job();
        final String franchise_code = item.getFranchise_code();
        final String urban_rural = item.getUrban_rural();
        final String rev_line_cr = item.getRev_line_cr();
        final String low_doc = item.getLow_doc();
        final String chg_off_date = item.getChg_off_date();
        final String disbursement_date = item.getDisbursement_date();
        final String disbursement_gross = item.getDisbursement_gross();
        final String balance_gross = item.getBalance_gross();
        final String mis_status = item.getMis_status();
        final String chg_off_prin_gr = item.getChg_off_prin_gr();
        final String gr_appv = item.getGr_appv();
        final String sba_appv = item.getSba_appv();


        final LoanData transformedData = new LoanData(id, loannr_chkdgt, name, city, state, zip, bank, bank_state, naics,
                approval_date, approval_fy, term, no_emp, new_exist, create_job, retained_job, franchise_code, urban_rural,
                rev_line_cr, low_doc, chg_off_date, disbursement_date, disbursement_gross, balance_gross, mis_status,
                chg_off_prin_gr, gr_appv, sba_appv);
        logger.info("Coverting (" + item + ") into (" + transformedData + ")");
        return transformedData;
    }
}
