package com.example.proyekuas.UnitTesting;

public interface KaryawanView {

    String getNamaKaryawan();
    void showNamaError(String message);

    String getNomorKaryawan();
    void showNomorKaryawanError(String message);

    String getUmurKaryawan();
    void showUmurKaryawanError(String message);

    String getJenisKelamin();
    void showJenisKelaminError(String message);

    String getRoleKaryawan();
    void showRoleKaryawanError(String message);

    void startAddEditKaryawan();

    void showAddEditKaryawanError(String message);
    void showErrorResponse(String message);
}
