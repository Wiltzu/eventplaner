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

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@PreserveOnRefresh
public class MyVaadinApplication extends UI {

    private ContentPanel contentPanel;

    /**
     * Constructor
     */
    public MyVaadinApplication() {

    }

    @Override
    protected void init(VaadinRequest request) {
        addWindow(new LoginWindow(this));
        setContent(initLayout());
        getPage().setTitle("EventPlanner");
    }

    /**
     * Initializes the main layout of the page
     * 
     * @return initialized main layout of the page
     */
    private VerticalLayout initLayout() {
        VerticalLayout v = new VerticalLayout();
        v.setStyleName(Reindeer.LAYOUT_BLUE);
        v.setSpacing(false);

        Panel topBanner = initTopBanner();
        v.addComponent(topBanner);
        v.setComponentAlignment(topBanner, Alignment.TOP_CENTER);

        Panel middlePanel = initMiddlePanel();
        v.addComponent(middlePanel);
        v.setComponentAlignment(middlePanel, Alignment.MIDDLE_CENTER);

        Panel bottomBanner = initBottomBanner();
        v.addComponent(bottomBanner);
        v.setComponentAlignment(bottomBanner, Alignment.BOTTOM_CENTER);
        return v;
    }

    /**
     * Initializes the middle banner
     * 
     * @return initialized middle banner
     */
    private Panel initMiddlePanel() {
        HorizontalLayout h = new HorizontalLayout();
        SidePanel sidePanel = new SidePanel(this);
        h.addComponent(sidePanel);
        h.setComponentAlignment(sidePanel, Alignment.MIDDLE_LEFT);

        contentPanel = new ContentPanel(this);
        h.addComponent(contentPanel);
        h.setComponentAlignment(contentPanel, Alignment.TOP_CENTER);

        Panel middlePanel = new Panel();
        middlePanel.setContent(h);

        return middlePanel;
    }

    /**
     * Initializes the top banner
     * 
     * @return initialized top banner
     */
    private Panel initTopBanner() {
        Panel topBanner = new Panel();
        topBanner.setWidth("100%");

        String basepath = VaadinService.getCurrent().getBaseDirectory()
                .getAbsolutePath();

        FileResource resource = new FileResource(new File(basepath
                + "/WEB-INF/images/topBannerPlaceholder.png"));

        Embedded image = new Embedded("", resource);

        VerticalLayout v = new VerticalLayout();
        v.addComponent(image);
        topBanner.setContent(v);

        return topBanner;
    }

    /**
     * Initializes the bottom banner
     * 
     * @return initialized bottom banner
     */
    private Panel initBottomBanner() {
        Panel bottomBanner = new Panel();
        bottomBanner.setWidth("100%");

        String basepath = VaadinService.getCurrent().getBaseDirectory()
                .getAbsolutePath();

        FileResource resource = new FileResource(new File(basepath
                + "/WEB-INF/images/bottomBannerPlaceholder.png"));

        Embedded image = new Embedded("", resource);

        VerticalLayout v = new VerticalLayout();
        v.addComponent(image);
        bottomBanner.setContent(v);

        return bottomBanner;
    }

    /**
     * Calls updateTables method from ContentPanel, updates all the tables
     */
    public void updateTables() {
        contentPanel.updateTables();
    }
}
