package my.vaadin;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import javafx.scene.input.KeyCode;

public class CustomerForm extends FormLayout {

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField email = new TextField("Email");
    private NativeSelect<CustomerStatus> status = new NativeSelect<>("Status");
    private DateField birthDate = new DateField("Birthday");
    private Button saveButton = new Button("Save");
    private Button deleteButton = new Button("Remove");

    private CustomerService service = CustomerService.getInstance();
    private Customer customer;
    private MyUI myUI;

    private Binder<Customer> binder = new Binder<>(Customer.class);

    public CustomerForm(MyUI myUI) {
        this.myUI = myUI;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(saveButton, deleteButton);
        addComponents(firstName, lastName, email, status, birthDate, buttons);

        status.setItems(CustomerStatus.values());
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bindInstanceFields(this);

        saveButton.addClickListener(e -> save());
        deleteButton.addClickListener(e -> delete());
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.setBean(customer);

        deleteButton.setVisible(customer.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }

    private void delete(){
        service.delete(customer);
        myUI.updateList();
        setVisible(false);
    }

    private void save(){
        service.save(customer);
        myUI.updateList();
        setVisible(false);
    }
}