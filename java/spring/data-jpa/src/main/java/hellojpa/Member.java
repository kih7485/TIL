package hellojpa;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "name")
    private String username;

    public Member(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
