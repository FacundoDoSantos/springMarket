package com.facundo.market.persistence;

import com.facundo.market.domain.Product;
import com.facundo.market.domain.repository.ProductRepository;
import com.facundo.market.persistence.crud.ProductoCrudRepository;
import com.facundo.market.persistence.entity.Producto;
import com.facundo.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {
    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll(){
        return mapper.toProducts((List<Producto>) productoCrudRepository.findAll());
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        return Optional.of(mapper.toProducts(productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId)));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return Optional.of(mapper.toProducts(productos.get()));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return Optional.of(mapper.toProduct(productoCrudRepository.findById(productId).get()));
    }

    @Override
    public Product save(Product product) {
        return mapper.toProduct(productoCrudRepository.save(mapper.toProducto(product)));
    }
    @Override
    public void delete(int idProducto){
        productoCrudRepository.deleteById(idProducto);
    }

}
