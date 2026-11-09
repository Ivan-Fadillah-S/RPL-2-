package dao;

import java.util.List;
import model.Barang;

public interface BarangDao {
    List<Barang> findAll();
    Barang findByKode(String kode);
    int insert(Barang barang);
    int update(Barang barang);
    int delete(String kode);
}