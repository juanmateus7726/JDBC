/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author juanc
 */
public class CAlumnos {

    int codigo;
    String nombreAlumnos;
    String apellidoAlumno;
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreAlumnos() {
        return nombreAlumnos;
    }

    public void setNombreAlumnos(String nombreAlumnos) {
        this.nombreAlumnos = nombreAlumnos;
    }

    public String getApellidoAlumno() {
        return apellidoAlumno;
    }

    public void setApellidoAlumno(String apellidoAlumno) {
        this.apellidoAlumno = apellidoAlumno;
    }
    
    public void InsertarAlumno(JTextField paramNombres, JTextField paramApellidos){
        
        setNombreAlumnos(paramNombres.getText());
        setApellidoAlumno(paramApellidos.getText());
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "insert into Alumnos (nombres, apellidos) values (?,?);";
        
        try {
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, getNombreAlumnos());
            cs.setString(2, getApellidoAlumno());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se inserto correctamente el alumno");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se inserto correctamente el alumno, Error:" + e.toString());
        }
        
    }
    
    public void MostrarAlumno(JTable paramtbtablaalumnos) {
        
        CConexion objetoConexion = new CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        TableRowSorter<TableModel>OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        paramtbtablaalumnos.setRowSorter(OrdenarTabla);
        
        String sql="";
        
        modelo.addColumn("ID");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        
        paramtbtablaalumnos.setModel(modelo);
        
        sql="select * From Alumnos";
        
        String[]datos = new String [3];
        
        Statement st;
        
        try {
            
            st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                
                modelo.addRow(datos);
                
            }
            paramtbtablaalumnos.setModel(modelo);
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "No se pudo mostrar los registros: Error:" +e.toString());
            
        }
        
    }
    
    public void SeleccionarAlumno(JTable paramtbtablaalumnos, JTextField paramID, JTextField paramNombres, JTextField paramApellidos) {
        
        try {
            
            int fila = paramtbtablaalumnos.getSelectedRow();
            
            if (fila>=0) {
                paramID.setText((String) (paramtbtablaalumnos.getValueAt(fila, 0)));
                paramNombres.setText((String) (paramtbtablaalumnos.getValueAt(fila, 1)));
                paramApellidos.setText((String) (paramtbtablaalumnos.getValueAt(fila, 2)));
            }
            else{
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
        
    }
    
    public void ModificarAlumnos(JTextField paramCodigo, JTextField paramNombres, JTextField paramApellidos) {
        
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombreAlumnos(paramNombres.getText());
        setApellidoAlumno(paramApellidos.getText());
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "UPDATE Alumnos SET alumnos.nombres =?, alumnos.apellidos=? WHERE alumnos.id=?;";
        
        try {
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, getNombreAlumnos());
            cs.setString(2, getApellidoAlumno());
            cs.setInt(3, getCodigo());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Modificacion exitosa");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar alumno, error: " +e.toString());
        }
        
    }
    
    public void EliminarAlumnos(JTextField paramCodigo) {
                
                setCodigo(Integer.parseInt(paramCodigo.getText()));
                
                CAlumnos objetoAlumnos = new CAlumnos();
                
                CConexion objetoConexion = new CConexion();
                
                String consulta = "DELETE FROM Alumnos WHERE alumnos.id = ?;";
                
                try {
                    
                    CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
                    
                    cs.setInt(1, getCodigo());
                    
                    cs.execute();
                    
                    JOptionPane.showMessageDialog(null, "Se elimino correctamente el alumno.");
                    
                } catch (Exception e) {
                    
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar, Error:" +e.toString());

            }
            
        }
    
}
