/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TET
 */
@WebServlet(name = "Conexion", urlPatterns = {"/Conexion"})
public class Conexion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            Connection conexion = null;
            java.sql.Statement sentencia = null;
            ResultSet resultado;
            
           
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection("jdbc:mysql://localhost/Tienda", "root", "n0m3l0");
                sentencia = conexion.createStatement();
                System.out.println("Se ha conectado a la base de datos");
            }
            catch(SQLException e){
                System.out.println("No se conecto a la base de datos");
            }
            
            String Nombre = request.getParameter("Nombre");
            String Apellido = request.getParameter("Apellido");
            resultado = sentencia.executeQuery("select * from usuarios where Nombre = '"+Nombre+"' and Apellido = '"+Apellido+"'");
            int contador = 0;
            while(resultado.next()){
                contador++;
            }
            if(contador == 1){
                HttpSession sesion = request.getSession(true);
                sesion.setAttribute(Nombre, out);
                sesion.setAttribute(Apellido, out);
                response.sendRedirect("index.jsp");
            }
            else{
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Login</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<script>alert('Datos incorrectos')</script>");
                out.println("<script>window.location='index.html'</script>");
                out.println("</body>");
                out.println("</html>"); 
                HttpSession sesion = request.getSession(false);
            }
        }
        catch(Exception e){
            System.out.println("No se conecto");
        }

    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}