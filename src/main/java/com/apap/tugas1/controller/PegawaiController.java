package com.apap.tugas1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

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
	
	@Autowired
	private ProvinsiService provinsiService;
	
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

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		List<ProvinsiModel> listProvinsi = provinsiService.listProvinsi();
		List<InstansiModel> listInstansi = instansiService.getInstansiProvinsi(listProvinsi.get(0));
		pegawai.setListJabatan(new ArrayList<JabatanModel>());
		pegawai.getListJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("tambahpegawai", true);
		model.addAttribute("pageTitle", "Tambah Pegawai");
		
		return "tambah-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String kodeInstansi = pegawai.getInstansi().getId() + "";
		SimpleDateFormat changeFormat = new SimpleDateFormat("dd-mm-yy");
		String tanggalLahir = changeFormat.format(pegawai.getTanggal_lahir()).replaceAll("-","");
		String tahunMasuk = pegawai.getTahun_masuk();

		String urutan;
		int noUrut = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggal_lahir(), pegawai.getTahun_masuk()).size() + 1;
		if(noUrut < 10) {
			urutan= "0" + noUrut;
		}
		else {
			urutan= noUrut + "";
		}
		
		String nip = kodeInstansi + tanggalLahir + tahunMasuk + urutan;
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("nipnya", nip);
		model.addAttribute("pageTitle", "Tambah Pegawai");
		
		return "tambahpeg";
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST, params= {"addRow"})
	public String addRowTambah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		List<ProvinsiModel> listProvinsi = provinsiService.listProvinsi();
		List<InstansiModel> listInstansi = instansiService.getInstansiProvinsi(pegawai.getInstansi().getProvinsi());
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		
		pegawai.getListJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    model.addAttribute("tambahpegawai", true);
	    return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	private String cariPegawai(Model model) {
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		List<InstansiModel> listInstansi = instansiService.listInstansi();
		List<ProvinsiModel> listProvinsi = provinsiService.listProvinsi();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("pageTitle", "Cari Pegawai");
		
		return "cari-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.GET)
	public String updatePegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);

		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		List<ProvinsiModel> listProvinsi = provinsiService.listProvinsi();
		List<InstansiModel> listInstansi = instansiService.getInstansiProvinsi(pegawai.getInstansi().getProvinsi());
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
	    model.addAttribute("pegawai", pegawai);
	    model.addAttribute("pageTitle", "Ubah Pegawai");
		
	    return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String nipCurr = pegawai.getNip();
		String nipNew;
		
		PegawaiModel pegawaiCurr = pegawaiService.getPegawaiDetailByNip(nipCurr);
		if((!pegawaiCurr.getTahun_masuk().equals(pegawai.getTahun_masuk())) || (!pegawaiCurr.getTanggal_lahir().equals(pegawai.getTanggal_lahir())) || (!pegawaiCurr.getInstansi().equals(pegawai.getInstansi()))) {
			String kodeInstansi = pegawai.getInstansi().getId() + "";
			SimpleDateFormat changeFormat = new SimpleDateFormat("dd-mm-yy");
			String tanggalLahir = changeFormat.format(pegawai.getTanggal_lahir()).replaceAll("-","");
			String tahunKerja = pegawai.getTahun_masuk();
			
			String urutan;
			int noUrut = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggal_lahir(), pegawai.getTahun_masuk()).size() + 1;
			if(noUrut < 10) {
				urutan= "0" + noUrut;
			}
			else {
				urutan= noUrut + "";
			}
			
			nipNew = kodeInstansi + tanggalLahir + tahunKerja + urutan;
			pegawai.setNip(nipNew);
		}
		else {
			 nipNew = nipCurr;
			 pegawai.setNip(nipCurr);
		}
	
		pegawaiService.updatePegawai(nipCurr, pegawai);
		model.addAttribute("nipnya", pegawai.getNip());
		model.addAttribute("pageTitle", "Ubah Pegawai");
		
		return "updatepeg";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"addRow"}, method = RequestMethod.POST)
	public String addRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.listProvinsi();
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getInstansiProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		
		ProvinsiModel provinsi = pegawai.getInstansi().getProvinsi();
		model.addAttribute("selectedItem", provinsi);
		
		
		pegawai.getListJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    model.addAttribute("pageTitle", "Ubah Pegawai");
		
	    return "update-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		List<ProvinsiModel> listProv = provinsiService.listProvinsi();
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		
		List<InstansiModel> listInstansi = instansiService.getInstansiProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		pegawai.getListJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.POST)
	private String findPegawaiCari(@ModelAttribute PegawaiModel pegawai1, @RequestParam("instansi")long idInstansi, @RequestParam("provinsi")long idProvinsi, @RequestParam("jabatan")long idJabatan, Model model) {
		InstansiModel instansi = instansiService.findInstansiById(idInstansi);
		JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
		List<PegawaiModel> listPegawai = pegawaiService.getFilter(idInstansi, idJabatan);
		model.addAttribute("nama", instansi.getNama());
		model.addAttribute("namaJabatan", jabatan.getNama());
	    model.addAttribute("listPegawai", listPegawai);
	    
	    List<ProvinsiModel> listProvinsi = provinsiService.listProvinsi();
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		List<InstansiModel> listInstansi = instansiService.listInstansi();
		
	    model.addAttribute("listJabatan", listJabatan);
	    model.addAttribute("listInstansi", listInstansi);
	    model.addAttribute("listProvinsi", listProvinsi);
	    
		return "cari-pegawai";
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
			model.addAttribute("pageTitle", "Detail Termuda Tertua");
			
			return "view-termuda-tertua";
		}
		
		
		return "view-termuda-tertua";
	}	
}	
