package fr.Movement;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "movement", schema = "public", catalog = "rapidcargo_db")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "realized_date")
    private LocalDateTime realizedDate;
    @Column(name = "declaration_place")
    private String declarationPlace;
    @Column(name = "warehouse_code")
    private String warehouseCode;
    @Column(name = "warehouse_name")
    private String warehouseName;
    @Column(name = "customs_status")
    private String customsStatus;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getRealizedDate() {
        return realizedDate;
    }

    public void setRealizedDate(LocalDateTime realizedDate) {
        this.realizedDate = realizedDate;
    }

    public String getDeclarationPlace() {
        return declarationPlace;
    }

    public void setDeclarationPlace(String declarationPlace) {
        this.declarationPlace = declarationPlace;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getCustomsStatus() {
        return customsStatus;
    }

    public void setCustomsStatus(String customsStatus) {
        this.customsStatus = customsStatus;
    }

}
