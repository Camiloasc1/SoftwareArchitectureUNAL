
package unal.architecture.interoperability.client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ProductService", targetNamespace = "http://service.architecture.unal/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ProductService {


    /**
     * 
     * @param id
     * @return
     *     returns unal.architecture.interoperability.client.ProductDTO
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProduct", targetNamespace = "http://service.architecture.unal/", className = "unal.architecture.interoperability.client.GetProduct")
    @ResponseWrapper(localName = "getProductResponse", targetNamespace = "http://service.architecture.unal/", className = "unal.architecture.interoperability.client.GetProductResponse")
    public ProductDTO getProduct(
        @WebParam(name = "id", targetNamespace = "")
        long id);

    /**
     * 
     * @param amount
     * @param id
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "orderProduct", targetNamespace = "http://service.architecture.unal/", className = "unal.architecture.interoperability.client.OrderProduct")
    @ResponseWrapper(localName = "orderProductResponse", targetNamespace = "http://service.architecture.unal/", className = "unal.architecture.interoperability.client.OrderProductResponse")
    public boolean orderProduct(
        @WebParam(name = "id", targetNamespace = "")
        long id,
        @WebParam(name = "amount", targetNamespace = "")
        int amount);

    /**
     * 
     * @return
     *     returns java.util.List<unal.architecture.interoperability.client.ProductDTO>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProducts", targetNamespace = "http://service.architecture.unal/", className = "unal.architecture.interoperability.client.GetProducts")
    @ResponseWrapper(localName = "getProductsResponse", targetNamespace = "http://service.architecture.unal/", className = "unal.architecture.interoperability.client.GetProductsResponse")
    public List<ProductDTO> getProducts();

}
