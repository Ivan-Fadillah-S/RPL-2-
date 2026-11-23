package config;

import java.awt.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.*;

/**
 * Pengatur tampilan (tema) seluruh aplikasi. HANYA mengubah visual,
 * tidak menyentuh logika bisnis / database.
 *
 * Pemakaian:
 *   - Tema.pasang()            -> sekali saja di main() sebelum form tampil
 *   - Tema.percantik(this, "") -> satu baris di constructor tiap form, setelah initComponents()
 */
public final class Tema {

    public static final Color PRIMER   = new Color(0x2563EB);
    public static final Color PRIMER_GELAP = new Color(0x1E40AF);
    public static final Color BAHAYA   = new Color(0xDC2626);
    public static final Color NETRAL   = new Color(0xE2E8F0);
    public static final Color LATAR    = new Color(0xF1F5F9);
    public static final Color KARTU    = Color.WHITE;
    public static final Color GARIS    = new Color(0xE2E8F0);
    public static final Color TEKS     = new Color(0x0F172A);
    public static final Color TEKS_REDUP = new Color(0x64748B);
    public static final Color HEADER_TABEL = new Color(0x1E293B);
    public static final Color BARIS_GANJIL = new Color(0xF8FAFC);
    public static final Color BARIS_PILIH  = new Color(0xDBEAFE);

    private Tema() {}

    /** Pasang look and feel modern (FlatLaf). Bila library belum ada, pakai Nimbus. */
    public static void pasang() {
        boolean flat = false;
        try {
            Class<?> c = Class.forName("com.formdev.flatlaf.FlatLaf");
            c.getMethod("setGlobalExtraDefaults", java.util.Map.class)
             .invoke(null, Collections.singletonMap("@accentColor", "#2563EB"));
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
            flat = true;
        } catch (Throwable ignored) { }

        if (!flat) {
            try {
                UIManager.put("nimbusBase", new Color(0x1E3A8A));
                UIManager.put("nimbusFocus", PRIMER);
                UIManager.put("nimbusSelectionBackground", PRIMER);
                for (UIManager.LookAndFeelInfo i : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(i.getName())) { UIManager.setLookAndFeel(i.getClassName()); break; }
                }
            } catch (Exception ignored) { }
        }

        // Font seragam (13 pt) untuk semua komponen
        ArrayList<Object> keys = Collections.list(UIManager.getDefaults().keys());
        for (Object k : keys) {
            Object v = UIManager.get(k);
            if (v instanceof Font f) {
                UIManager.put(k, new FontUIResource(f.deriveFont(13f)));
            }
        }
        UIManager.put("TableHeader.font", new FontUIResource(
                UIManager.getFont("TableHeader.font").deriveFont(Font.BOLD, 13f)));

