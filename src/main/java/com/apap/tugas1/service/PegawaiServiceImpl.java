package com.apap.tugas1.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Autowired
	private InstansiDb instansiDb;
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.save(pegawai);
	}
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public double getGajiByNip(String nip) {
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

	@Override
	public List<PegawaiModel> getFilter(long idInstansi, long idJabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> list = new ArrayList<PegawaiModel>();
		
		InstansiModel instansi = instansiDb.findById(idInstansi);
		List<PegawaiModel> listPegawai = pegawaiDb.findByInstansi(instansi);
		for(PegawaiModel pegawai : listPegawai) {
			for(JabatanModel jabatanA : pegawai.getListJabatan()) {
				if(jabatanA.getId() == idJabatan) {
					list.add(pegawai);
				}
			}
		}
		return list;
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}

	@Override
	public void updatePegawai(String nip, PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		PegawaiModel updatePegawai = pegawaiDb.findByNip(nip);
		updatePegawai.setNama(pegawai.getNama());
		updatePegawai.setNip(pegawai.getNip());
		updatePegawai.setTanggal_lahir(pegawai.getTanggal_lahir());
		updatePegawai.setTempat_lahir(pegawai.getTempat_lahir());
		updatePegawai.setTahun_masuk(pegawai.getTahun_masuk());
		updatePegawai.setInstansi(pegawai.getInstansi());
		updatePegawai.setListJabatan(pegawai.getListJabatan());
		pegawaiDb.save(updatePegawai);
	}

		
}
