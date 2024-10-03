package nl.hu.dp.ovchip.data;

import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;

import java.util.List;

public interface ProductDAO {
    boolean saveProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(Product product);
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();
}