        // Properti khusus FlatLaf (diabaikan bila bukan FlatLaf)
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("PasswordField.showRevealButton", true);
        UIManager.put("Table.selectionBackground", BARIS_PILIH);
        UIManager.put("Table.selectionForeground", TEKS);
        UIManager.put("MenuItem.selectionBackground", BARIS_PILIH);
        UIManager.put("MenuItem.selectionForeground", TEKS);
        UIManager.put("Menu.selectionBackground", BARIS_PILIH);
        UIManager.put("Menu.selectionForeground", TEKS);
    }

    /** Percantik satu jendela beserta seluruh isinya. */
    public static void percantik(JFrame f, String judul) {
        if (judul != null && !judul.isEmpty()) f.setTitle(judul + " - ERP Toko");
        f.getContentPane().setBackground(LATAR);
        percantik(f.getContentPane());
        JMenuBar mb = f.getJMenuBar();
        if (mb != null) {
            mb.setBorder(new MatteBorder(0, 0, 1, 0, GARIS));
            mb.setBackground(Color.WHITE);
        }
        f.pack();
    }

    public static void percantik(Container c) {
        for (Component k : c.getComponents()) {
            if (k instanceof JScrollPane sp) {
                sp.setBorder(new LineBorder(GARIS));
                sp.getViewport().setBackground(Color.WHITE);
            } else if (k instanceof JPanel p) {
                p.setBackground(LATAR);
            } else if (k instanceof JTable t) {
                gayaTabel(t);
            } else if (k instanceof JButton b) {
                gayaTombol(b);
            } else if (k instanceof JPasswordField || k instanceof JTextField) {
                ((javax.swing.text.JTextComponent) k).setMargin(new Insets(5, 8, 5, 8));
            } else if (k instanceof JLabel l) {
                gayaLabel(l);
            }
            if (k instanceof Container cc) percantik(cc);
        }
    }

    private static void gayaLabel(JLabel l) {
        Font f = l.getFont();
        if (f.getSize() >= 16) {          // judul halaman
            l.setFont(f.deriveFont(Font.BOLD, 22f));
            l.setForeground(TEKS);
        } else {
            l.setForeground(new Color(0x334155));
        }
    }

    /** Label total harga (dipanggil manual dari form transaksi). */
    public static void gayaTotal(JLabel l) {
        l.setFont(l.getFont().deriveFont(Font.BOLD, 26f));
        l.setForeground(PRIMER);
    }

    public static void gayaTombol(JButton b) {
        String t = b.getText() == null ? "" : b.getText().trim().toLowerCase();
        Color bg, fg = Color.WHITE, hover, tekan;
        if (t.equals("hapus") || t.equals("batal")) {
            bg = BAHAYA; hover = new Color(0xB91C1C); tekan = new Color(0x991B1B);
        } else if (t.equals("edit") || t.equals("reset") || t.equals("refresh")) {
            bg = NETRAL; fg = TEKS; hover = new Color(0xCBD5E1); tekan = new Color(0x94A3B8);
        } else {
            bg = PRIMER; hover = PRIMER_GELAP; tekan = new Color(0x1E3A8A);
        }
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(b.getFont().deriveFont(Font.BOLD, 13f));
        b.setMargin(new Insets(6, 16, 6, 16));
        b.putClientProperty("FlatLaf.style", "arc: 10; background: " + hex(bg)
                + "; foreground: " + hex(fg) + "; hoverBackground: " + hex(hover)
                + "; pressedBackground: " + hex(tekan) + "; borderWidth: 0; focusWidth: 0");
    }

    public static void gayaTabel(JTable t) {
        t.setRowHeight(30);
        t.setShowVerticalLines(false);
        t.setShowHorizontalLines(true);
        t.setGridColor(GARIS);
        t.setIntercellSpacing(new Dimension(0, 1));
        t.setFillsViewportHeight(true);
        t.setSelectionBackground(BARIS_PILIH);
        t.setSelectionForeground(TEKS);
        t.setFont(t.getFont().deriveFont(13f));

        DefaultTableCellRenderer isi = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable tb, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(tb, v, sel, false, r, c);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                setHorizontalAlignment(v instanceof Number ? SwingConstants.RIGHT : SwingConstants.LEFT);
                setForeground(TEKS);
                setBackground(sel ? BARIS_PILIH : (r % 2 == 0 ? Color.WHITE : BARIS_GANJIL));
                return this;
            }
        };
        for (Class<?> k : new Class<?>[]{Object.class, Number.class, Integer.class, Double.class, Long.class, Float.class}) {
            t.setDefaultRenderer(k, isi);
        }

        JTableHeader h = t.getTableHeader();
        if (h != null) {
            h.setReorderingAllowed(false);
            h.setPreferredSize(new Dimension(h.getPreferredSize().width, 34));
            h.setDefaultRenderer(new DefaultTableCellRenderer() {
                @Override public Component getTableCellRendererComponent(JTable tb, Object v,
                        boolean sel, boolean foc, int r, int c) {
                    super.getTableCellRendererComponent(tb, v, false, false, r, c);
                    setBackground(HEADER_TABEL);
                    setForeground(Color.WHITE);
                    setFont(getFont().deriveFont(Font.BOLD, 13f));
                    setBorder(new EmptyBorder(0, 10, 0, 10));
                    setHorizontalAlignment(SwingConstants.LEFT);
                    return this;
                }
            });
        }
    }

    private static String hex(Color c) {
        return String.format("#%06X", c.getRGB() & 0xFFFFFF);
    }
}
