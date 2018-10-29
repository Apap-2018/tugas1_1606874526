package com.apap.tugas1.service;

import java.util.List;


import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	InstansiModel findInstansiById(long id);
	List<InstansiModel> listInstansi();
	List<InstansiModel> getInstansiProvinsi(ProvinsiModel provinsi);
	

}
