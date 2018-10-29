package com.apap.tugas1.model;



import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * PegawaiModel
 */

@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nip", nullable = false, unique = true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempatLahir;
	
	@NotNull
	@Column(name = "tanggal_lahir", nullable = false)
	private Date tanggalLahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahunMasuk;
	
	//
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE) //
	@JsonIgnore
	private InstansiModel instansi;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "jabatan_pegawai", joinColumns = { @JoinColumn(name="id_pegawai", referencedColumnName="id")}, inverseJoinColumns = { @JoinColumn(name="id_jabatan", referencedColumnName="id") }) 
	 private List<JabatanModel> listJabatan;


	// setter getter
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempat_lahir() {
		return tempatLahir;
	}

	public void setTempat_lahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public Date getTanggal_lahir() {
		return tanggalLahir;
	}

	public void setTanggal_lahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTahun_masuk() {
		return tahunMasuk;
	}

	public void setTahun_masuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}

	public List<JabatanModel> getListJabatan() {
		return listJabatan;
	}

	public void setListJabatan(List<JabatanModel> listJabatan) {
		this.listJabatan = listJabatan;
	}
	
	public double getGaji() {
		double tunjangan = (instansi.getProvinsi().getPresentase_tunjangan())/100;
		double gajiTerbesar = 0;
		for(JabatanModel jabatan : listJabatan) {
			double gajiPokok = jabatan.getGaji_pokok();
			if(gajiPokok > gajiTerbesar) {
				gajiTerbesar = gajiPokok;
			}
		}

		double gaji = gajiTerbesar + (tunjangan*gajiTerbesar);
		return gaji;
	}
	
	public int getUmur() {
		// TODO Auto-generated method stub
		LocalDate birthday = tanggalLahir.toLocalDate();
		LocalDate now = LocalDate.now();
		
		return now.getYear() - birthday.getYear();
	}
}
