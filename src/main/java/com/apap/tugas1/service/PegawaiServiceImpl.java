package com.apap.tugas1.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public double getGajiLengkapByNip(String nip) {
		// TODO Auto-generated method stub
		double gajiLengkap = 0;
        PegawaiModel pegawai = this.getPegawaiDetailByNip(nip);
        double gajiTerbesar = 0;
        for (JabatanModel jabatan : pegawai.getListJabatan()) {
            if (jabatan.getGaji_pokok() > gajiTerbesar) {
                gajiTerbesar = jabatan.getGaji_pokok();
            }
        }
        gajiLengkap += gajiTerbesar;
        double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentase_tunjangan();
        gajiLengkap += (gajiLengkap * presentaseTunjangan/100);
        return gajiLengkap;
	}

	
	
	
}
