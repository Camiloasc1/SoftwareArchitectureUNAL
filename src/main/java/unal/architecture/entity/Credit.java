package unal.architecture.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Credit.findAll", query = "Select c from Credit c")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credit {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;
    @Column(nullable = false)
    @Min(0)
    private float amount;
    @Column(nullable = false)
    @Min(1)
    @Max(48)
    private int numberOfPayments;
    @Column(nullable = false)
    private boolean paid;
    @Column(nullable = false)
    private String type;
    @ManyToOne(optional = false)
    private User user;


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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getNumberOfPayments() {
        return numberOfPayments;
    }

    public void setNumberOfPayments(int numberOfPayments) {
        this.numberOfPayments = numberOfPayments;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credit credit = (Credit) o;

        if (id != credit.id) return false;
        if (Float.compare(credit.amount, amount) != 0) return false;
        if (numberOfPayments != credit.numberOfPayments) return false;
        if (paid != credit.paid) return false;
        if (date != null ? !date.equals(credit.date) : credit.date != null) return false;
        if (type != null ? !type.equals(credit.type) : credit.type != null) return false;
        return user != null ? user.equals(credit.user) : credit.user == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (amount != +0.0f ? Float.floatToIntBits(amount) : 0);
        result = 31 * result + numberOfPayments;
        result = 31 * result + (paid ? 1 : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
