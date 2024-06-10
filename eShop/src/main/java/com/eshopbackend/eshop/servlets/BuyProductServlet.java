package com.eshopbackend.eshop.servlets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BuyProductServlet", urlPatterns = {"/BuyProductServlet"})
public class BuyProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String productId = request.getParameter("productId");
        int amountOfProducts = Integer.parseInt(request.getParameter("amountOfProducts"));
        UUID id = UUID.fromString(productId);
        String apiUrl = "http://localhost:8080/products/sell/" + id;
                
        try (PrintWriter out = response.getWriter()) {
            
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the JSON request body
            String jsonRequestBody = "{\"amountOfProducts\": " + amountOfProducts + "}";
            
            // Write JSON to the request body
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonRequestBody);
                wr.flush();
            }
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 204){
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Product bought</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Has comprado tu producto exitosamente!!</h1>");
                out.println("</body>");
                out.println("</html>");
            } else if (responseCode == 404){
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Product not found</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>El producto no fue encontrado</h1>");
                out.println("</body>");
                out.println("</html>");
            } else if (responseCode == 400){
                                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error when buying product</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>La operación no se pudo realizar debido a que el producto no tiene stock o la cantidad de productos que quieres comprar excede la cantidad en inventario disponible</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }   
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
