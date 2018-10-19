package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.JabatanPegawaiDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb jabatanDb;

	@Autowired
    private JabatanPegawaiDb jabatanPegawaiDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
		
	}

	@Override
	public JabatanModel getJabatanDetailById(long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(id);
	}

	@Override
	public List<JabatanModel> listJabatan() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}

	@Override
	public void deleteJabatanById(long id) {
		// TODO Auto-generated method stub
		jabatanDb.deleteById(id);
	}

	@Override
	public void updateJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		JabatanModel updateJabatan = jabatanDb.findById(jabatan.getId());
		updateJabatan.setNama(jabatan.getNama());
		updateJabatan.setDeskripsi(jabatan.getDeskripsi());
		updateJabatan.setGaji_pokok(jabatan.getGaji_pokok());
		jabatanDb.save(updateJabatan);
	}

	

	

	

	
	
}
