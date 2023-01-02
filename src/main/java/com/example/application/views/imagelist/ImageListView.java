package com.example.application.views.imagelist;

import com.example.application.data.entity.MeritItem;
import com.example.application.data.service.MeritItemService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.annotation.security.PermitAll;
import java.io.ByteArrayInputStream;

import static com.example.application.common.Definitions.TEXT_MERITS;
import static com.example.application.common.Definitions.TEXT_PIN_POTHA;

@PageTitle(TEXT_MERITS)
@Route(value = "image-list", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class ImageListView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;
    private final MeritItemService meritItemService;

    public ImageListView(MeritItemService meritItemService) {

        this.meritItemService = meritItemService;

        constructUI();
        createViewCards();
    }

    private void createViewCards() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<MeritItem> list = meritItemService.list(pageable);
        list.forEach(meritItem -> {
            Image image = new Image();
            image.setSrc(new StreamResource("image", () -> new ByteArrayInputStream(meritItem.getImage())));
            ImageListViewCard imageListViewCard = new ImageListViewCard(
                    meritItem.getId(),
                    meritItem.getTitle(),
                    meritItem.getEventDate().toString(),
                    meritItem.getDescription(), image);
            imageContainer.add(imageListViewCard);
        });
    }

    private void constructUI() {
        addClassNames("image-list-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        VerticalLayout container = new VerticalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2(TEXT_PIN_POTHA);
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.MEDIUM, FontSize.XXXLARGE);
        Paragraph description1 = new Paragraph("මා භික්ඛවේ පුඤ්ඤානං භායිත්ථ. සුඛස්සේතං භික්ඛවේ අධිවචනං ඉට්ඨස්ස කන්තස්ස පියස්ස මනාපස්ස යදිදං පුඤ්ඤානි.");
        description1.addClassNames(Margin.Bottom.SMALL, Margin.Top.LARGE, TextColor.HEADER);
        Paragraph description2 = new Paragraph("පින්වත් මහණෙනි, පින්වලට භය වෙන්න එපා! පින්වත් මහණෙනි, ‘පින’ කියලා කියන්නේ මේ ඉතා යහපත්, සිත්කළු, ප්\u200Dරිය මනාප වූ සැපයටම කියන නමක්.");
        description2.addClassNames(Margin.Bottom.SMALL, Margin.Top.NONE, TextColor.SECONDARY);
        Paragraph description3 = new Paragraph("-ඛුද්දක නිකාය/ඉතිවුත්තක පාළි/මා පුඤ්ඤභායි සුත්තං");
        description3.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.NONE, TextColor.TERTIARY);

        headerContainer.add(/*header,*/ description1, description2, description3);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Newest first", "Oldest first");
        sortBy.setValue("Newest first");

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(sortBy, headerContainer);
        add(container, imageContainer);

    }
}
