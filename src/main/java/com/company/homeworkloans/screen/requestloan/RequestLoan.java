package com.company.homeworkloans.screen.requestloan;

import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UiController("RequestLoan")
@UiDescriptor("request-loan.xml")
public class RequestLoan extends Screen {

    @Autowired
    private InstanceContainer<Loan> loanDc;
    @Autowired
    private Metadata metadata;
    @Autowired
    private MetadataTools metadataTools;

    @Autowired
    private Notifications notifications;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onInit(InitEvent event) {
        Loan loan = metadata.create(Loan.class);
        loanDc.setItem(loan);
    }

    @Subscribe("saveBtn")
    public void onSaveBtnClick(Button.ClickEvent event) {
        Loan loan = loanDc.getItem();
        loan.setStatus(LoanStatus.REQUESTED);
        loan.setRequestDate(LocalDate.now());
        dataManager.save(loan);
    }


}