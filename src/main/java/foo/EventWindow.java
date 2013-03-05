package foo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

import foo.domain.MyEvent;

@SuppressWarnings("serial")
public class EventWindow extends Window {

    private JPAContainer<MyEvent> events;

    public EventWindow() {
        super("Event");
        setContent(initContents());
    }

    /**
     * Build the window contents here
     */
    private Panel initContents() {
        Panel p = new Panel();

        return p;
    }

}
