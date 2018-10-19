package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {

	void addJabatan(JabatanModel jabatan);
	JabatanModel getJabatanDetailById(long id);
	List<JabatanModel> listJabatan();
	void deleteJabatanById(long id);
	void updateJabatan(JabatanModel jabatan);
}
