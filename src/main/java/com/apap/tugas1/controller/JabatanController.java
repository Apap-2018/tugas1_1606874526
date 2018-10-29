package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

/**
 * JabatanController
 */
@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String addJabatan(Model model) {

		model.addAttribute("jabatan", new JabatanModel());

		model.addAttribute("pageTitle", "Tambah Jabatan");
		return "tambah-jabatan";
	}

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);

		model.addAttribute("pageTitle", "Tambah Jabatan Berhasil");
		return "tambah";
	}

	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam(value = "id_jabatan", required = true) long id_jabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id_jabatan);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("pageTitle", "Detail Jabatan");

		return "view-jabatan";
	}

	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	private String viewAllJabatan(Model model) {
		List<JabatanModel> jabatan = jabatanService.listJabatan();

		model.addAttribute("listJabatan", jabatan);

		model.addAttribute("pageTitle", "Viewall Jabatan");
		return "viewall-jabatan";
	}

	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel jabatanCurr = jabatanService.getJabatanDetailById(jabatan.getId());
		model.addAttribute("jabatan", jabatanCurr.getNama());
		jabatanService.deleteJabatanById(jabatan.getId());
		model.addAttribute("pageTitle", "Hapus Jabatan");
		
		return "delete";
	}

	@RequestMapping(value = "/jabatan/update-jabatan", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value = "id_jabatan", required = true) long id_jabatan, Model model) {
		model.addAttribute("jabatan", jabatanService.getJabatanDetailById(id_jabatan));
		model.addAttribute("pageTitle", "Ubah Jabatan");
		
		return "update-jabatan";
	}

	@RequestMapping(value = "/jabatan/update-jabatan", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.updateJabatan(jabatan);
		model.addAttribute("pageTitle", "Ubah Jabatan");
		
		return "update";
	}
}
