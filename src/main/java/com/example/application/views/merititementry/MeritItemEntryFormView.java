package com.example.application.views.merititementry;

import com.example.application.common.Definitions;
import com.example.application.components.appnav.fields.UploadField;
import com.example.application.data.entity.MeritItem;
import com.example.application.data.service.MeritItemService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;

import javax.annotation.security.PermitAll;
import java.util.Optional;

import static com.example.application.common.Definitions.*;

//Don't use @PageTitle("එකතු කරන්න"), this class uses 'HasDynamicTitle'
//@Route(value = "merit-entry-form", layout = MainLayout.class, registerAtStartup = false)
@Route(value = "merit-entry-form/:meritItemId?", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class MeritItemEntryFormView extends Div implements HasDynamicTitle, HasUrlParameter<String> {

    private String pageTitle = PAGE_TITLE_ADD;
    private H3 headerPageTitle = new H3();
    private TextField title = new TextField(TEXT_TITLE);
    private TextField description = new TextField(TEXT_DESCRIPTION);
    private DatePicker eventDate = new DatePicker(TEXT_EVENT_DATE);
    private UploadField uploadField = new UploadField();
    private Button cancelBtn = new Button("Cancel");
    private Button saveBtn = new Button("Save");
    private Image imageFromDB;
    private Binder<MeritItem> binder = new Binder<>(MeritItem.class);

    private final MeritItemService meritItemService;

    public MeritItemEntryFormView(MeritItemService meritItemService) {
        this.meritItemService = meritItemService;
        addClassName("merit-entry-form-view");

        add(headerPageTitle);
        add(createFormLayout());
        add(createButtonLayout());

        // Basic name fields that are required to fill in
        binder.forField(title).asRequired().bind("title");
        binder.forField(description).asRequired().bind("description");
        binder.forField(eventDate).asRequired().bind("eventDate");
        binder.forField(uploadField).bind("image");
        clearForm();

        registerListeners();

        imageFromDB = new Image();
        imageFromDB.setAlt("image from DB");
        imageFromDB.setMaxWidth("300px");
        imageFromDB.getStyle().set("margin-right", "15px");
        imageFromDB.setVisible(true); // see updateImage()

    }

    private void registerListeners() {
        cancelBtn.addClickListener(e -> clearForm());
        saveBtn.addClickListener(e -> {
            meritItemService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new MeritItem());
    }

    private void setHeaderPageTitle(String action) {
        headerPageTitle.setText(String.format(TEXT_MERIT + " " + action));
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(title, description, eventDate, uploadField);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(saveBtn);
        buttonLayout.add(cancelBtn);
        return buttonLayout;
    }

    @Override
    public String getPageTitle() {
        return pageTitle;
    }

    private void setPageTitle(String title) {
        pageTitle = title;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        Optional<String> meritItemId = beforeEvent.getRouteParameters().get(Definitions.URL_PARAM_MERIT_ITEM_ID);
        if (meritItemId.isPresent()) {
            saveBtn.setText("Update");
            setPageTitle(PAGE_TITLE_UPDATE);
            setHeaderPageTitle(PAGE_TITLE_UPDATE);
            populateUpdateForm(Long.valueOf(meritItemId.get()));
        } else {
            saveBtn.setText("Add");
            setPageTitle(PAGE_TITLE_ADD);
            setHeaderPageTitle(PAGE_TITLE_ADD);
        }
    }

    private void populateUpdateForm(Long meritItemId) {
        Optional<MeritItem> meritItemOptional = meritItemService.get(meritItemId);
        meritItemOptional.ifPresent(meritItem -> {
            binder.setBean(meritItem);
        });
    }
}
