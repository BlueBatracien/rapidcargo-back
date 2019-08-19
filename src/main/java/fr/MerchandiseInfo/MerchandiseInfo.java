package fr.MerchandiseInfo;

import javax.persistence.*;

@Entity
@Table(name = "merchandise_info", schema = "public", catalog = "rapidcargo_db")
public class MerchandiseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ref_type")
    private String refType;
    @Column(name = "reference")
    private Long reference;
    @Column(name = "quantity")
    private Double quantity;
    @Column(name = "weight")
    private Double weight;
    @Column(name = "ref_quantity")
    private Double refQuantity;
    @Column(name = "ref_weight")
    private Double refWeight;
    @Column(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getRefQuantity() {
        return refQuantity;
    }

    public void setRefQuantity(Double refQuantity) {
        this.refQuantity = refQuantity;
    }

    public Double getRefWeight() {
        return refWeight;
    }

    public void setRefWeight(Double refWeight) {
        this.refWeight = refWeight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
