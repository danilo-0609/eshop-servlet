package com.eshopbackend.eshop.servlets;

import com.eshopbackend.eshop.responses.ProductResponse;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetProductsByIdServlet", urlPatterns = {"/GetProductsByIdServlet"})
public class GetProductsByIdServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String productId = request.getParameter("productId");
        UUID id = UUID.fromString(productId);
        String apiUrl = "http://localhost:8080/products/get/" + id; 
        
        try (PrintWriter out = response.getWriter()) {
            
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) { // HTTP OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Gson gson = new Gson();
                ProductResponse productResponse = gson.fromJson(in, ProductResponse.class);
                
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                
                // Assuming the response is JSON, you can parse it as needed
                String productJson = content.toString();
                
                String stockStatus = "Con inventario";
                
                if (productResponse.getStockStatus() == "OUT_OF_STOCK"){
                    stockStatus = "Sin inventario";
                } 
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Product Details</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
                out.println("h1 { color: #333; }");
                out.println(".product-details { border: 1px solid #ccc; padding: 20px; border-radius: 5px; max-width: 600px; }");
                out.println(".product-details div { margin-bottom: 10px; }");
                out.println(".product-details label { font-weight: bold; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Product Details</h1>");
                out.println("<div class='product-details'>");
                out.println("<div><label>ID:</label> " + id + "</div>");
                out.println("<div><label>Nombre del vendedor:</label> " + productResponse.getSellerName() + "</div>");
                out.println("<div><label>Nombre del producto:</label> " + productResponse.getName() + "</div>");
                out.println("<div><label>Precio:</label> $" + productResponse.getPrice() + "</div>");
                out.println("<div><label>Descripcion:</label> " + productResponse.getDescription() + "</div>");
                out.println("<div><label>Tipo de producto:</label> " + productResponse.getProductType() + "</div>");
                out.println("<div><label>Cantidad en el inventario: </label> " + productResponse.getInStock() + "</div>");
                out.println("<div><label>Estado del inventario: </label> " + stockStatus + "</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else if (responseCode == 404) { // HTTP Not Found
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Product Not Found</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Product was not found</h1>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>An error occurred while retrieving the product</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
