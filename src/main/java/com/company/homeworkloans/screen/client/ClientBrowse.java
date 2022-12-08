package com.company.homeworkloans.screen.client;

import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Client;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {
//    @Subscribe("requestBtn")
//    public void onRequestBtnClick(Button.ClickEvent event) {
//
//    }
}