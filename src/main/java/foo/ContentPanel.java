package foo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.util.filter.Like;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import foo.domain.Activity;
import foo.domain.MyEvent;
import foo.domain.User;

public class ContentPanel extends CustomComponent {

    private JPAContainer<User> users;
    private JPAContainer<MyEvent> myEvents, allEvents;
    private JPAContainer<Activity> activities;

    private Table tblAllEvents, tblMyEvents, tblFriends;
    private TabSheet tabsheet;

    private final UI parentUI;
    private Window eventWindow;
    private User currentUser;

    /**
     * Constructor for Content panel
     * 
     * @param parent
     *            the UI parent
     */
    public ContentPanel(UI parent) {
        this.parentUI = parent;
        users = JPAContainerFactory.make(User.class, "database");
        myEvents = JPAContainerFactory.make(MyEvent.class, "database");
        allEvents = JPAContainerFactory.make(MyEvent.class, "database");

        activities = JPAContainerFactory.make(Activity.class, "database");

        tabsheet = new TabSheet();
        tabsheet.setSizeFull();

        HorizontalLayout myEventsLayout = initMyEventsTable();
        HorizontalLayout allEventsLayout = initAllEventsLayout();

        tblFriends = new Table("Events", myEvents);

        tabsheet.addTab(allEventsLayout, "All Events");
        tabsheet.addTab(myEventsLayout, "My Events");

        setCompositionRoot(tabsheet);
        setSizeFull();
    }

    /**
     * Initializes the allEventsLayout
     * 
     * @return initialized allEventsLayout
     */
    private HorizontalLayout initAllEventsLayout() {
        HorizontalLayout eventLayout = null;

        if (tblAllEvents == null) {
            eventLayout = new HorizontalLayout();
            eventLayout.setId("All Events");
            eventLayout.setSpacing(true);

            tblAllEvents = new Table(null, allEvents);
            tblAllEvents.setSelectable(true);
            Button btnEdit = new Button("View Details",
                    new ViewEventDetailsClickListener());

            eventLayout.addComponent(tblAllEvents);
            eventLayout.addComponent(btnEdit);
        }

        tblAllEvents.setVisibleColumns(new String[] { "name", "description",
                "creator" });

        return eventLayout;
    }

    /**
     * Initializes the myEventsTable
     * 
     * @return initialized myEventsTable
     */
    private HorizontalLayout initMyEventsTable() {
        HorizontalLayout myEventLayout = null;

        if (getCurrentUser() != null) {
            currentUser = (User) getCurrentUser();
            myEvents.addContainerFilter(new Like("partisipants.id", Integer
                    .toString(currentUser.getId())));
        }

        if (tblMyEvents == null) {
            myEventLayout = new HorizontalLayout();
            myEventLayout.setId("My Events");
            myEventLayout.setSpacing(true);

            tblMyEvents = new Table(null, myEvents);
            tblMyEvents.setSelectable(true);
            Button btnEdit = new Button("View Details",
                    new ViewEventDetailsClickListener());

            myEventLayout.addComponent(tblMyEvents);
            myEventLayout.addComponent(btnEdit);

        }
        tblMyEvents.setVisibleColumns(new String[] { "name", "description",
                "creator" });
        return myEventLayout;
    }

    /**
     * Gets the current user
     * 
     * @return the current user logged in
     */
    private Object getCurrentUser() {
        return VaadinService.getCurrentRequest().getWrappedSession()
                .getAttribute("user");
    }

    /**
     * Gets the JPAContainer holding Activities
     * 
     * @return JPAContainer holding Activities
     */
    public JPAContainer<Activity> getActivitys() {
        return activities;
    }

    /**
     * Updates all of the tables
     */
    public void updateTables() {
        myEvents.refresh();
        allEvents.refresh();
        users.refresh();
        activities.refresh();
        tblMyEvents
                .setContainerDataSource(tblMyEvents.getContainerDataSource());
        tblAllEvents.setContainerDataSource(tblAllEvents
                .getContainerDataSource());
        tblFriends.setContainerDataSource(tblFriends.getContainerDataSource());
        initMyEventsTable();
        initAllEventsLayout();
    }

    /**
     * @author Ville Ahti
     * @author Antti Laine
     * 
     *         Class that implements Button.ClickListener for listening to click
     *         events for the view event details button
     */
    private class ViewEventDetailsClickListener implements Button.ClickListener {
        boolean isAdded = false;
        MyEvent selectedEvent;
        Object tblIndex;

        @Override
        public void buttonClick(ClickEvent event) {
            Component selectedTab = tabsheet.getSelectedTab();
            try {
                if (selectedTab.getId().equals("All Events")) {
                    tblIndex = tblAllEvents.getValue();
                    selectedEvent = allEvents.getItem(tblIndex).getEntity();
                }
                // My Events
                else {
                    tblIndex = tblMyEvents.getValue();
                    selectedEvent = myEvents.getItem(tblIndex).getEntity();
                }

            } catch (NullPointerException e) {
                Notification.show("Select event to view details.");
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
                        myEvents);
                eventWindow.center();
                parentUI.addWindow(eventWindow);
                isAdded = true;
            }
        }
    }
}
