package foo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

import foo.domain.Activity;
import foo.domain.MyEvent;
import foo.domain.User;
import foo.security.Password;

public class ContentPanel extends CustomComponent {

    private JPAContainer<User> users;
    private JPAContainer<MyEvent> events;
    private JPAContainer<Activity> activitys;

    public ContentPanel() {
        users = JPAContainerFactory.make(User.class, "database");
        events = JPAContainerFactory.make(MyEvent.class, "database");
        activitys = JPAContainerFactory.make(Activity.class, "database");
        addData();

        TabSheet tabsheet = new TabSheet();
        tabsheet.setSizeFull();
        Table userTable = new Table("Users", users);
        userTable.setSelectable(true);

        Table eventTable = new Table("All Events", events);
        eventTable.setVisibleColumns(new String[] { "name", "creator" });
        eventTable.setSelectable(true);
        tabsheet.addTab(eventTable, "All Events");

        userTable.setVisibleColumns(new String[] { "id", "name", "password" });
        tabsheet.addTab(userTable, "My Events");
        tabsheet.addTab(new Table("Events", events), "Friend's Events");
        tabsheet.addTab(new Label("Contents of the third tab"),
                "My Past events");

        setCompositionRoot(tabsheet);
        setSizeFull();
    }

    // only for testing
    private void addData() {
        User matti = new User();
        matti.setName("matti");
        try {
			matti.setPassword(Password.getSaltedHash("salainen"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        users.addEntity(matti);
        User pekka = new User();
        pekka.setName("Pekka");
        try {
			pekka.setPassword(Password.getSaltedHash("crypt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        users.addEntity(matti);
        users.addEntity(pekka);

        Activity activity = new Activity("Aktiviteetti", pekka);
        activitys.addEntity(activity);

        MyEvent event = new MyEvent("event", pekka);
        event.addPartisipant(pekka);
        event.addPartisipant(matti);
        event.addActivity(activity);
        events.addEntity(event);

        events.commit();
        users.commit();
        activitys.commit();
    }

}
