package fr.Movement;

import fr.Customs.Customs;
import fr.MerchandiseInfo.MerchandiseInfo;
import fr.OutputInfo.OutputInfo;
import fr.User.User;
import fr.Warehouse.Warehouse;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movement", schema = "public", catalog = "rapidcargo_db")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;
    @Column(name = "realized_date", columnDefinition = "TIMESTAMP")
    private LocalDate realizedDate;
    @ManyToOne
    @JoinColumn(name = "declaration_place")
    private Warehouse declarationPlace;
    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "fk_customs")
    private Customs customs;
    @OneToOne
    @JoinColumn(name = "fk_merchandise_info")
    private MerchandiseInfo merchandiseInfo;
    @Column(name = "type")
    private String type;
    @ManyToOne
    @JoinColumn(name = "fk_original_warehouse")
    private Warehouse originalWarehouse;
    @OneToOne
    @JoinColumn(name = "fk_output_info")
    private OutputInfo outputInfo;
    @ManyToOne
    @JoinColumn(name = "fk_destination_warehouse")
    private Warehouse destinationWarehouse;

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

    public LocalDate getRealizedDate() {
        return realizedDate;
    }

    public void setRealizedDate(LocalDate realizedDate) {
        this.realizedDate = realizedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customs getCustoms() {
        return customs;
    }

    public void setCustoms(Customs customs) {
        this.customs = customs;
    }

    public MerchandiseInfo getMerchandiseInfo() {
        return merchandiseInfo;
    }

    public void setMerchandiseInfo(MerchandiseInfo merchandiseInfo) {
        this.merchandiseInfo = merchandiseInfo;
    }

    public Warehouse getDeclarationPlace() {
        return declarationPlace;
    }

    public void setDeclarationPlace(Warehouse declarationPlace) {
        this.declarationPlace = declarationPlace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Warehouse getOriginalWarehouse() {
        return originalWarehouse;
    }

    public void setOriginalWarehouse(Warehouse originalWarehouse) {
        this.originalWarehouse = originalWarehouse;
    }

    public OutputInfo getOutputInfo() {
        return outputInfo;
    }

    public void setOutputInfo(OutputInfo outputInfo) {
        this.outputInfo = outputInfo;
    }

    public Warehouse getDestinationWarehouse() {
        return destinationWarehouse;
    }

    public void setDestinationWarehouse(Warehouse destinationWarehouse) {
        this.destinationWarehouse = destinationWarehouse;
    }
}
