package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;


public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	double getGajiByNip(String nip);
	List <PegawaiModel> getFilter(long idInstansi, long idJabatan);
	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
	void addPegawai(PegawaiModel pegawai);
	void updatePegawai(String nip, PegawaiModel pegawai);
	
}
