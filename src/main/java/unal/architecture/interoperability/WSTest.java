package unal.architecture.interoperability;

import unal.architecture.interoperability.client.ProductDTO;
import unal.architecture.interoperability.client.ProductService;
import unal.architecture.interoperability.client.ProductServiceService;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.BindingProvider;
import java.util.List;

@Stateless
@Path("wstest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WSTest {

//    static {
//        java.net.Authenticator.setDefault(new java.net.Authenticator() {
//
//            @Override
//            protected java.net.PasswordAuthentication getPasswordAuthentication() {
//                return new java.net.PasswordAuthentication("admin", "admin".toCharArray());
//            }
//        });
//    }

    @GET
    public List<ProductDTO> test() {
        ProductServiceService service = new ProductServiceService();
        ProductService productService = service.getProductServicePort();
        ((BindingProvider) productService).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "admin");
        ((BindingProvider) productService).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "admin");

        return productService.getProducts();
    }
}
