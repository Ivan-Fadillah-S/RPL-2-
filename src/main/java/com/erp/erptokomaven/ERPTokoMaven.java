/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.erp.erptokomaven;

/**
 *
 * @author Ivan
 */
public class ERPTokoMaven {

    public static void main(String[] args) {
        config.Tema.pasang(); // pasang tema tampilan
        java.awt.EventQueue.invokeLater(() -> new views.FormLogin().setVisible(true));
    }
}
