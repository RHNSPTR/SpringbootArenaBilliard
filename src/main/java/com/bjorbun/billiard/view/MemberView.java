package com.bjorbun.billiard.view;

import com.bjorbun.billiard.model.Member;
import com.bjorbun.billiard.service.MemberService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "member", layout = MainLayout.class)
public class MemberView extends VerticalLayout {

    private final MemberService memberService;
    private Grid<Member> grid = new Grid<>(Member.class, false);
    
    private TextField txtIdMember = new TextField("ID Member");
    private TextField txtNama = new TextField("Nama");
    private TextField txtTelepon = new TextField("No Telepon");
    private Button btnSimpan = new Button("Tambah Member");

    public MemberView(MemberService memberService) {
        this.memberService = memberService;
        setSizeFull();
        add(new H2("Manajemen Data Member"));

        configureForm();
        configureGrid();

        HorizontalLayout mainContent = new HorizontalLayout(grid, createFormLayout());
        mainContent.setSizeFull();
        mainContent.setFlexGrow(1, grid);

        add(mainContent);
        updateList();
    }

    private void configureForm() {
        btnSimpan.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnSimpan.addClickListener(e -> {
            Member m = new Member();
            m.setIdMember(txtIdMember.getValue());
            m.setNama(txtNama.getValue());
            m.setNoTelepon(txtTelepon.getValue());
            m.setPoinLoyalitas(0);
            
            memberService.saveMember(m);
            Notification.show("Member baru berhasil terdaftar!");
            clearForm();
            updateList();
        });
    }

    private VerticalLayout createFormLayout() {
        VerticalLayout form = new VerticalLayout(txtIdMember, txtNama, txtTelepon, btnSimpan);
        form.setWidth("300px");
        return form;
    }

    private void configureGrid() {
        grid.addColumn(m -> m.getIdMember()).setHeader("ID Member");
        grid.addColumn(m -> m.getNama()).setHeader("Nama");
        grid.addColumn(m -> m.getNoTelepon()).setHeader("No Telepon");
        grid.addColumn(m -> m.getPoinLoyalitas()).setHeader("Poin");

        // Kolom Aksi Hapus Member
        grid.addComponentColumn(m -> {
            Button btnHapus = new Button("Hapus");
            btnHapus.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            btnHapus.addClickListener(e -> {
                try {
                    memberService.hapusMember(m.getId());
                    Notification.show("Member berhasil dihapus!");
                    updateList();
                } catch (RuntimeException ex) {
                    Notification.show(ex.getMessage());
                }
            });
            return btnHapus;
        }).setHeader("Aksi");

        grid.setSizeFull();
    }

    private void clearForm() {
        txtIdMember.clear();
        txtNama.clear();
        txtTelepon.clear();
    }

    private void updateList() {
        grid.setItems(memberService.getAllMember());
    }
}