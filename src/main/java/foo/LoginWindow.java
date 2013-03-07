package foo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.util.filter.Like;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import foo.domain.User;
import foo.security.Password;

@SuppressWarnings("serial")
public class LoginWindow extends Window {

    private JPAContainer<User> users;
    private Label lblUsername;
    private Label lblPassword;
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Button btnLogin;
    private Button btnRegister;

    private Label lblPasswordRepeat;
    private PasswordField passwordRepeat;
    private Button btnSignUp;

    private VerticalLayout loginLayout;
    private VerticalLayout registerLayout;

    private UI parentUI;

    /**
     * Constructor for LoginWindow
     * 
     * @param parentUI
     *            The parent UI
     */
    public LoginWindow(UI parentUI) {
        super("Login");
        this.parentUI = parentUI;
        initDBContainer();

        initButtons();

        setHeight("200px");
        setWidth("150px");
        setContent(initLoginLayout());
        setResizable(false);
        setModal(true);
        center();
        setClosable(false);
    }

    /**
     * Initializes the loginLayout
     * 
     * @return initialized loginLayout
     */
    private VerticalLayout initLoginLayout() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);

        VerticalLayout fieldLayout = new VerticalLayout();
        fieldLayout.addComponent(lblUsername);
        fieldLayout.addComponent(txtUsername);
        fieldLayout.addComponent(lblPassword);
        fieldLayout.addComponent(txtPassword);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponent(btnLogin);
        buttonLayout.addComponent(btnRegister);

        mainLayout.addComponent(fieldLayout);
        mainLayout.addComponent(buttonLayout);

        return mainLayout;
    }

    /**
     * Initializes the buttons used in the layout
     */
    private void initButtons() {
        lblUsername = new Label("Username:");
        txtUsername = new TextField();
        txtUsername.setCursorPosition(0);
        lblPassword = new Label("Password:");
        txtPassword = new PasswordField();
        btnLogin = new Button("Login", new LoginClickListener());
        btnLogin.setClickShortcut(KeyCode.ENTER);
        btnRegister = new Button("Register");
        btnRegister.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                setCaption("Register");
                setContent(initRegisterLayout());

            }
        });
    }

    /**
     * Initializes the registerLayout
     * 
     * @return initialized registerLayout
     */
    private VerticalLayout initRegisterLayout() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);

        lblPasswordRepeat = new Label("Repeat Password");
        passwordRepeat = new PasswordField();
        btnSignUp = new Button("Sign Up", new SingUpClickListener());

        VerticalLayout fieldLayout = new VerticalLayout();
        fieldLayout.addComponent(lblUsername);
        fieldLayout.addComponent(txtUsername);
        fieldLayout.addComponent(lblPassword);
        fieldLayout.addComponent(txtPassword);
        fieldLayout.addComponent(lblPasswordRepeat);
        fieldLayout.addComponent(passwordRepeat);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponent(btnSignUp);

        mainLayout.addComponent(fieldLayout);
        mainLayout.addComponent(buttonLayout);

        return mainLayout;
    }

    /**
     * Sets user to session
     * 
     * @param user
     *            the user to be attached to the session
     */
    private void setUserToSession(User user) {
        VaadinService.getCurrentRequest().getWrappedSession()
                .setAttribute("user", user);
    }

    /**
     * Initializes the users JPAContainer
     */
    private void initDBContainer() {
        users = JPAContainerFactory.make(User.class, "database");
    }

    /**
     * @author Ville Ahti
     * @author Antti Laine
     * 
     *         Class that implements Button.ClickListener for listening to click
     *         events for login button
     */
    private class LoginClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(ClickEvent event) {
            users.addContainerFilter(new Like("name", txtUsername.getValue()));
            users.applyFilters();
            if (users.size() != 0) {
                User user = users.getItem(users.firstItemId()).getEntity();

                try {
                    if (Password.check(txtPassword.getValue(),
                            user.getPassword())) {
                        setUserToSession(user);
                        close();
                        // update tables
                        ((MyVaadinApplication) parentUI).updateTables();
                    }
                } catch (Exception e) {
                    System.err.println("Empty password!");
                }
            }
            users.removeAllContainerFilters();
        }

    }

    /**
     * @author Ville Ahti
     * @author Antti Laine
     * 
     *         Class that implements Button.ClickListener for listening to click
     *         events for signup button
     */
    private class SingUpClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(ClickEvent event) {
            String username = txtUsername.getValue();
            String password = txtPassword.getValue();
            User newUser = null;
            try {
                newUser = new User(username, Password.getSaltedHash(password));
            } catch (Exception e) {
                System.err.println("password is empty");
                ;
            }

            users.addEntity(newUser);
            users.commit();
            setContent(initLoginLayout());
        }

    }
}
