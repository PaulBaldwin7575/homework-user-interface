package com.company.homeworkloans.screen.requestloan;

import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
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
        if (loan.getClient() == null) {
            notifications.create()
                    .withCaption("Select a client")
                    .withType(Notifications.NotificationType.TRAY)
                    .show();
        } else if (loan.getAmount() == null || loan.getAmount().signum() != 1) {
            notifications.create()
                    .withCaption("Amount must be bigger, than 0")
                    .withType(Notifications.NotificationType.TRAY)
                    .show();
        } else {
            loan.setStatus(LoanStatus.REQUESTED);
            loan.setRequestDate(LocalDate.now());
            dataManager.save(loan);
            close(StandardOutcome.CLOSE);
        }
    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        close(StandardOutcome.CLOSE);
    }
}