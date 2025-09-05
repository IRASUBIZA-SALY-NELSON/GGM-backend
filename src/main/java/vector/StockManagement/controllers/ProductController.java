package vector.StockManagement.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vector.StockManagement.model.CreateProductRequest;
import vector.StockManagement.model.Product;
import vector.StockManagement.model.Tenant;
import vector.StockManagement.model.User;
import vector.StockManagement.repositories.TenantRepository;
import vector.StockManagement.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController {

    private final ProductService productService;
    private final TenantRepository tenantRepository;

    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }


    @PostMapping
    @PreAuthorize("hasRole('SALES_MANAGER')")
    public Product create(@RequestBody Product product) {
        // Get current authenticated user and set their tenant
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // If user has no tenant, assign the default tenant (ID: 1)
        if (currentUser != null) {
            Tenant tenant = currentUser.getTenant();
            if (tenant == null) {
                // Get default tenant (the only one in your system)
                tenant = tenantRepository.findById(1L).orElse(null);
            }
            product.setTenant(tenant);
        }
        
        return productService.save(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SALES_MANAGER') or hasRole('STORE_MANAGER')")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Product existing = productService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SALES_MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}