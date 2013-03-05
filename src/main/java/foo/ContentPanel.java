package foo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import foo.domain.Activity;
import foo.domain.MyEvent;
import foo.domain.User;
import foo.security.Password;

public class ContentPanel extends CustomComponent {

    private JPAContainer<User> users;
    private JPAContainer<MyEvent> events;
    private JPAContainer<Activity> activitys;

    private Table eventTable, userTable;
    private Button btnEdit;

    private final UI parentUI;
    private Window eventWindow;

    public ContentPanel(UI parent) {
        this.parentUI = parent;
        users = JPAContainerFactory.make(User.class, "database");
        events = JPAContainerFactory.make(MyEvent.class, "database");
        activitys = JPAContainerFactory.make(Activity.class, "database");
        addData();

        // Build the eventWindow here for later use @line 52-68

        HorizontalLayout eventLayout = new HorizontalLayout();
        eventLayout.setSpacing(true);

        TabSheet tabsheet = new TabSheet();
        tabsheet.setSizeFull();

        btnEdit = new Button("View Details");
        btnEdit.addClickListener(new ClickListener() {
            boolean isAdded = false;
            MyEvent selectedEvent;
            Object tblIndex;

            @Override
            public void buttonClick(ClickEvent event) {
                // handle nulls here later
                try {
                    tblIndex = eventTable.getValue();
                    selectedEvent = events.getItem(tblIndex).getEntity();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // only one event window up at a time
                if (isAdded) {
                    parentUI.removeWindow(eventWindow);
                    isAdded = false;
                }

                // need to have event selected from the event table
                // add the window
                if (selectedEvent != null) {
                    eventWindow = new EventWindow(selectedEvent);
                    eventWindow.center();
                    parentUI.addWindow(eventWindow);
                    isAdded = true;
                }
            }
        });

        userTable = new Table("Users", users);
        userTable.setSelectable(true);
        userTable.setVisibleColumns(new String[] { "id", "name", "password" });
        eventTable = new Table("All Events", events);
        eventTable.setVisibleColumns(new String[] { "name", "creator" });
        eventTable.setSelectable(true);

        // kokeilu
        eventLayout.addComponent(eventTable);
        eventLayout.addComponent(btnEdit);

        tabsheet.addTab(eventLayout, "All Events");
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
