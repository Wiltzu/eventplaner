package foo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.filter.Filters;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

import foo.domain.MyEvent;
import foo.domain.User;

public class ContentPanel extends CustomComponent {
	
	private JPAContainer<User> users;
	private JPAContainer<MyEvent> events;
	
	public ContentPanel() {
		users = JPAContainerFactory.make(User.class, "database");
		events = JPAContainerFactory.make(MyEvent.class, "database");
		addData();
		
		TabSheet tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		Table userTable = new Table("Users", users);
		//event id always 6 (now) (event.getId() == 0)
		users.addContainerFilter(Filters.eq("events.id", 6));
		users.applyFilters();
		users.refresh();
		userTable.setVisibleColumns(new String[]{"id", "name", "password"});
		tabsheet.addTab(userTable, "My Events");
		tabsheet.addTab(new Table("Events", events), "Friend's Events");
		tabsheet.addTab(new Label("Contents of the third tab"),
				"My Past events");
		
		setCompositionRoot(tabsheet);
		setSizeFull();
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

}
