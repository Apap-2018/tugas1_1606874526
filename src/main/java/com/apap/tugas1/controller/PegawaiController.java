package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

/**
 * PegawaiController
 */
@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List <JabatanModel> jabatan = jabatanService.listJabatan();
		List <InstansiModel> instansi = instansiService.listInstansi();
		model.addAttribute("pageTitle", "Home");
		model.addAttribute("listJabatan", jabatan);
		model.addAttribute("listInstansi", instansi);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam(value = "nip", required = true) String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		if (pegawai != null) {
			model.addAttribute("pegawai", pegawai);
			model.addAttribute("pageTitle", "Detail Pegawai");
			double gaji = pegawai.getGaji();
			model.addAttribute("gaji", gaji) ;
			return "view-pegawai";
		}
		return "home";
	}


	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String lihatPegawaiTermudaTertua(@RequestParam("idInstansi") long idInstansi, Model model) {
		InstansiModel instansi = instansiService.findInstansiById(idInstansi);
		List<PegawaiModel> listPegawai = instansi.getListPegawaiInstansi();
		
		
		if(!listPegawai.isEmpty()) {
			PegawaiModel pegawaiTertua = listPegawai.get(0);
			PegawaiModel pegawaiTermuda = listPegawai.get(1);
			
			for(PegawaiModel pegawai : listPegawai) {
				if(pegawai.getUmur() > pegawaiTertua.getUmur()) {
					pegawaiTertua = pegawai;
				}
				else if(pegawai.getUmur() < pegawaiTermuda.getUmur()) {
					pegawaiTermuda = pegawai;
				}
			} 
			
			int gajiTertua = (int)pegawaiTertua.getGaji();
			int gajiTermuda = (int)pegawaiTermuda.getGaji();
			
			model.addAttribute("tertua", pegawaiTertua);
			model.addAttribute("termuda", pegawaiTermuda);
			
			model.addAttribute("gajiTertua", gajiTertua);
			model.addAttribute("gajiTermuda", gajiTermuda);
			return "view-termuda-tertua";
		}
		
		
		return "view-termuda-tertua";
	}	
}	
