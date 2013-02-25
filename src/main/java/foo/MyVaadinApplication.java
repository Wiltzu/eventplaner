/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package foo;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * The Application's "main" class Test comment! --Brigesh
 */
@SuppressWarnings("serial")
public class MyVaadinApplication extends UI {

    @Override
    protected void init(VaadinRequest request) {
        initLayout();
    }

    private void initLayout() {
        VerticalLayout v = new VerticalLayout();
        v.setStyleName(Reindeer.LAYOUT_BLUE);
        v.setSpacing(false);
        setContent(v);

        Panel topBanner = initTopBanner();
        v.addComponent(topBanner);
        v.setComponentAlignment(topBanner, Alignment.TOP_CENTER);

        // Panel userPanel = initUserPanel();
        // v.addComponent(userPanel);
        // v.setComponentAlignment(userPanel, Alignment.TOP_CENTER);

        Panel middlePanel = initMiddlePanel();
        v.addComponent(middlePanel);
        v.setComponentAlignment(middlePanel, Alignment.MIDDLE_CENTER);

        Panel bottomBanner = initBottomBanner();
        v.addComponent(bottomBanner);
        v.setComponentAlignment(bottomBanner, Alignment.BOTTOM_CENTER);
    }

    private Panel initMiddlePanel() {
        HorizontalLayout h = new HorizontalLayout();

        Panel sidePanel = initSidePanel();
        h.addComponent(sidePanel);
        h.setComponentAlignment(sidePanel, Alignment.MIDDLE_LEFT);

        TabSheet contentTab = initContentTab();
        contentTab.setSizeFull();
        h.addComponent(contentTab);
        h.setComponentAlignment(contentTab, Alignment.TOP_CENTER);

        Panel middlePanel = new Panel();
        middlePanel.setContent(h);

        return middlePanel;
    }

    private Panel initTopBanner() {
        Panel topBanner = new Panel();
        topBanner.setWidth("100%");

        String basepath = VaadinService.getCurrent().getBaseDirectory()
                .getAbsolutePath();

        FileResource resource = new FileResource(new File(basepath
                + "/WEB-INF/images/topBannerPlaceholder.png"));

        Embedded image = new Embedded("TopBanner placeholder image", resource);

        VerticalLayout v = new VerticalLayout();
        v.addComponent(image);
        topBanner.setContent(v);

        return topBanner;
    }

    private Panel initSidePanel() {
        Panel sidePanel = new Panel();
        sidePanel.setHeight("100%");

        VerticalLayout vv = new VerticalLayout();
        vv.setStyleName(Reindeer.LAYOUT_WHITE);

        Label temporary = new Label("Side Panel Here");

        Button login = new Button("Login");
        Button register = new Button("Register");
        Button createNewEvent = new Button("Create new event!");

        vv.addComponent(temporary);
        vv.addComponent(register);
        vv.addComponent(login);
        vv.addComponent(createNewEvent);

        sidePanel.setContent(vv);

        return sidePanel;
    }

    private Panel initUserPanel() {
        Panel userPanel = new Panel();
        userPanel.setWidth("100%");

        HorizontalLayout v = new HorizontalLayout();
        v.setStyleName(Reindeer.LAYOUT_WHITE);

        Label temporary = new Label("User Panel Here");

        Button viewMyEvents = new Button("My Events");
        Button viewFriendEvents = new Button("Friend's Events");

        v.addComponent(viewMyEvents);
        v.addComponent(viewFriendEvents);
        v.addComponent(temporary);
        userPanel.setContent(v);

        return userPanel;
    }

    private TabSheet initContentTab() {
        TabSheet tabsheet = new TabSheet();
        tabsheet.setSizeFull();
        tabsheet.addTab(new Label("Contents of the first tab"), "My Events");
        tabsheet.addTab(new Label("Contents of the first tab"),
                "Friend's Events");
        tabsheet.addTab(new Label("Contents of the first tab"),
                "My Past events");

        return tabsheet;
    }

    private Panel initBottomBanner() {
        Panel bottomBanner = new Panel();
        bottomBanner.setWidth("100%");

        String basepath = VaadinService.getCurrent().getBaseDirectory()
                .getAbsolutePath();

        FileResource resource = new FileResource(new File(basepath
                + "/WEB-INF/images/topBannerPlaceholder.png"));

        Embedded image = new Embedded("BottomBanner placeholder image",
                resource);

        VerticalLayout v = new VerticalLayout();
        v.addComponent(image);
        bottomBanner.setContent(v);

        return bottomBanner;
    }

}
