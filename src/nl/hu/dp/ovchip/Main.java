package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.domein.Reiziger;
import nl.hu.dp.ovchip.domein.ReizigerDAO;
import nl.hu.dp.ovchip.domein.ReizigerDAOHibernate;
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
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(String[] args) throws SQLException {
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate(factory);

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

        //Wijzigingen weer terugzetten
        reizigerDAO.deleteReiziger(Kasper);
        reizigerDAO.deleteReiziger(Jan);
    }
}