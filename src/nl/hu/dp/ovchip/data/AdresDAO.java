package nl.hu.dp.ovchip.data;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;

import java.util.List;

public interface AdresDAO {
    boolean saveAdres(Adres adres);
    boolean updateAdres(Adres adres);
    boolean deleteAdres(Adres adres);
    Adres findById(int id);
    Adres findByReiziger(Reiziger reiziger);
    List<Adres> findAll();
}
