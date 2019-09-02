package fr.OutputInfo;

import fr.Customs.Customs;
import fr.Movement.Movement;

import javax.persistence.*;

@Entity
@Table(name = "output_info", schema = "public", catalog = "rapidcargo_db")
public class OutputInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_customs_doc_type")
    private Customs customsDoc;

    @Column(name = "customs_doc_ref")
    private Long customsDocRef;

    @OneToOne
    @JoinColumn(name = "fk_input")
    private Movement inputMovement;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customs getCustomsDoc() {
        return customsDoc;
    }

    public void setCustomsDoc(Customs customsDoc) {
        this.customsDoc = customsDoc;
    }

    public Long getCustomsDocRef() {
        return customsDocRef;
    }

    public void setCustomsDocRef(Long customsDocRef) {
        this.customsDocRef = customsDocRef;
    }

    public Movement getInputMovement() {
        return inputMovement;
    }

    public void setInputMovement(Movement inputMovement) {
        this.inputMovement = inputMovement;
    }
}
