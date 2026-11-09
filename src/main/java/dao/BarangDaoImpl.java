package dao;

import java.util.List;
import model.Barang;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BarangDaoImpl implements BarangDao {

    private final JdbcTemplate jdbcTemplate;

    public BarangDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mengubah 1 baris hasil query menjadi 1 objek Barang
    private final RowMapper<Barang> mapper = (rs, rowNum) -> {
        Barang b = new Barang();
        b.setKodeBarang(rs.getString("kode_barang"));
        b.setNamaBarang(rs.getString("nama_barang"));
        int idKat = rs.getInt("id_kategori");
        b.setIdKategori(rs.wasNull() ? null : idKat);
        b.setHargaBeli(rs.getDouble("harga_beli"));
        b.setHargaJual(rs.getDouble("harga_jual"));
        b.setStok(rs.getInt("stok"));
        b.setStokMin(rs.getInt("stok_min"));
        return b;
    };

    @Override
    public List<Barang> findAll() {
        return jdbcTemplate.query("SELECT * FROM barang ORDER BY kode_barang", mapper);
    }

    @Override
    public Barang findByKode(String kode) {
        List<Barang> hasil = jdbcTemplate.query(
                "SELECT * FROM barang WHERE kode_barang = ?", mapper, kode);
        return hasil.isEmpty() ? null : hasil.get(0);
    }

    @Override
    public int insert(Barang b) {
        return jdbcTemplate.update(
                "INSERT INTO barang (kode_barang, nama_barang, id_kategori, harga_beli, harga_jual, stok, stok_min) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                b.getKodeBarang(), b.getNamaBarang(), b.getIdKategori(),
                b.getHargaBeli(), b.getHargaJual(), b.getStok(), b.getStokMin());
    }

    @Override
    public int update(Barang b) {
        return jdbcTemplate.update(
                "UPDATE barang SET nama_barang=?, id_kategori=?, harga_beli=?, harga_jual=?, stok=?, stok_min=? "
                + "WHERE kode_barang=?",
                b.getNamaBarang(), b.getIdKategori(), b.getHargaBeli(),
                b.getHargaJual(), b.getStok(), b.getStokMin(), b.getKodeBarang());
    }

    @Override
    public int delete(String kode) {
        return jdbcTemplate.update("DELETE FROM barang WHERE kode_barang = ?", kode);
    }
}