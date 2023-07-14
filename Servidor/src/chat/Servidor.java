
package chat;

        
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

 
public class Servidor extends javax.swing.JFrame implements Runnable{
   
   /* static ServerSocket ss;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;*/
   

    
    public Servidor() {
        initComponents();
        Thread hilo = new Thread(this);
        hilo.start();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        txtMensaje = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        chkEnviarArchivo = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 204, 255));
        setPreferredSize(new java.awt.Dimension(401, 320));
        getContentPane().setLayout(null);

        txtArea.setColumns(20);
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(18, 30, 350, 144);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Servidor");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(160, 0, 75, 22);

        txtMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeActionPerformed(evt);
            }
        });
        getContentPane().add(txtMensaje);
        txtMensaje.setBounds(16, 220, 260, 40);

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(290, 220, 80, 40);

        chkEnviarArchivo.setText("Seleccionar Archivo");
        getContentPane().add(chkEnviarArchivo);
        chkEnviarArchivo.setBounds(180, 180, 160, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMensajeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // String mensaje = "Hola cliente"; 

        try {
        Socket socket = new Socket("localhost", 1212); // Conexión al cliente
        DataOutputStream enviar_mensaje = new DataOutputStream(socket.getOutputStream());
        
        // Enviar mensaje al cliente
       // enviar_mensaje.writeUTF("");
       // enviar_mensaje.writeUTF(mensaje);
        
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
        
    
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
//----------------------------------------------------------------------------------------------
        
    /*    String msgin="";
        try{
            ss = new ServerSocket(1212);
            s  = ss.accept();
            
            din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            
            while(!msgin.equals("exit")){
                msgin=din.readUTF();
                
                Servidor instancia = new Servidor();
                instancia.txtArea.setText(instancia.txtArea.getText().trim() + "/n Server/c" + msgin);
                //txtArea.setText(txtArea.getText().trim()+"/n"+msgin);
                
            }
            
        }catch(Exception e){
            
        }*/
    //--------------------------------------------------------------------------------------------------------------
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkEnviarArchivo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables

  
    @Override
    public void run() {
        
        try{
            
            ServerSocket Servidor = new ServerSocket(1212);
            
            while (true){
            Socket misocket = Servidor.accept(); 
            //-----------------------------------------------------------------------------------
            DataInputStream recibir_mensaje = new DataInputStream(misocket.getInputStream()); 
            String nombreArchivo = recibir_mensaje.readUTF();//-----------
            // Verificar si el mensaje recibido es un archivo o un mensaje de texto
            if (nombreArchivo.isEmpty()) {
            // Es un mensaje de texto
            //---------------------------------------------------------------------------
            //DataInputStream recibir_mensaje = new DataInputStream(misocket.getInputStream()); 
            String mensajes = recibir_mensaje.readUTF();
            txtArea.append("Cliente:  " + mensajes +"         ");
            
            Date fechaActual = new Date();
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            String hora = formatoHora.format(fechaActual);
            
            String mensajeConHora = "[" + hora + "] "; 
            txtArea.append(mensajeConHora + "\n\n");
            
            } else {//----------------------------------------------------------------
            // Es un archivo
            FileOutputStream fos = new FileOutputStream("C:\\Users\\HP\\Desktop\\CAR. PARA PRUEBAS\\" + nombreArchivo);
        
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = recibir_mensaje.read(buffer)) != -1) {
               fos.write(buffer, 0, bytesRead);
            }
            
            fos.close();
            }   
            //txtMensaje.setText("");
             //--------------------------------------------------------------------------
            misocket.close();
            
        }
            
            
        }catch(Exception e){
            String errorMsg = "Error al mostrar los mensajes en el JTextArea: " + e.getMessage();
            txtArea.append(errorMsg);
            txtArea.append("\n");
          
        }
    }
    
    
}
