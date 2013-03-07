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

/**
 * The Application's "main" class Test comment! --Brigesh
 */
@SuppressWarnings("serial")
@PreserveOnRefresh
public class MyVaadinApplication extends UI {
	
	private ContentPanel contentPanel;

    public MyVaadinApplication() {
    	
    }

    @Override
    protected void init(VaadinRequest request) {
        if(getSession().getAttribute("user") == null) {
        	addWindow(new LoginWindow(this));
        	setContent(initLayout());
        }
        else {
        	setContent(initLayout());
        }
    }

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
    
    public void updateTables() {
    	contentPanel.updateTables();
    }
}
