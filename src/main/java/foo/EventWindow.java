package foo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import foo.domain.MyEvent;

@SuppressWarnings("serial")
public class EventWindow extends Window {

    private JPAContainer<MyEvent> events;
    private MyEvent myEvent;

    public EventWindow(MyEvent myEvent) {
        super(myEvent.getName());
        this.myEvent = myEvent;
        setContent(initContents());
    }

    /**
     * Build the window contents here
     */
    private Panel initContents() {
        Panel p = new Panel("Event Details");
        VerticalLayout v = new VerticalLayout();

        // Add event info around here
        Label lblEventCreator = new Label("Event created by: "
                + myEvent.getCreator().getName());
        v.addComponent(lblEventCreator);

        // add userlist + activity list here

        p.setContent(v);
        return p;
    }
}
