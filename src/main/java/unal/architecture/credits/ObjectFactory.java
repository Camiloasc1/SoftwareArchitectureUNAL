
package unal.architecture.credits;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the unal.architecture.credits package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MakeAcquiredProductResponse_QNAME = new QName("http://Service.BusinessLogic/", "makeAcquiredProductResponse");
    private final static QName _MakeAcquiredProduct_QNAME = new QName("http://Service.BusinessLogic/", "makeAcquiredProduct");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: unal.architecture.credits
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MakeAcquiredProductResponse }
     * 
     */
    public MakeAcquiredProductResponse createMakeAcquiredProductResponse() {
        return new MakeAcquiredProductResponse();
    }

    /**
     * Create an instance of {@link MakeAcquiredProduct }
     * 
     */
    public MakeAcquiredProduct createMakeAcquiredProduct() {
        return new MakeAcquiredProduct();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeAcquiredProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service.BusinessLogic/", name = "makeAcquiredProductResponse")
    public JAXBElement<MakeAcquiredProductResponse> createMakeAcquiredProductResponse(MakeAcquiredProductResponse value) {
        return new JAXBElement<MakeAcquiredProductResponse>(_MakeAcquiredProductResponse_QNAME, MakeAcquiredProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeAcquiredProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service.BusinessLogic/", name = "makeAcquiredProduct")
    public JAXBElement<MakeAcquiredProduct> createMakeAcquiredProduct(MakeAcquiredProduct value) {
        return new JAXBElement<MakeAcquiredProduct>(_MakeAcquiredProduct_QNAME, MakeAcquiredProduct.class, null, value);
    }

}
