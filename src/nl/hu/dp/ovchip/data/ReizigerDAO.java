package nl.hu.dp.ovchip.data;

import nl.hu.dp.ovchip.domein.Reiziger;

import java.time.LocalDate;
import java.util.List;

public interface ReizigerDAO {
     boolean saveReiziger(Reiziger reiziger);
     boolean updateReiziger(Reiziger reiziger);
     boolean deleteReiziger(Reiziger reiziger);
     Reiziger findById(int id);
     List<Reiziger> findByGbdatum(LocalDate date);
     List<Reiziger> findAll();
}
