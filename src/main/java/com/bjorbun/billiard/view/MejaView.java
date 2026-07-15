package com.bjorbun.billiard.view;

import com.bjorbun.billiard.model.Meja;
import com.bjorbun.billiard.service.MejaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "meja", layout = MainLayout.class)
public class MejaView extends VerticalLayout {

    // 1. Injeksi Service untuk berinteraksi dengan Database
    private final MejaService mejaService;

    // 2. Deklarasi Komponen UI
    private Grid<Meja> grid = new Grid<>(Meja.class, false);
    private Binder<Meja> binder = new Binder<>(Meja.class); // Menghubungkan Form ke Model
    
    // Komponen Form
    private TextField noMeja = new TextField("Nomor Meja");
    private ComboBox<String> tipeMeja = new ComboBox<>("Tipe Meja");
    private ComboBox<String> status = new ComboBox<>("Status");
    private NumberField hargaPerJam = new NumberField("Harga Per Jam (Rp)");

    private Button saveButton = new Button("Simpan", VaadinIcon.CHECK.create());
    private Button clearButton = new Button("Batal / Tambah Baru");

    public MejaView(MejaService mejaService) {
        this.mejaService = mejaService;
        
        setSizeFull();
        
        H2 judul = new H2("Manajemen Data Meja");
        judul.getStyle().set("color", "#d7b257"); // Tema Emas
        
        konfigurasiGrid();
        konfigurasiForm();
        
        // 3. Menyusun Layout Form secara Horizontal
        HorizontalLayout formLayout = new HorizontalLayout(noMeja, tipeMeja, status, hargaPerJam);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, clearButton);
        
        // Memasukkan semua komponen ke dalam layar
        add(judul, formLayout, buttonLayout, grid);
        
        // Memuat data pertama kali
        perbaruiTabel();
        bersihkanForm();
    }

    private void konfigurasiGrid() {
        // Mendefinisikan kolom pada tabel
        grid.addColumn(Meja::getNoMeja).setHeader("No Meja").setSortable(true);
        grid.addColumn(Meja::getTipeMeja).setHeader("Tipe Meja").setSortable(true);
        grid.addColumn(Meja::getStatus).setHeader("Status").setSortable(true);
        grid.addColumn(Meja::getHargaPerJam).setHeader("Harga/Jam (Rp)").setSortable(true);
        
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        
        // Event Listener: Jika baris tabel diklik, datanya akan naik ke Form untuk di-edit
        grid.asSingleSelect().addValueChangeListener(event -> editMeja(event.getValue()));
    }

    private void konfigurasiForm() {
        tipeMeja.setItems("Reguler", "VIP");
        status.setItems("Tersedia", "Terisi", "Perbaikan");
        
        // Mengikat nama variabel di atas agar otomatis masuk ke Entity Meja
        binder.bindInstanceFields(this);
        
        // Styling Tombol Simpan dengan warna emas PPT
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.getStyle().set("background-color", "#d7b257");
        saveButton.getStyle().set("color", "#0a261d");
        
        saveButton.addClickListener(event -> simpanData());
        clearButton.addClickListener(event -> bersihkanForm());
    }

    private void simpanData() {
        Meja meja = binder.getBean(); // Mengambil data dari Form
        
        if (meja.getNoMeja() == null || meja.getNoMeja().isEmpty()) {
            Notification notif = Notification.show("Nomor Meja tidak boleh kosong!");
            notif.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Menyimpan ke database via Service
        mejaService.saveMeja(meja); // Pastikan nama method 'saveMeja' sesuai dengan yang ada di MejaService Anda
        
        perbaruiTabel();
        bersihkanForm();
        
        Notification notif = Notification.show("Data Meja berhasil disimpan!");
        notif.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void editMeja(Meja meja) {
        if (meja == null) {
            bersihkanForm();
        } else {
            binder.setBean(meja);
        }
    }

    private void bersihkanForm() {
        // Mengosongkan form dengan memberikan objek Meja baru
        binder.setBean(new Meja());
        grid.asSingleSelect().clear();
    }

    private void perbaruiTabel() {
        // Mengambil ulang data terbaru dari database
        // PERHATIAN: Jika nama method di MejaService Anda bukan 'getAllMeja()', ubah bagian ini
        grid.setItems(mejaService.getAllMeja()); 
    }
}