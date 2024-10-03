package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.data.*;
import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure()
                    .addAnnotatedClass(Reiziger.class)
                    .addAnnotatedClass(Adres.class)
                    .addAnnotatedClass(OVChipkaart.class)
                    .addAnnotatedClass(Product.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(String[] args) throws SQLException {
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate(factory);
        AdresDAO adresDAO = new AdresDAOHibernate(factory);
        OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOHibernate(factory);
        ProductDAO productDAO = new ProductDAOHibernate(factory);


        // Opdracht P2H
        System.out.println("[TEST] ReizigerDAOHibernate");
        System.out.println("Nieuwe reiziger wordt opgeslagen en geladen door findById");
        Reiziger Kasper = new Reiziger(45,"Kasper", null, "Meijer", LocalDate.of(2004, 1, 3));
        reizigerDAO.saveReiziger(Kasper);
        System.out.println(reizigerDAO.findById(45));

        System.out.println("Nieuwe reiziger maken, opslaan en weer verwijderen");
        Reiziger delete = new Reiziger(35,"Delete", null, "Mij", LocalDate.of(2004, 1, 3));
        reizigerDAO.saveReiziger(delete);
        System.out.println(reizigerDAO.findById(35));
        reizigerDAO.deleteReiziger(delete);
        System.out.println(reizigerDAO.findAll());

        System.out.println("Voorletter aanpassen");
        System.out.println(reizigerDAO.findById(45));
        Kasper.setVoorletters("K");
        reizigerDAO.updateReiziger(Kasper);
        System.out.println(reizigerDAO.findById(45));

        // Even extra object aanmaken voor het testen op geboortedatum
        Reiziger Jan = new Reiziger(21, "J", null, "Bos", LocalDate.of(2004, 1, 3));
        reizigerDAO.saveReiziger(Jan);

        System.out.println("Ophalen van reiziger door findByGbdatum");
        System.out.println(reizigerDAO.findByGbdatum(LocalDate.of(2004, 1, 3)));

        System.out.println("Alle Reizigers ophalen");
        System.out.println(reizigerDAO.findAll());

        // Opdracht P3H
        System.out.println();
        System.out.println("[TEST] AdresDAOHibernate");
        System.out.println("Nieuw adres opslaan en ophalen door findById");
        Adres adres = new Adres(10, "1234AB", "12", "Straat", "Plaats", Kasper);
        adresDAO.saveAdres(adres);
        System.out.println(adresDAO.findById(10));

        System.out.println();
        System.out.println("Adres updaten en ophalen door reiziger");
        adres.setPostcode("4321BA");
        adresDAO.updateAdres(adres);
        System.out.println(adresDAO.findByReiziger(Kasper));

        System.out.println();
        System.out.println("Adressen ophalen door findAll");
        System.out.println(adresDAO.findAll());

        // Opdracht P4H
        System.out.println();
        System.out.println("[TEST] OVChipkaartDAOHibernate");
        System.out.println("Nieuwe OVChipkaart opslaan en ophalen door findById");
        OVChipkaart ovChipkaart = new OVChipkaart(123456, LocalDate.of(2021, 1, 1), 1, 25.0, Kasper);
        ovChipkaartDAO.saveOVChipkaart(ovChipkaart);
        System.out.println(ovChipkaartDAO.findById(123456));

        System.out.println();
        System.out.println("OVChipkaart updaten en ophalen door reiziger");
        ovChipkaart.setSaldo(50.0);
        ovChipkaartDAO.updateOVChipkaart(ovChipkaart);
        System.out.println(ovChipkaartDAO.findByReiziger(Kasper));

        System.out.println();
        System.out.println("OVChipkaarten ophalen door findAll");
        System.out.println(ovChipkaartDAO.findAll());

        // Opdracht P5H
        System.out.println();
        System.out.println("[TEST] ProductDAOHibernate");
        System.out.println("Nieuw product opslaan, koppelen aan OVChipkaart en ophalen door findByOVChipkaart");
        Product product = new Product("Test", "Test", 25.0);
        ovChipkaart.addProduct(product);
        product.addOVChipkaart(ovChipkaart);
        productDAO.saveProduct(product);
        System.out.println(productDAO.findByOVChipkaart(ovChipkaart));

        System.out.println();
        System.out.println("Product updaten en ophalen door OVChipkaart");
        product.setPrijs(50.0);
        productDAO.updateProduct(product);
        System.out.println(productDAO.findByOVChipkaart(ovChipkaart));

        System.out.println();
        System.out.println("Producten ophalen door findAll");
        System.out.println(productDAO.findAll());

        //Wijzigingen weer terugzetten na alle tests
        product.removeOVChipkaart(ovChipkaart);
        ovChipkaart.removeProduct(product);
        productDAO.updateProduct(product);
        ovChipkaartDAO.updateOVChipkaart(ovChipkaart);
        productDAO.deleteProduct(product);

        ovChipkaartDAO.deleteOVChipkaart(ovChipkaart);
        adresDAO.deleteAdres(adres);
        reizigerDAO.deleteReiziger(Kasper);
        reizigerDAO.deleteReiziger(Jan);
    }
}