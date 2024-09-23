package nl.hu.dp.ovchip.data;

import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    boolean saveOVChipkaart(OVChipkaart ovChipkaart);
    boolean updateOVChipkaart(OVChipkaart ovChipkaart);
    boolean deleteOVChipkaart(OVChipkaart ovChipkaart);
    OVChipkaart findById(int id);
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    List<OVChipkaart> findAll();
}
