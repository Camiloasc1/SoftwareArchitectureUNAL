package unal.architecture.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Sale.findAll", query = "Select s from Sale s"),
        @NamedQuery(name = "Sale.findByDate", query = "Select s from Sale s WHERE s.date >= :date1 AND s.date <= :date2"),
        @NamedQuery(name = "Sale.findAllBySeller", query = "Select s from Sale s where s.seller.id = :id")

})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sale {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;
    @Column(nullable = false)
    private String client;
    @ManyToOne(optional = false)
    private User seller;

    @OneToMany(mappedBy = "sale",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<SaleDetail> saleDetail;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public List<SaleDetail> getSaleDetail(){ return this.saleDetail; }

    public void setSaleDetail(List<SaleDetail> sailDetail){ this.saleDetail = sailDetail; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sale)) return false;

        Sale sale = (Sale) o;

        if (id != sale.id) return false;
        if (date != null ? !date.equals(sale.date) : sale.date != null) return false;
        if (client != null ? !client.equals(sale.client) : sale.client != null) return false;
        return seller != null ? seller.equals(sale.seller) : sale.seller == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (seller != null ? seller.hashCode() : 0);
        return result;
    }
}
