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

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.filter.Filters;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import foo.domain.MyEvent;
import foo.domain.User;

/**
 * The Application's "main" class Test comment! --Brigesh
 */
@SuppressWarnings("serial")
@PreserveOnRefresh
public class MyVaadinApplication extends UI {


	public MyVaadinApplication() {
		
	}

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

		SidePanel sidePanel = new SidePanel();
		h.addComponent(sidePanel);
		h.setComponentAlignment(sidePanel, Alignment.MIDDLE_LEFT);

		ContentPanel content = new ContentPanel();
		h.addComponent(content);
		h.setComponentAlignment(content, Alignment.TOP_CENTER);

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
