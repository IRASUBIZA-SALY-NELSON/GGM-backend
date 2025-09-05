package vector.StockManagement.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vector.StockManagement.model.CreateProductRequest;
import vector.StockManagement.model.PriceList;
import vector.StockManagement.model.PriceListItem;
import vector.StockManagement.model.Product;
import vector.StockManagement.repositories.PriceListItemRepository;
import vector.StockManagement.repositories.ProductRepository;
import vector.StockManagement.services.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PriceListItemRepository priceListItemRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);// add in the functionality to also delete the priceList item
    }

    @Override
    public Product update(Long id, Product product) {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing != null) {
            // Update fields that are provided
            if (product.getName() != null) {
                existing.setName(product.getName());
            }
            if (product.getDescription() != null) {
                existing.setDescription(product.getDescription());
            }
            if (product.getPrice() != null) {
                existing.setPrice(product.getPrice());
            }
            if (product.getUnit() != 0) {
                existing.setUnit(product.getUnit());
            }
            if (product.getSize() != null) {
                existing.setSize(product.getSize());
            }
            if (product.getCode() != null) {
                existing.setCode(product.getCode());
            }
            if (product.getCategory() != null) {
                existing.setCategory(product.getCategory());
            }
            if (product.getBarcode() != null) {
                existing.setBarcode(product.getBarcode());
            }
            if (product.getWeight() != null) {
                existing.setWeight(product.getWeight());
            }
            if (product.getImageUrl() != null) {
                existing.setImageUrl(product.getImageUrl());
            }
            if (product.getIsActive() != null) {
                existing.setIsActive(product.getIsActive());
            }
            if (product.getAttributes() != null && !product.getAttributes().isEmpty()) {
                existing.setAttributes(product.getAttributes());
            }
            
            return productRepository.save(existing);
        }
        return null;
    }
}