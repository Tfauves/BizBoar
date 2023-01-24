package com.bizboar.superCoolBatchProgram.domain;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "LOAN_DATA" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankLoanData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loannr_chkdgt")
    private String loannr_chkdgt;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "bank")
    private String bank;

    @Column(name = "bank_state")
    private String bank_state;

    @Column(name = "naics")
    private String naics;

    @Column(name = "approval_date")
    private String approval_date;

    @Column(name = "approval_fy")
    private String approval_fy;

    @Column(name = "term")
    private String term;

    @Column(name = "no_emp")
    private String no_emp;

    @Column(name = "new_exist")
    private String new_exist;

    @Column(name = "create_job")
    private String create_job;

    @Column(name = "retained_job")
    private String retained_job;

    @Column(name = "franchise_code")
    private String franchise_code;

    @Column(name = "urban_rural")
    private String urban_rural;

    @Column(name = "rev_line_cr")
    private String rev_line_cr;

    @Column(name = "low_doc")
    private String low_doc;

    @Column(name = "chg_off_date")
    private String chg_off_date;

    @Column(name = "disbursement_date")
    private String disbursement_date;

    @Column(name = "disbursement_gross")
    private String disbursement_gross;

    @Column(name = "balance_gross")
    private String balance_gross;

    @Column(name = "mis_status")
    private String mis_status;

    @Column(name = "chg_off_prin_gr")
    private String chg_off_prin_gr;

    @Column(name = "gr_appv")
    private String gr_appv;

    @Column(name = "sba_appv")
    private String sba_appv;
}
