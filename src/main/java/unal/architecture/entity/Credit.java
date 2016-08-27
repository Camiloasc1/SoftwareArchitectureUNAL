package unal.architecture.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Credit.findAll", query = "Select c from Credit c")
})
public class Credit {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private java.sql.Date date;
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
