package fr.InputMovement;

import fr.MerchandiseInfo.MerchandiseInfo;
import fr.Movement.Movement;

import javax.persistence.*;

@Entity
@Table(name = "input_movement", schema = "public", catalog = "rapidcargo_db")
public class InputMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "fk_movement")
    private Movement movement;

    @OneToOne
    @JoinColumn(name = "fk_merchandise_info")
    private MerchandiseInfo merchandiseInfo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public MerchandiseInfo getMerchandiseInfo() {
        return merchandiseInfo;
    }

    public void setMerchandiseInfo(MerchandiseInfo merchandiseInfo) {
        this.merchandiseInfo = merchandiseInfo;
    }
}
