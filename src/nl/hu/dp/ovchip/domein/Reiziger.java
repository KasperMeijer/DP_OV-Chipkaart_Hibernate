package nl.hu.dp.ovchip.domein;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="reiziger")
public class Reiziger {
    @Id
    @Column(name="reiziger_id")
    private int id;

    @Column(name="voorletters")
    private String voorletters;

    @Column(name="tussenvoegsel")
    private String tussenvoegsel;

    @Column(name="achternaam")
    private String achternaam;

    @Column(name="geboortedatum")
    private LocalDate geboortedatum;

    @OneToOne(mappedBy = "reiziger")
    private Adres adres;

    public Reiziger(){}

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String toString(){
        if(tussenvoegsel != null){
            return voorletters + " " + tussenvoegsel + " " + achternaam + " " + geboortedatum;
        } else {
            return voorletters + " " + achternaam + " " + geboortedatum;
        }
    }
}
