package foo;

import java.util.Set;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
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
    private JPAContainer<Activity> activities;

    private Table eventTable, myEventsTable, friendsTable;
    private Button btnEdit;

    private final UI parentUI;
    private Window eventWindow;
    private User currentUser;

    public ContentPanel(UI parent) {
        this.parentUI = parent;
        users = JPAContainerFactory.make(User.class, "database");
        events = JPAContainerFactory.make(MyEvent.class, "database");
        activities = JPAContainerFactory.make(Activity.class, "database");
        addData();

        HorizontalLayout eventLayout = new HorizontalLayout();
        eventLayout.setSpacing(true);

        TabSheet tabsheet = new TabSheet();
        tabsheet.setSizeFull();

        btnEdit = new Button("View Details");
        btnEdit.addClickListener(new ClickListener() {
            boolean isAdded = false;
            MyEvent selectedEvent;
            EntityItem<MyEvent> eventEntity;
            Object tblIndex;

            @Override
            public void buttonClick(ClickEvent event) {
                // handle nulls here later
                try {
                    tblIndex = eventTable.getValue();
                    selectedEvent = events.getItem(tblIndex).getEntity();
                    eventEntity = events.getItem(tblIndex);
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
                    eventWindow = new EventWindow(selectedEvent, activities,
                            events, eventEntity);
                    eventWindow.center();
                    parentUI.addWindow(eventWindow);
                    isAdded = true;
                }
            }
        });
        initMyEventsTable();

        eventTable = new Table("All Events", events);
        eventTable.setVisibleColumns(new String[] { "name", "creator" });
        eventTable.setSelectable(true);

        friendsTable = new Table("Events", events);

        // kokeilu
        eventLayout.addComponent(eventTable);
        eventLayout.addComponent(btnEdit);

        tabsheet.addTab(eventLayout, "All Events");
        tabsheet.addTab(myEventsTable, "My Events");
        tabsheet.addTab(friendsTable, "Friend's Events");
        tabsheet.addTab(new Label("Contents of the third tab"),
                "My Past events");

        setCompositionRoot(tabsheet);
        setSizeFull();
    }

    @SuppressWarnings("unchecked")
    private void initMyEventsTable() {
        Container ic = new IndexedContainer();
        ic.addContainerProperty("Id", Integer.class, null);
        ic.addContainerProperty("Name", String.class, null);
        ic.addContainerProperty("Creator", User.class, null);

        if (parentUI.getSession().getAttribute("user") != null) {
            currentUser = (User) parentUI.getSession().getAttribute("user");
            Set<MyEvent> eventSet = currentUser.getEvents();

            for (MyEvent event : eventSet) {
                ic.addItem(event.getId());
                ic.getContainerProperty(event.getId(), "Id").setValue(
                        event.getId());
                ic.getContainerProperty(event.getId(), "Name").setValue(
                        event.getName());
                ic.getContainerProperty(event.getId(), "Creator").setValue(
                        event.getCreator());
            }
        }

        if (myEventsTable == null) {
            myEventsTable = new Table("Users", ic);
            myEventsTable.setSelectable(true);

        }
        myEventsTable.setVisibleColumns(new String[] { "Name", "Creator" });

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

        MyEvent event = new MyEvent("event", "This is cool event", pekka);
        event.addPartisipant(pekka);
        event.addPartisipant(matti);
        Activity activity = new Activity("Aktiviteetti", pekka);
        getActivitys().addEntity(activity);
        event.addActivity(activity);
        events.addEntity(event);

        events.commit();
        users.commit();
        getActivitys().commit();
    }

    public JPAContainer<Activity> getActivitys() {
        return activities;
    }

    public void updateTables() {
        events.refresh();
        users.refresh();
        activities.refresh();
        initMyEventsTable();
        myEventsTable.setContainerDataSource(myEventsTable
                .getContainerDataSource());
        eventTable.setContainerDataSource(eventTable.getContainerDataSource());
        eventTable
                .setContainerDataSource(friendsTable.getContainerDataSource());

    }

}
