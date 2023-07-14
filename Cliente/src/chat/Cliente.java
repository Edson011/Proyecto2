
package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class Cliente extends javax.swing.JFrame {
    
    /*static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;*/
     private Socket socket;
    

    
    public Cliente() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        
       
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        txtMensaje = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        chkEnviarArchivo = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Cliente");

        txtArea.setColumns(20);
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        txtMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeActionPerformed(evt);
            }
        });

        btnEnviar.setBackground(new java.awt.Color(0, 102, 102));
        btnEnviar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEnviar.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        chkEnviarArchivo.setText("Seleccionar Archivo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEnviar)))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chkEnviarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chkEnviarArchivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:
     /*   try{
            String msgout="";
            msgout=txtMensaje.getText().trim();
            dout.writeUTF(msgout);
            
            
        }catch(Exception e){
            
        }*/
       try{
            Socket socket = new Socket ("192.168.142.1",1212);
            
            
            DataOutputStream enviar_mensaje = new DataOutputStream(socket.getOutputStream());
            //enviar_mensaje.writeUTF(txtMensaje.getText()); 
           
           if (chkEnviarArchivo.isSelected()) {//---------------------
            // Abrir el diálogo de selección de archivo
               JFileChooser fileChooser = new JFileChooser();
               int seleccion = fileChooser.showOpenDialog(this);
            
               if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                
                // Enviar el nombre del archivo al servidor
                enviar_mensaje.writeUTF(archivo.getName());
                
                // Enviar el archivo al servidor
                FileInputStream fis = new FileInputStream(archivo);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    enviar_mensaje.write(buffer, 0, bytesRead);
                }
                fis.close();
                
                
            } else {
                 
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún archivo.");
               }
            } else {
            
            enviar_mensaje.writeUTF(txtMensaje.getText());
            
            }
            //------------------------------------------------------------------------
            txtMensaje.setText("");

            socket.close();
            //enviar_mensaje.close();
            //recibirMensaje.close();
             
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error : " +ex + " No se pudo realizar la conexión . ");
        }
    
      
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMensajeActionPerformed

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
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);
            }
        });
    
 //-----------------------------------------------------------------------------------------
    /*    try{
            s=new Socket("192.168.142.1",1212);
            
            din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            
            String msgin="";
            
            while(!msgin.equals("exit")){
                msgin=din.readUTF();
                
                Cliente instancia = new Cliente();
                instancia.txtArea.setText(instancia.txtArea.getText().trim() + "/n Server/c" + msgin);
                //txtArea.setText(txtArea.getText().trim()+"/n Server/c"+msgin);
                
               s.close();
            }
            
            
        }catch(Exception e){
            
        } */
//-----------------------------------------------------------------------------------------
    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JCheckBox chkEnviarArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables

public void recibirDatos() {
        try {
            // Crear flujo de entrada para recibir datos del servidor
            DataInputStream recibir_mensaje = new DataInputStream(socket.getInputStream());

            String nombreArchivo = recibir_mensaje.readUTF();
            
            if (nombreArchivo.isEmpty()) {
                // Es un mensaje de texto
                //String mensaje = recibir_mensaje.readUTF();
                String mensajes = recibir_mensaje.readUTF();
                txtArea.append("Cliente:  " + mensajes +"         ");
            
                Date fechaActual = new Date();
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
                String hora = formatoHora.format(fechaActual);
            
                String mensajeConHora = "[" + hora + "] "; 
                txtArea.append(mensajeConHora + "\n\n");
                
            } else {
                // Es un archivo
                FileOutputStream fos = new FileOutputStream("C:\\Users\\HP\\Desktop\\CAR. PARA PRUEBAS\\" + nombreArchivo);

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = recibir_mensaje.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                fos.close();
                
            }
            recibir_mensaje.close();
            
        } catch (Exception e) {
            
        }
    }    

}


