package com.example.proyekuas.UnitTesting;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

public class KaryawanPresenterTest {

    @Mock
    private KaryawanView view;
    @Mock
    private KaryawanService service;
    private KaryawanPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new KaryawanPresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenNameIsEmpty() throws Exception {
        when(view.getNamaKaryawan()).thenReturn("");
        System.out.println("\n\n" + "Testing Pertama: Inputan Nama Karyawan Kosong");
        System.out.println("Nama : " + view.getNamaKaryawan());
        presenter.onKaryawanClicked();
        verify(view).showNamaError("Nama tidak boleh kosong");
        System.out.println("qwoeubqwid");
    }

    @Test
    public void shouldShowErrorMessageWhenNomorIsEmpty() throws Exception {
        System.out.println("\n\n" + "Testing Kedua: Inputan Nomor Karyawan Kosong");
        when(view.getNamaKaryawan()).thenReturn("Calvin");
        System.out.println("Nama Karyawan : " + view.getNamaKaryawan());
        when(view.getNomorKaryawan()).thenReturn("");
        System.out.println("Nomor Karyawan : " + view.getNomorKaryawan());
        presenter.onKaryawanClicked();
        verify(view).showNomorKaryawanError("Nomor Karyawan tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenNomorNot4Digits() throws Exception {
        System.out.println("\n\n" + "Testing Ketiga: Inputan Nomor Karyawan Bukan 4 Digit");
        when(view.getNamaKaryawan()).thenReturn("Calvin");
        System.out.println("Nama Karyawan : " + view.getNamaKaryawan());
        when(view.getNomorKaryawan()).thenReturn("12345");
        System.out.println("Nomor Karyawan : " + view.getNomorKaryawan());
        presenter.onKaryawanClicked();
        verify(view).showNomorKaryawanError("Nomor Karyawan harus 4 digit dan Angka Semua");
    }

    @Test
    public void shouldShowErrorMessageWhenNomorIsNotNumber() throws Exception {
        System.out.println("\n\n" + "Testing Keempat: Inputan Nomor Karyawan Bukan Angka");
        when(view.getNamaKaryawan()).thenReturn("Calvin");
        System.out.println("Nama Karyawan : " + view.getNamaKaryawan());
        when(view.getNomorKaryawan()).thenReturn("qwer");
        System.out.println("Nomor Karyawan : " + view.getNomorKaryawan());
        presenter.onKaryawanClicked();
        verify(view).showNomorKaryawanError("Nomor Karyawan harus 4 digit dan Angka Semua");
    }

    @Test
    public void shouldShowErrorMessageWhenUmurIsEmpty() throws Exception {
        System.out.println("\n\n" + "Testing Kelima: Inputan Umur Karyawan Kosong");
        when(view.getNamaKaryawan()).thenReturn("Calvin");
        System.out.println("Nama Karyawan : " + view.getNamaKaryawan());
        when(view.getNomorKaryawan()).thenReturn("1234");
        System.out.println("Nomor Karyawan : " + view.getNomorKaryawan());
        when(view.getUmurKaryawan()).thenReturn("");
        System.out.println("Umur Karyawan : " + view.getUmurKaryawan());
        presenter.onKaryawanClicked();
        verify(view).showUmurKaryawanError("Umur tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenUmurLessThan20() throws Exception {
        System.out.println("\n\n" + "Testing Keenam: Inputan Umur Karyawan < 20");
        when(view.getNamaKaryawan()).thenReturn("Calvin");
        System.out.println("Nama Karyawan : " + view.getNamaKaryawan());
        when(view.getNomorKaryawan()).thenReturn("1234");
        System.out.println("Nomor Karyawan : " + view.getNomorKaryawan());
        when(view.getUmurKaryawan()).thenReturn("19");
        System.out.println("Umur Karyawan : " + view.getUmurKaryawan());
        presenter.onKaryawanClicked();
        verify(view).showUmurKaryawanError("Umur minimal 20 maksimal 40");
    }

    @Test
    public void shouldShowErrorMessageWhenUmurGreaterThan40() throws Exception {
        System.out.println("\n\n" + "Testing Ketujuh: Inputan Umur Karyawan > 40");
        when(view.getNamaKaryawan()).thenReturn("Calvin");
        System.out.println("Nama Karyawan : " + view.getNamaKaryawan());
        when(view.getNomorKaryawan()).thenReturn("1234");
        System.out.println("Nomor Karyawan : " + view.getNomorKaryawan());
        when(view.getUmurKaryawan()).thenReturn("41");
        System.out.println("Umur Karyawan : " + view.getUmurKaryawan());
        presenter.onKaryawanClicked();
        verify(view).showUmurKaryawanError("Umur minimal 20 maksimal 40");
    }

    @Test
    public void shouldShowErrorMessageWhenJenisKelaminIsEmpty() throws Exception {
        System.out.println("\n\n" + "Testing Kedelapan: Inputan Jenis Kelamin Kosong");
        when(view.getNamaKaryawan()).thenReturn("Calvin");
        System.out.println("Nama Karyawan : " + view.getNamaKaryawan());
        when(view.getNomorKaryawan()).thenReturn("1234");
        System.out.println("Nomor Karyawan : " + view.getNomorKaryawan());
        when(view.getUmurKaryawan()).thenReturn("21");
        System.out.println("Umur Karyawan : " + view.getUmurKaryawan());
        when(view.getJenisKelamin()).thenReturn("");
        System.out.println("Jenis Kelamin Karyawan : " + view.getJenisKelamin());
        presenter.onKaryawanClicked();
        verify(view).showJenisKelaminError("Jenis Kelamin tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenRoleKaryawanIsEmpty() throws Exception {
        System.out.println("\n\n" + "Testing Kesembilan: Inputan Role Karyawan Kosong");
        when(view.getNamaKaryawan()).thenReturn("Calvin");
        System.out.println("Nama Karyawan : " + view.getNamaKaryawan());
        when(view.getNomorKaryawan()).thenReturn("1234");
        System.out.println("Nomor Karyawan : " + view.getNomorKaryawan());
        when(view.getUmurKaryawan()).thenReturn("21");
        System.out.println("Umur Karyawan : " + view.getUmurKaryawan());
        when(view.getJenisKelamin()).thenReturn("Perempuan");
        System.out.println("Jenis Kelamin Karyawan : " + view.getJenisKelamin());
        when(view.getRoleKaryawan()).thenReturn("");
        System.out.println("Role Karyawan : "  + view.getRoleKaryawan());
        presenter.onKaryawanClicked();
        verify(view).showRoleKaryawanError("Role Karyawan tidak boleh kosong");
    }

}