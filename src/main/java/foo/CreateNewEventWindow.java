package foo;

import java.util.HashSet;
import java.util.Set;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import foo.domain.Activity;
import foo.domain.MyEvent;
import foo.domain.User;

public class CreateNewEventWindow extends Window {

	private JPAContainer<MyEvent> events;
	private Set<Activity> activities;
	private User creator;

	private Button btnCreateEvent, btnAddActivity;
	private TextField txtEventName;
	private TextArea txtEventDescription;
	private VerticalLayout mainLayout;
	private VerticalLayout activitiesLayout;
	
	private UI parentUI;

	public CreateNewEventWindow(UI parentUI) {
		events = JPAContainerFactory.make(MyEvent.class, "database");
		activities = new HashSet<Activity>();
		this.parentUI = parentUI;
		initLayout();

		setCaption("New Event");
		setHeight("500px");
		setWidth("300px");
		center();
		setContent(mainLayout);
	}

	private void initLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.setSpacing(true);

		VerticalLayout eventLayout = new VerticalLayout();
		txtEventName = new TextField("EventName");
		txtEventName.setCursorPosition(0);
		txtEventName.setWidth("250px");
		txtEventDescription = new TextArea("Description", "Event description");
		txtEventDescription.setWidth("250px");
		txtEventDescription.setHeight("150px");
		eventLayout.addComponent(txtEventName);
		eventLayout.addComponent(txtEventDescription);

		activitiesLayout = new VerticalLayout();
		activitiesLayout.setSpacing(true);
		btnAddActivity = new Button("Add Activity",
				new AddActivityClickListener());
		activitiesLayout.addComponent(btnAddActivity);

		VerticalLayout createBtnLayout = new VerticalLayout();
		btnCreateEvent = new Button("Create", new CreateEventClickListener());

		createBtnLayout.addComponent(btnCreateEvent);

		mainLayout.addComponent(eventLayout);
		mainLayout.addComponent(activitiesLayout);
		mainLayout.addComponent(createBtnLayout);
	}

	private User getCurrentUser() {
		return (User) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user");
	}

	private class AddActivityClickListener implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {

			HorizontalLayout activityLayout = new HorizontalLayout();
			activityLayout.setSpacing(true);
			final TextField txtActivityName = new TextField();
			final Button btnConfirm = new Button("Confirm");
			btnConfirm.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					if (creator == null) {
						creator = getCurrentUser();
					}

					String activityName = txtActivityName.getValue();
					if (activityName != "") {
						activities.add(new Activity(activityName, creator));
						txtActivityName.setEnabled(false);
						btnConfirm.setEnabled(false);
					} else {
						Notification.show("Give name to your activity!");
					}
				}
			});

			activityLayout.addComponent(txtActivityName);
			activityLayout.addComponent(btnConfirm);

			activitiesLayout.addComponent(activityLayout);
		}

	}

	private class CreateEventClickListener implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if (creator == null) {
				creator = getCurrentUser();
			}

			String eventName = txtEventName.getValue();
			String eventDescription = txtEventDescription.getValue();
			if (!eventName.equals("") && !eventDescription.equals("")) {
				MyEvent newEvent = new MyEvent(eventName, eventDescription,
						creator);
				Object id = events.addEntity(newEvent);
				events.commit();
				events.refresh();
				newEvent = events.getItem(id).getEntity();
				newEvent.addSetOfActivities(activities);
				events.addEntity(newEvent);
				events.commit();
				close(); // closes window
				((MyVaadinApplication)parentUI).updateTables();
			}
		}

	}
}
