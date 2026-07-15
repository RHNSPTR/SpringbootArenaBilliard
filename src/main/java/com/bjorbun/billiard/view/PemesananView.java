package com.bjorbun.billiard.view;

import com.bjorbun.billiard.model.Meja;
import com.bjorbun.billiard.model.Member;
import com.bjorbun.billiard.model.Pemesanan;
import com.bjorbun.billiard.service.MejaService;
import com.bjorbun.billiard.service.MemberService;
import com.bjorbun.billiard.service.PemesananService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "pemesanan", layout = MainLayout.class)
public class PemesananView extends VerticalLayout {

    private final PemesananService pemesananService;
    private final MejaService mejaService;
    private final MemberService memberService;

    private Grid<Pemesanan> grid = new Grid<>(Pemesanan.class, false);
    private TextField txtKode = new TextField("Kode Booking");
    private IntegerField numDurasi = new IntegerField("Durasi (Jam)");
    private ComboBox<Meja> cbMeja = new ComboBox<>("Pilih Meja");
    private ComboBox<Member> cbMember = new ComboBox<>("Pilih Member");
    private Button btnSimpan = new Button("Check-In");

    public PemesananView(PemesananService pemesananService, MejaService mejaService, MemberService memberService) {
        this.pemesananService = pemesananService;
        this.mejaService = mejaService;
        this.memberService = memberService;

        setSizeFull();
        add(new H2("Manajemen Pemesanan Billiard"));

        configureForm();
        configureGrid();

        HorizontalLayout mainContent = new HorizontalLayout(grid, createFormLayout());
        mainContent.setSizeFull();
        mainContent.setFlexGrow(1, grid);

        add(mainContent);
        updateList();
    }

    private void configureForm() {
        cbMeja.setItems(mejaService.getAllMeja()); 
        cbMeja.setItemLabelGenerator(Meja::getNoMeja);

        cbMember.setItems(memberService.getAllMember()); 
        cbMember.setItemLabelGenerator(Member::getNama);

        btnSimpan.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnSimpan.addClickListener(e -> simpanPemesanan());
    }

    private VerticalLayout createFormLayout() {
        VerticalLayout form = new VerticalLayout(txtKode, numDurasi, cbMeja, cbMember, btnSimpan);
        form.setWidth("300px");
        return form;
    }

    private void configureGrid() {
        grid.addColumn(Pemesanan::getKodeBooking).setHeader("Kode Booking");
        grid.addColumn(p -> p.getMeja() != null ? p.getMeja().getNoMeja() : "-").setHeader("Meja");
        grid.addColumn(p -> p.getMember() != null ? p.getMember().getNama() : "Non-Member").setHeader("Pelanggan");
        grid.addColumn(Pemesanan::getDurasiJam).setHeader("Durasi");
        grid.addColumn(Pemesanan::getTotalBiaya).setHeader("Total Biaya");
        grid.addColumn(Pemesanan::getStatusPemesanan).setHeader("Status");

        // Menambahkan Tombol Aksi Batal & Hapus Booking
        grid.addComponentColumn(p -> {
            HorizontalLayout actions = new HorizontalLayout();
            
            Button btnBatal = new Button("Batal");
            btnBatal.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
            // Tombol Batal hanya aktif jika status pemesanan masih berjalan ("Mulai")
            btnBatal.setEnabled("Mulai".equalsIgnoreCase(p.getStatusPemesanan()));
            btnBatal.addClickListener(e -> {
                try {
                    pemesananService.batalkanPemesanan(p.getId());
                    Notification.show("Pemesanan berhasil dibatalkan!");
                    updateList();
                } catch (RuntimeException ex) {
                    Notification.show(ex.getMessage(), 5000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });

            Button btnHapus = new Button("Hapus");
            btnHapus.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            btnHapus.addClickListener(e -> {
                try {
                    pemesananService.hapusPemesanan(p.getId());
                    Notification.show("Data booking berhasil dihapus!");
                    updateList();
                } catch (RuntimeException ex) {
                    Notification.show(ex.getMessage(), 5000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });

            actions.add(btnBatal, btnHapus);
            return actions;
        }).setHeader("Aksi");

        grid.setSizeFull();
    }

    private void simpanPemesanan() {
        try {
            if (cbMeja.getValue() == null) {
                throw new RuntimeException("Silakan pilih meja terlebih dahulu.");
            }

            Pemesanan p = new Pemesanan();
            p.setKodeBooking(txtKode.getValue());
            p.setDurasiJam(numDurasi.getValue() != null ? numDurasi.getValue() : 1);
            p.setMeja(cbMeja.getValue());
            p.setMember(cbMember.getValue());

            // Memanggil Service utama
            pemesananService.buatPemesanan(p);
            
            Notification.show("Check-In Sukses!", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            
            clearForm();
            updateList();
        } catch (RuntimeException e) {
            // MENANGKAP ERROR JIKA MEJA PENUH / TERISI DARI SERVICE LAYER
            Notification.show(e.getMessage(), 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void clearForm() {
        txtKode.clear();
        numDurasi.clear();
        cbMeja.clear();
        cbMember.clear();
    }

    private void updateList() {
        grid.setItems(pemesananService.getAllPemesanan());
    }
}