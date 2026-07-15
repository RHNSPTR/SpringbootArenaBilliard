package com.bjorbun.billiard.view;

import com.bjorbun.billiard.model.Member;
import com.bjorbun.billiard.service.MemberService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

@Route(value = "member", layout = MainLayout.class)
public class MemberView extends VerticalLayout {

    private final MemberService memberService;

    // Grid untuk tabel dan Binder untuk form
    private Grid<Member> grid = new Grid<>(Member.class, false);
    private Binder<Member> binder = new Binder<>(Member.class);

    // Komponen Input Form (Namanya sudah disesuaikan persis dengan Member.java)
    private TextField idMember = new TextField("ID Member");
    private TextField nama = new TextField("Nama Lengkap");
    private TextField noTelepon = new TextField("Nomor Telepon");
    private IntegerField poinLoyalitas = new IntegerField("Poin Loyalitas");

    private Button saveButton = new Button("Simpan", VaadinIcon.CHECK.create());
    private Button clearButton = new Button("Batal / Tambah Baru");

    public MemberView(MemberService memberService) {
        this.memberService = memberService;

        setSizeFull();

        H2 judul = new H2("Manajemen Data Member");
        judul.getStyle().set("color", "#d7b257"); // Tema Emas PPT

        konfigurasiGrid();
        konfigurasiForm();

        HorizontalLayout formLayout = new HorizontalLayout(idMember, nama, noTelepon, poinLoyalitas);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, clearButton);

        add(judul, formLayout, buttonLayout, grid);

        perbaruiTabel();
        bersihkanForm();
    }

    private void konfigurasiGrid() {
        // Mengatur kolom tabel sesuai Member.java
        grid.addColumn(Member::getIdMember).setHeader("ID Member").setSortable(true);
        grid.addColumn(Member::getNama).setHeader("Nama Lengkap").setSortable(true);
        grid.addColumn(Member::getNoTelepon).setHeader("No. Telepon");
        grid.addColumn(Member::getPoinLoyalitas).setHeader("Poin Loyalitas").setSortable(true);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        // Listener saat baris di-klik untuk proses edit
        grid.asSingleSelect().addValueChangeListener(event -> editMember(event.getValue()));
    }

    private void konfigurasiForm() {
        // Mengikat textfield ke variabel di entitas Member secara otomatis
        binder.bindInstanceFields(this);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.getStyle().set("background-color", "#d7b257");
        saveButton.getStyle().set("color", "#0a261d");

        saveButton.addClickListener(event -> simpanData());
        clearButton.addClickListener(event -> bersihkanForm());
    }

    private void simpanData() {
        Member member = binder.getBean();

        if (member.getNama() == null || member.getNama().trim().isEmpty()) {
            Notification notif = Notification.show("Nama Member tidak boleh kosong!");
            notif.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Simpan via Service
        memberService.saveMember(member);

        perbaruiTabel();
        bersihkanForm();

        Notification notif = Notification.show("Data Member berhasil disimpan!");
        notif.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void editMember(Member member) {
        if (member == null) {
            bersihkanForm();
        } else {
            binder.setBean(member);
        }
    }

    private void bersihkanForm() {
        binder.setBean(new Member());
        grid.asSingleSelect().clear();
    }

    private void perbaruiTabel() {
        // Tarik data dari DB
        grid.setItems(memberService.getAllMember());
    }
}