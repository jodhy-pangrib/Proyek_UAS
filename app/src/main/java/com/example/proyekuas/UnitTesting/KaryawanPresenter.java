package com.example.proyekuas.UnitTesting;

import com.example.proyekuas.Volley.models.Karyawan;

public class KaryawanPresenter {

    private KaryawanView view;
    private KaryawanService service;
    private KaryawanCallback callback;
    private Karyawan karyawan;

    public KaryawanPresenter(KaryawanView view, KaryawanService service){
        this.view = view;
        this.service = service;
    }

    public void onKaryawanClicked() {
        String regex = "\\d{4}";

        if (view.getNamaKaryawan().isEmpty()) {
            view.showNamaError("Nama tidak boleh kosong");
            return;
        } else if (view.getNomorKaryawan().isEmpty()) {
            view.showNomorKaryawanError("Nomor Karyawan tidak boleh kosong");
            return;
        } else if (!(view.getNomorKaryawan().matches(regex))) {
            view.showNomorKaryawanError("Nomor Karyawan harus 4 digit dan Angka Semua");
            return;
        } else if (view.getUmurKaryawan().isEmpty()) {
            view.showUmurKaryawanError("Umur tidak boleh kosong");
            return;
        } else if (Integer.valueOf(view.getUmurKaryawan()) < 20 || Integer.valueOf(view.getUmurKaryawan()) > 40) {
            view.showUmurKaryawanError("Umur minimal 20 maksimal 40");
            return;
        } else if (view.getJenisKelamin().isEmpty()) {
            view.showJenisKelaminError("Jenis Kelamin tidak boleh kosong");
            return;
        } else if (view.getRoleKaryawan().isEmpty()) {
            view.showRoleKaryawanError("Role Karyawan tidak boleh kosong");
            return;
        } else {
            service.karyawan(view, karyawan, new KaryawanCallback() {
                @Override
                public void onSuccess(boolean value, Karyawan karyawan) {
                    view.startAddEditKaryawan();
                }

                @Override
                public void onError() {

                }
            });
            return;
        }
    }
}
