package unal.architecture.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Credit.findAll", query = "Select c from Credit c")
})
public class Credit {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;
    @Column(nullable = false)
    private float interest;
    @Column(nullable = false)
    private boolean paid;

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

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
