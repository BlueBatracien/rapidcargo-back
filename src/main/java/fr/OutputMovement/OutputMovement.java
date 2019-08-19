package fr.OutputMovement;

import javax.persistence.*;

@Entity
@Table(name = "output_movement", schema = "public", catalog = "rapidcargo_db")
public class OutputMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "document_type")
    private String documentType;
    @Column(name = "document_ref")
    private String documentRef;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentRef() {
        return documentRef;
    }

    public void setDocumentRef(String documentRef) {
        this.documentRef = documentRef;
    }

}
