package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.data.AdresDAO;
import nl.hu.dp.ovchip.data.AdresDAOHibernate;
import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;
import nl.hu.dp.ovchip.data.ReizigerDAO;
import nl.hu.dp.ovchip.data.ReizigerDAOHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.sql.SQLOutput;
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
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(String[] args) throws SQLException {
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate(factory);
        AdresDAO adresDAO = new AdresDAOHibernate(factory);

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

        //Wijzigingen weer terugzetten na tests
        adresDAO.deleteAdres(adres);
        reizigerDAO.deleteReiziger(Kasper);
        reizigerDAO.deleteReiziger(Jan);
    }
}