package com.bjorbun.billiard.view;

import com.bjorbun.billiard.model.Meja;
import com.bjorbun.billiard.service.MejaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "meja", layout = MainLayout.class)
public class MejaView extends VerticalLayout {

    private final MejaService mejaService;
    private Grid<Meja> grid = new Grid<>(Meja.class, false);

    private TextField txtNoMeja = new TextField("Nomor Meja");
    private TextField txtTipe = new TextField("Tipe Meja (VIP/9-Feet)");
    private ComboBox<String> cbStatus = new ComboBox<>("Status Awal");
    private NumberField numHarga = new NumberField("Harga Per Jam");
    private Button btnSimpan = new Button("Tambah Meja");

    public MejaView(MejaService mejaService) {
        this.mejaService = mejaService;
        setSizeFull();
        add(new H2("Manajemen Data Meja Billiard"));

        configureForm();
        configureGrid();

        HorizontalLayout mainContent = new HorizontalLayout(grid, createFormLayout());
        mainContent.setSizeFull();
        mainContent.setFlexGrow(1, grid);

        add(mainContent);
        updateList();
    }

    private void configureForm() {
        cbStatus.setItems("Tersedia", "Terisi", "Penuh");
        cbStatus.setValue("Tersedia");
        
        btnSimpan.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnSimpan.addClickListener(e -> {
            Meja m = new Meja();
            m.setNoMeja(txtNoMeja.getValue());
            m.setTipeMeja(txtTipe.getValue());
            m.setStatus(cbStatus.getValue());
            m.setHargaPerJam(numHarga.getValue() != null ? numHarga.getValue() : 0.0);

            mejaService.saveMeja(m);
            Notification.show("Meja biliar berhasil ditambahkan!");
            clearForm();
            updateList();
        });
    }

    private VerticalLayout createFormLayout() {
        VerticalLayout form = new VerticalLayout(txtNoMeja, txtTipe, cbStatus, numHarga, btnSimpan);
        form.setWidth("300px");
        return form;
    }

    private void configureGrid() {
        grid.addColumn(Meja::getNoMeja).setHeader("No Meja");
        grid.addColumn(Meja::getTipeMeja).setHeader("Tipe Meja");
        grid.addColumn(Meja::getStatus).setHeader("Status");
        grid.addColumn(Meja::getHargaPerJam).setHeader("Harga / Jam");
        grid.setSizeFull();
    }

    private void clearForm() {
        txtNoMeja.clear();
        txtTipe.clear();
        numHarga.clear();
    }

    private void updateList() {
        grid.setItems(mejaService.getAllMeja());
    }
}