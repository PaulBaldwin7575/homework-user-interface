package com.company.homeworkloans.screen.loan;

import com.company.homeworkloans.app.LoanService;
import com.company.homeworkloans.entity.Client;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.component.data.table.ContainerGroupTableItems;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.UUID;

@UiController("LoanApproval.browse")
@UiDescriptor("loan-approval.xml")
@LookupComponent("loansTable")
public class LoanApproval extends StandardLookup<Loan> {

    @Autowired
    private CollectionLoader<Loan> loansDl1;
    @Autowired
    private Table<Loan> loansTable1;
    @Autowired
    private CollectionContainer<Loan> loansDc1;

    @Autowired
    private LoanService loanService;
    @Autowired
    private GroupTable<Loan> loansTable;
    @Autowired
    private Notifications notifications;

    @Install(to = "loansTable.age", subject = "columnGenerator")
    private Component loansTableAgeColumnGenerator(Loan loan) {
        LocalDate birthDate = loan.getClient().getBirthDate();
        LocalDate currentDate = LocalDate.now();
        String age = String.valueOf(Period.between(birthDate, currentDate).getYears());
        return new Table.PlainTextCell(age);
    }

    @Subscribe(id = "loansDc", target = Target.DATA_CONTAINER)
    public void onLoansDcItemChange(InstanceContainer.ItemChangeEvent<Loan> event) {
        if (event.getItem() != null) {
            Client currentClient = Objects.requireNonNull(event.getItem()).getClient();
            UUID currentLoan = Objects.requireNonNull(event.getItem()).getId();
            loansDl1.setParameter("client1", currentClient);
            loansDl1.setParameter("id1", currentLoan);
            loansDl1.load();
            loansTable1.setItems(new ContainerGroupTableItems<>(loansDc1));
        }
    }

    @Subscribe("approveBtn")
    public void onApproveBtnClick(Button.ClickEvent event) {
        Loan loan = loansTable.getSingleSelected();
        if (loan != null) {
            loanService.approveRequest(loan);
            getScreenData().loadAll();
        }
        notifications.create()
                .withCaption(loan == null
                        ? "Not selected"
                        : "Approved")
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }

    @Subscribe("rejectBtn")
    public void onRejectBtnClick(Button.ClickEvent event) {
        Loan loan = loansTable.getSingleSelected();
        if (loan != null) {
            loanService.rejectRequest(loan);
            getScreenData().loadAll();
        }
        notifications.create()
                .withCaption(loan == null
                        ? "Not selected"
                        : "Rejected")
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }
}