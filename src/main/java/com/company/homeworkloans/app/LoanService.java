package com.company.homeworkloans.app;

import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanService {
    @Autowired
    private DataManager dataManager;

    public void approveRequest(Loan loan){
        loan.setStatus(LoanStatus.APPROVED);
        dataManager.save(loan);
    }

    public void rejectRequest(Loan loan){
        loan.setStatus(LoanStatus.REJECTED);
        dataManager.save(loan);
    }
}