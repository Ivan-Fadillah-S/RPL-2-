package views;

import config.Tema;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** Halaman beranda (tampilan saja): sapaan + kartu pintasan ke menu yang sudah ada. */
public class Beranda extends JPanel {

    public static class Pintasan {
        final String judul, deskripsi; final JMenuItem item; final JMenu induk; final Color warna;
        public Pintasan(String judul, String deskripsi, JMenuItem item, JMenu induk, Color warna) {
            this.judul = judul; this.deskripsi = deskripsi; this.item = item; this.induk = induk; this.warna = warna;
        }
    }

    public Beranda(String nama, String role, Pintasan... daftar) {
        setLayout(new BorderLayout());
        setBackground(Tema.LATAR);

        // Banner atas
        JPanel banner = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, Tema.PRIMER_GELAP, getWidth(), getHeight(), Tema.PRIMER));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        banner.setBorder(new EmptyBorder(28, 36, 28, 36));
        JLabel judul = new JLabel("ERP Toko");
        judul.setFont(judul.getFont().deriveFont(Font.BOLD, 30f));
        judul.setForeground(Color.WHITE);
        String sapa = (nama == null || nama.isEmpty()) ? "Selamat datang" : "Selamat datang, " + nama;
        String peran = (role == null || role.isEmpty()) ? "" : "   |   Peran: " + role;
        JLabel sub = new JLabel(sapa + peran);
        sub.setFont(sub.getFont().deriveFont(15f));
        sub.setForeground(new Color(0xDBEAFE));
        JPanel teks = new JPanel(new GridLayout(2, 1, 0, 4));
        teks.setOpaque(false);
        teks.add(judul); teks.add(sub);
        banner.add(teks, BorderLayout.CENTER);
        add(banner, BorderLayout.NORTH);

        // Grid kartu
        JPanel isi = new JPanel(new BorderLayout());
        isi.setOpaque(false);
        isi.setBorder(new EmptyBorder(24, 36, 24, 36));
        JLabel kepala = new JLabel("Menu Cepat");
        kepala.setFont(kepala.getFont().deriveFont(Font.BOLD, 16f));
        kepala.setForeground(Tema.TEKS);
        kepala.setBorder(new EmptyBorder(0, 0, 14, 0));
        isi.add(kepala, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 3, 18, 18));
        grid.setOpaque(false);
        for (Pintasan p : daftar) grid.add(new Kartu(p));
        JPanel pembungkus = new JPanel(new BorderLayout());
        pembungkus.setOpaque(false);
        pembungkus.add(grid, BorderLayout.NORTH);
        isi.add(pembungkus, BorderLayout.CENTER);
        add(isi, BorderLayout.CENTER);
    }

    private static class Kartu extends JPanel {
        private boolean hover;
        private final boolean aktif;

        Kartu(Pintasan p) {
            aktif = p.item.isEnabled() && (p.induk == null || p.induk.isEnabled());
            setOpaque(false);
            setLayout(new BorderLayout(14, 0));
            setBorder(new EmptyBorder(18, 18, 18, 18));
            setPreferredSize(new Dimension(220, 92));

            JLabel ikon = new JLabel(p.judul.substring(0, 1).toUpperCase(), SwingConstants.CENTER) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(aktif ? p.warna : new Color(0xCBD5E1));
                    g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            ikon.setFont(ikon.getFont().deriveFont(Font.BOLD, 18f));
            ikon.setForeground(Color.WHITE);
            ikon.setPreferredSize(new Dimension(44, 44));
            JPanel kiri = new JPanel(new GridBagLayout());
            kiri.setOpaque(false);
            kiri.add(ikon);
            add(kiri, BorderLayout.WEST);

            JLabel j = new JLabel(p.judul);
            j.setFont(j.getFont().deriveFont(Font.BOLD, 14f));
            j.setForeground(aktif ? Tema.TEKS : Tema.TEKS_REDUP);
            JLabel d = new JLabel(aktif ? p.deskripsi : "Tidak tersedia untuk peran Anda");
            d.setFont(d.getFont().deriveFont(12f));
            d.setForeground(Tema.TEKS_REDUP);
            JPanel tengah = new JPanel(new GridLayout(2, 1, 0, 2));
            tengah.setOpaque(false);
            tengah.add(j); tengah.add(d);
            add(tengah, BorderLayout.CENTER);

            if (aktif) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    @Override public void mouseExited(MouseEvent e) { hover = false; repaint(); }
                    @Override public void mouseClicked(MouseEvent e) { p.item.doClick(); }
                });
            }
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hover ? new Color(0xEFF6FF) : Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            g2.setColor(hover ? Tema.PRIMER : Tema.GARIS);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
