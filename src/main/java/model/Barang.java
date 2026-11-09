package model;

public class Barang {
    private String kodeBarang;
    private String namaBarang;
    private Integer idKategori; // boleh null
    private double hargaBeli;
    private double hargaJual;
    private int stok;
    private int stokMin;

    public String getKodeBarang() { return kodeBarang; }
    public void setKodeBarang(String kodeBarang) { this.kodeBarang = kodeBarang; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }

    public Integer getIdKategori() { return idKategori; }
    public void setIdKategori(Integer idKategori) { this.idKategori = idKategori; }

    public double getHargaBeli() { return hargaBeli; }
    public void setHargaBeli(double hargaBeli) { this.hargaBeli = hargaBeli; }

    public double getHargaJual() { return hargaJual; }
    public void setHargaJual(double hargaJual) { this.hargaJual = hargaJual; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public int getStokMin() { return stokMin; }
    public void setStokMin(int stokMin) { this.stokMin = stokMin; }
}