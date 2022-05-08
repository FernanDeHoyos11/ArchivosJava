/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_registro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Formatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fernan
 */
public class ventana extends javax.swing.JFrame {

    String barra = File.separator;
    String ubicacion = System.getProperty("user.dir") + barra + "medicos" + barra;
    File contenedor = new File(ubicacion);
    File medicos[] = contenedor.listFiles();

    String[] titulos = {"id", "Nombre", "Telefono", "Edad", "especializacion"};
    DefaultTableModel dtm = new DefaultTableModel(null, titulos);

    private void tabla() {
        for (int i = 0; i < medicos.length; i++) {
            File url = new File(ubicacion + medicos[i].getName());
            try {
                FileInputStream fis = new FileInputStream(url);
                Properties mostrar = new Properties();
                mostrar.load(fis);

                String filas[] = {medicos[i].getName().replace(".txt", ""),
                    mostrar.getProperty("nombre"), mostrar.getProperty("telefono"), mostrar.getProperty("edad"), mostrar.getProperty("especialisacion")
                };

                dtm.addRow(filas);
            } catch (Exception e) {
            }
        }
        jTable1.setModel(dtm);
    }

    private void actualizarTabla() {
        medicos = contenedor.listFiles();
        dtm.setRowCount(0);
        tabla();
    }

    public ventana() {

        initComponents();
        this.setLocationRelativeTo(null);
         
        tabla();

    }

    public void contar() {
        String ubicacion = System.getProperty("user.dir") + barra + "medicos" + barra;
        File contenedor = new File(ubicacion);
        File[] lista = contenedor.listFiles();
        int contar = lista.length;
        txtBuscar.setText(String.valueOf(contar));

    }

