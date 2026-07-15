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
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;

@Route(value = "pemesanan", layout = MainLayout.class)
public class PemesananView extends VerticalLayout {

    private final PemesananService pemesananService;
    private final MejaService mejaService;
    private final MemberService memberService;

    // Grid dan Binder
    private Grid<Pemesanan> grid = new Grid<>(Pemesanan.class, false);
    private Binder<Pemesanan> binder = new Binder<>(Pemesanan.class);

    // Komponen Form
    private TextField kodeBooking = new TextField("Kode Booking");
    private ComboBox<Member> member = new ComboBox<>("Pilih Member");
    private ComboBox<Meja> meja = new ComboBox<>("Pilih Meja");
    private DateTimePicker waktuMulai = new DateTimePicker("Waktu Mulai");
    private IntegerField durasiJam = new IntegerField("Durasi (Jam)");

    private Button saveButton = new Button("Proses Pesanan", VaadinIcon.TICKET.create());
    private Button clearButton = new Button("Batal / Pesanan Baru");

    public PemesananView(PemesananService pemesananService, MejaService mejaService, MemberService memberService) {
        this.pemesananService = pemesananService;
        this.mejaService = mejaService;
        this.memberService = memberService;

        setSizeFull();

        H2 judul = new H2("Transaksi Reservasi Billiard");
        judul.getStyle().set("color", "#d7b257"); // Tema Emas PPT

        konfigurasiGrid();
        konfigurasiForm();

        HorizontalLayout barisPertama = new HorizontalLayout(kodeBooking, member, meja);
        barisPertama.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        
        HorizontalLayout barisKedua = new HorizontalLayout(waktuMulai, durasiJam);
        barisKedua.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, clearButton);

        add(judul, barisPertama, barisKedua, buttonLayout, grid);

        perbaruiTabel();
        bersihkanForm();
    }

    private void konfigurasiGrid() {
        grid.addColumn(Pemesanan::getKodeBooking).setHeader("Kode Booking");
        
        // Menampilkan nama member, jika null tampilkan strip (-)
        grid.addColumn(p -> p.getMember() != null ? p.getMember().getNama() : "-")
            .setHeader("Pelanggan");
            
        // Menampilkan nomor meja, jika null tampilkan strip (-)
        grid.addColumn(p -> p.getMeja() != null ? p.getMeja().getNoMeja() : "-")
            .setHeader("Nomor Meja");
            
        // Memformat waktu agar rapi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        grid.addColumn(p -> p.getWaktuMulai() != null ? p.getWaktuMulai().format(formatter) : "-")
            .setHeader("Waktu Mulai");
            
        grid.addColumn(Pemesanan::getDurasiJam).setHeader("Durasi (Jam)");
        
        grid.addColumn(p -> p.getTotalBiaya() != null ? "Rp " + p.getTotalBiaya() : "Rp 0")
            .setHeader("Total Biaya");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editPesanan(event.getValue()));
    }

    private void konfigurasiForm() {
        // Mengisi dropdown Member dari database dan mengatur agar yang tampil adalah namanya
        member.setItems(memberService.getAllMember());
        member.setItemLabelGenerator(Member::getNama);

        // Mengisi dropdown Meja dari database dan mengatur agar yang tampil adalah nomor mejanya
        meja.setItems(mejaService.getAllMeja());
        meja.setItemLabelGenerator(m -> "Meja " + m.getNoMeja() + " - " + m.getTipeMeja());

        binder.bindInstanceFields(this);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.getStyle().set("background-color", "#d7b257");
        saveButton.getStyle().set("color", "#0a261d");

        saveButton.addClickListener(event -> simpanData());
        clearButton.addClickListener(event -> bersihkanForm());
    }

    private void simpanData() {
        Pemesanan pesanan = binder.getBean();

        // Validasi Sederhana
        if (pesanan.getKodeBooking() == null || pesanan.getKodeBooking().isEmpty()) {
            Notification.show("Kode Booking harus diisi!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }
        if (pesanan.getMeja() == null) {
            Notification.show("Pilih meja terlebih dahulu!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }
        if (pesanan.getDurasiJam() == null || pesanan.getDurasiJam() <= 0) {
            Notification.show("Durasi harus lebih dari 0!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Simpan menggunakan service, di mana total biaya akan dihitung otomatis
        pemesananService.buatPesanan(pesanan);

        perbaruiTabel();
        bersihkanForm();

        Notification.show("Pesanan berhasil diproses!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void editPesanan(Pemesanan pesanan) {
        if (pesanan == null) {
            bersihkanForm();
        } else {
            binder.setBean(pesanan);
        }
    }

    private void bersihkanForm() {
        binder.setBean(new Pemesanan());
        grid.asSingleSelect().clear();
    }

    private void perbaruiTabel() {
        grid.setItems(pemesananService.getAllPemesanan());
    }
}