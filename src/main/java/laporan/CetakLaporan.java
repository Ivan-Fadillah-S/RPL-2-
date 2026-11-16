package laporan;

import config.Koneksi;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class CetakLaporan {

    public static void tampilkan(String namaFile, String judul) {
        try {
            // 1. Ambil koneksi (JANGAN ditutup, dipakai bersama oleh form lain)
            Connection conn = Koneksi.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Koneksi database gagal. Cek MySQL di XAMPP.");
                return;
            }

            // 2. Ambil file .jrxml dari folder resources/reports
            InputStream file = CetakLaporan.class.getResourceAsStream("/reports/" + namaFile);
            if (file == null) {
                JOptionPane.showMessageDialog(null, "File laporan tidak ditemukan: " + namaFile);
                return;
            }

            // 3. Compile .jrxml menjadi laporan
            JasperReport laporan = JasperCompileManager.compileReport(file);

            // 4. Isi laporan dengan data dari database
            JasperPrint hasil = JasperFillManager.fillReport(
                    laporan, new HashMap<String, Object>(), conn);

            // 5. Tampilkan jendela preview
            JasperViewer viewer = new JasperViewer(hasil, false);
            viewer.setTitle(judul);
            viewer.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal cetak laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }
}