    public void MinMax() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            list.add(Integer.parseInt(jTable1.getValueAt(i, 3).toString()));

        }
        int max = Collections.max(list);
        txtMax.setText(String.valueOf(max));
    }

    public void crear() {
        String archivo = txtid.getText() + ".txt";
        File c_ubicacion = new File(ubicacion);
        File c_archivo = new File(ubicacion + archivo);

        if (txtid.getText().equals("")) {

        } else {
            try {
                if (c_archivo.exists()) {
                    JOptionPane.showMessageDialog(rootPane, "el archivo ya exixte");
                } else {
                    c_ubicacion.mkdirs();
                    java.util.Formatter crea = new java.util.Formatter(ubicacion + archivo);
                    crea.format("%s\r\n%s\r\n%s\r\n%s\r\n%s",
                            "id=" + txtid.getText(),
                            "nombre=" + txtnombre.getText(),
                            "telefono=" + txttelefono.getText(),
                            "edad=" + Integer.parseInt(txtedad.getText()),
                            "especialisacion=" + txtesp.getText());
                    crea.close();
                    JOptionPane.showMessageDialog(rootPane, "Archivo craedo");

                    medicos = contenedor.listFiles();

                    actualizarTabla();
                    txtid.setText("");
                    txtnombre.setText("");
                    txttelefono.setText("");
                    txtedad.setText("");
                    txtesp.setText("");

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Archivo no craedo\n" + e);
            }

        }
    }

    public void mostrar() {

        File url = new File(ubicacion + txtid.getText() + ".txt");

        if (txtid.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "indique ID para mostrar");
        } else {
            if (url.exists()) {
                try {
                    FileInputStream fis = new FileInputStream(url);
                    Properties mostrar = new Properties();
                    mostrar.load(fis);

                   
                    this.txtnombre.setText(mostrar.getProperty("nombre"));
                    this.txttelefono.setText(mostrar.getProperty("telefono"));
                    this.txtedad.setText(mostrar.getProperty("edad"));
                    this.txtesp.setText(mostrar.getProperty("especialisacion"));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "ERROE\n" + e);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "ESE REGISTRO NO ES VALIDO");
            }
        }
    }

    public void editar() {
        if (txtid.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "ingrese ID");

        } else {
            File url = new File(ubicacion + txtid.getText() + ".txt");

            if (url.exists()) {
                try {
                    FileWriter editar = new FileWriter(ubicacion + txtid.getText() + ".txt");

                    String id = "id=";
                    String nombre = "nombre=";
                    String telefono = "telefono=";
                    String edad = "edad=";
                    String especialisacion = "especialisacion=";

                    PrintWriter guardar = new PrintWriter(editar);

                    guardar.println(id + txtid.getText().trim());
                    guardar.println(nombre + txtnombre.getText().trim());
                    guardar.println(telefono + txttelefono.getText().trim());
                    guardar.println(edad + txtedad.getText().trim());
                    guardar.println(especialisacion + txtesp.getText().trim());

                    editar.close();
                    JOptionPane.showMessageDialog(rootPane, "modificacion exitosa");

                    actualizarTabla();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "error" + e);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "no existe");
            }
        }
    }

    public void eliminar() {
        File url = new File(ubicacion + txtid.getText() + ".txt");
        String btns[] = {"eliminar", "cancelar"};

        if (txtid.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "indique id de archivo a eliminar");
        } else {

            if (url.exists()) {

                try {
                    FileInputStream fis = new FileInputStream(url);
                    fis.close();

                    int seguro = JOptionPane.showOptionDialog(rootPane, "¿esta seguro de eliminar el registro?"
                            + txtid.getText(), "eliminar", 0, 0, null, btns, null);

                    if (seguro == JOptionPane.YES_OPTION) {
                        url.delete();
                        txtid.setText("");
                        txtnombre.setText("");
                        txttelefono.setText("");
                        txtedad.setText("");
                        txtesp.setText("");

                        JOptionPane.showMessageDialog(rootPane, "archivo eliminado");

                    }
                    if (seguro == JOptionPane.NO_OPTION) {

                    }
                    actualizarTabla();

                } catch (Exception e) {
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "archivo con ese ID no existe");
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtedad = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        btncrear = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        btnmostrar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtesp = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        txtpromedio = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        txtMax = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Edad:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Especialisacion:");

        jLabel4.setText("Telefono:");

        txtedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtedadActionPerformed(evt);
            }
        });

        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });

        txttelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttelefonoActionPerformed(evt);
            }
        });

        btncrear.setText("CREAR REGISTRO");
        btncrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearActionPerformed(evt);
            }
        });

        jLabel5.setText("Id:");

        txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidActionPerformed(evt);
            }
        });

        btnmostrar.setText("MOSTRAR");
        btnmostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmostrarActionPerformed(evt);
            }
        });

        btneditar.setText("EDITAR");
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        jButton1.setText("LIMPIAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btneliminar.setText("ELIMINAR");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txtesp.setText(" ");
        txtesp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtespActionPerformed(evt);
            }
        });

        btnBuscar.setText("Contar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        jButton2.setText("Promedio de edad");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("mayor edad");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btneliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpromedio, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btncrear, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnmostrar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtedad, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtesp, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(btneditar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnBuscar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMax, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(37, 37, 37))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtesp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btncrear)
                            .addComponent(btnmostrar)
                            .addComponent(btneditar)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btneliminar)
                            .addComponent(jButton2)
                            .addComponent(txtpromedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(txtMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtedadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtedadActionPerformed

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreActionPerformed

    private void txttelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttelefonoActionPerformed

    private void btncrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearActionPerformed
        this.crear();
    }//GEN-LAST:event_btncrearActionPerformed

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidActionPerformed

    private void btnmostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmostrarActionPerformed
        mostrar();
    }//GEN-LAST:event_btnmostrarActionPerformed

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (txtid.getText().equals("") && txtedad.getText().equals("")
                && txtnombre.getText().equals("") && txttelefono.getText().equals("")) {

            JOptionPane.showMessageDialog(rootPane, "no hay nada para limpiar");

        } else {
            txtid.setText("");
            txtedad.setText("");
            txtnombre.setText("");
            //txtedad.setText("");
            txttelefono.setText("");
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btneliminarActionPerformed

    private void txtespActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtespActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtespActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int seleccion = jTable1.rowAtPoint(evt.getPoint());
        txtid.setText(String.valueOf(jTable1.getValueAt(seleccion, 0)));
        txtnombre.setText(String.valueOf(jTable1.getValueAt(seleccion, 1)));
        txttelefono.setText(String.valueOf(jTable1.getValueAt(seleccion, 2)));
        txtedad.setText(String.valueOf(jTable1.getValueAt(seleccion, 3)));
        txtesp.setText(String.valueOf(jTable1.getValueAt(seleccion, 4)));
    }//GEN-LAST:event_jTable1MouseClicked

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        contar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int contar = jTable1.getRowCount();
        int suma = 0;
        for (int i = 0; i < contar; i++) {
            suma = suma + Integer.parseInt(jTable1.getValueAt(i, 3).toString());

        }

        int promedio = suma / contar;
        txtpromedio.setText(String.valueOf(promedio));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       MinMax();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btncrear;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnmostrar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtMax;
    private javax.swing.JTextField txtedad;
    private javax.swing.JTextField txtesp;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtpromedio;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
