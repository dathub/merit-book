package com.example.application.views.imagelist;

import com.example.application.common.Definitions;
import com.example.application.views.merititementry.MeritItemEntryFormView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

public class ImageListViewCard extends ListItem {

    private Long id;
    private String title;
    private String date;
    private String description;
    private Image image;

    public ImageListViewCard(Long id, String sTitle, String sDate, String sDescription, Image sImage) {
        this.id = id;
        this.title = sTitle;
        this.date = sDate;
        this.description = sDescription;
        this.image = sImage;

        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div imageDiv = new Div();
        imageDiv.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        imageDiv.setHeight("160px");

        image.setWidth("100%");
        imageDiv.add(image);

        Span titleBar = new Span();
        titleBar.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        titleBar.setText(title);

        Span dateBar = new Span();
        dateBar.addClassNames(FontSize.MEDIUM, FontWeight.LIGHT);
        dateBar.setText(date);

        Paragraph descriptionBar = new Paragraph();
        descriptionBar.addClassName(Margin.Vertical.MEDIUM);
        descriptionBar.setText(description);

        Button editButton = new Button();
        editButton.setIcon(VaadinIcon.EDIT.create());

        editButton.addClickListener(e -> {
            UI.getCurrent().navigate(MeritItemEntryFormView.class);
        });

        RouteParameters routeParameters = new RouteParameters(Definitions.URL_PARAM_MERIT_ITEM_ID, id.toString());
        Icon vaadinIcon = new Icon(VaadinIcon.EDIT);
        RouterLink editLink = new RouterLink(MeritItemEntryFormView.class, routeParameters);
        editLink.add(vaadinIcon);

        add(imageDiv, titleBar, dateBar, descriptionBar, editLink);
    }

}
