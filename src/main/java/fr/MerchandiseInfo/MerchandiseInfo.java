package fr.MerchandiseInfo;

import fr.RefrenceType.ReferenceType;

import javax.persistence.*;

@Entity
@Table(name = "merchandise_info", schema = "public", catalog = "rapidcargo_db")
public class MerchandiseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "fk_reference_type")
    private ReferenceType referenceType;
    @Column(name = "reference")
    private Long reference;
    @Column(name = "quantity")
    private Double quantity;
    @Column(name = "weight")
    private Double weight;
    @Column(name = "total_quantity")
    private Double totalQuantity;
    @Column(name = "total_weight")
    private Double totalWeight;
    @Column(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
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

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
