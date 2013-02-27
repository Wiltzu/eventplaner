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
import com.vaadin.annotations.PreserveOnRefresh;
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

import foo.domain.User;
import foo.domain.MyEvent;

/**
 * The Application's "main" class Test comment! --Brigesh
 */
@SuppressWarnings("serial")
@PreserveOnRefresh
public class MyVaadinApplication extends UI {

	private JPAContainer<User> users;
	private JPAContainer<MyEvent> events;

	public MyVaadinApplication() {
		users = JPAContainerFactory.make(User.class, "database");
		events = JPAContainerFactory.make(MyEvent.class, "database");
		addData();
	}

	@Override
	protected void init(VaadinRequest request) {
		// login();
		initLayout();
	}

	private void addData() {
		User matti = new User();
		matti.setName("Ville");
		matti.setPassword("salainen");
		users.addEntity(matti);
		User pekka = new User();
		pekka.setName("Pekka");
		pekka.setPassword("crypt");
		users.addEntity(matti);
		users.addEntity(pekka);

		MyEvent event = new MyEvent("event");
		event.addPartisipant(pekka);
		event.addPartisipant(matti);
		events.addEntity(event);

		events.commit();
		users.commit();
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

		Button login = new Button("Log out");
		login.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
		Button register = new Button("Register");
		Button createNewEvent = new Button("Create new event!");

		vv.addComponent(temporary);
		vv.addComponent(register);
		vv.addComponent(login);
		vv.addComponent(createNewEvent);

		login.setWidth("130");
		register.setWidth("130");

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
		Table userTable = new Table("Users", users);
		userTable.setVisibleColumns(new String[]{"id", "name", "password"});
		tabsheet.addTab(userTable, "My Events");
		tabsheet.addTab(new Table("Events", events), "Friend's Events");
		tabsheet.addTab(new Label("Contents of the third tab"),
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

	// Uutta shaibaa
	protected void login() {
		final VerticalLayout logInLayout = new VerticalLayout();

		logInLayout.setMargin(true);

		setContent(logInLayout);

		final TextField userName = new TextField("Username:");
		final PasswordField password = new PasswordField("Password");

		Button button = new Button("Log in");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				/**
				 * TEMPORARY SOLLUTION
				 * 
				 * TODO: Add inputcheck for username and password
				 * 
				 */
				if (userName.getValue().equals("")
						|| password.getValue().equals("")) {
					Notification.show("Type something to the textfields",
							Notification.TYPE_WARNING_MESSAGE);

				} else {
					Notification.show("Teretulemast " + userName.getValue(),
							Notification.TYPE_HUMANIZED_MESSAGE);
					logInLayout.setVisible(false);
					initLayout();
				}
			}
		});
		logInLayout.addComponent(userName);
		logInLayout.addComponent(password);
		logInLayout.addComponent(button);

	}

}
