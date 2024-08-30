package com.paulomlr.ecommerceSystem.services;

import com.paulomlr.ecommerceSystem.domain.Product;
import com.paulomlr.ecommerceSystem.domain.ProductSale;
import com.paulomlr.ecommerceSystem.domain.Sale;
import com.paulomlr.ecommerceSystem.domain.User;
import com.paulomlr.ecommerceSystem.domain.dto.productSale.ProductSaleRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.sale.SaleRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.sale.SaleResponseDTO;
import com.paulomlr.ecommerceSystem.domain.enums.ProductStatus;
import com.paulomlr.ecommerceSystem.domain.enums.SaleStatus;
import com.paulomlr.ecommerceSystem.repositories.ProductRepository;
import com.paulomlr.ecommerceSystem.repositories.SaleRepository;
import com.paulomlr.ecommerceSystem.repositories.UserRepository;
import com.paulomlr.ecommerceSystem.services.exceptions.InsufficientStockException;
import com.paulomlr.ecommerceSystem.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<SaleResponseDTO> findAll() {
        return saleRepository.findAll()
                .stream().map(SaleResponseDTO::new)
                .toList();
    }

    public SaleResponseDTO findById(UUID id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found. Id: " + id));
        return new SaleResponseDTO(sale);
    }

    public List<SaleResponseDTO> getSalesByUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Id " + id));

        return saleRepository.findByUser(user)
                .stream()
                .map(SaleResponseDTO::new)
                .toList();
    }

    @Transactional
    public Sale insert(SaleRequestDTO saleRequest) {
        User user = userRepository.findById(saleRequest.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Id: " + saleRequest.userId()));

        Set<ProductSale> productSales = saveSale(saleRequest.items(), saleRequest);

        Sale sale = new Sale(Instant.now(), user, productSales, SaleStatus.COMPLETED);
        productSales.forEach(productSale -> productSale.getId().setSale(sale));

        productRepository.saveAll(
                productSales.stream()
                        .map(ProductSale::getProduct)
                        .toList()
        );
        return saleRepository.save(sale);
    }

    public void delete(UUID id){
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found. Id: " + id));
        saleRepository.delete(sale);
    }

    public SaleResponseDTO update(UUID id, SaleRequestDTO obj) {
        return saleRepository.findById(id)
                .map(sale -> {
                    updateData(sale, obj);
                    Sale updateSale = saleRepository.save(sale);
                    return new SaleResponseDTO(updateSale);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found. Id: " + id));
    }

    private void updateData(Sale sale, SaleRequestDTO obj) {
        sale.getItems().clear();

        obj.items().forEach(itemDto -> {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found. Id: " + itemDto.productId()));

            ProductSale productSale = new ProductSale(product, sale, itemDto.quantity());
            sale.getItems().add(productSale);
        });

        sale.setSaleDate(Instant.now());
    }

    public void cancelSale(UUID id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found. Id: " + id));

        sale.setSaleStatus(SaleStatus.CANCELED);
        sale.setUser(null);
        saleRepository.save(sale);
    }

    public List<SaleResponseDTO> getSalesByDate(Instant date) {
        return saleRepository.findBySaleDate(date)
                .stream().map(SaleResponseDTO::new)
                .toList();
    }

    public List<SaleResponseDTO> getSalesByMonth(int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        Instant startInstant = startDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endInstant = endDate.atStartOfDay(ZoneOffset.UTC).toInstant();

        return saleRepository.findBySaleDateBetween(startInstant, endInstant)
                .stream()
                .map(SaleResponseDTO::new)
                .toList();
    }

    public List<SaleResponseDTO> getSalesThisWeek () {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfWeek = startOfWeek.plusDays(4).withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        Instant startInstant = startOfWeek.toInstant(ZoneOffset.UTC);
        Instant endInstant = endOfWeek.toInstant(ZoneOffset.UTC);

        return saleRepository.findBySaleDateBetween(startInstant, endInstant)
                .stream()
                .map(SaleResponseDTO::new)
                .toList();
    }

    private Set<ProductSale> saveSale(Set<ProductSaleRequestDTO> items, SaleRequestDTO saleRequest) {
        return saleRequest.items().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.productId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found. Id: "+ item.productId()));

                    if(item.quantity() > product.getStockQuantity()) {
                        throw new InsufficientStockException("Insufficient stock available for the requested item: " + product.getName());
                    }
                    product.setStockQuantity(product.getStockQuantity() - item.quantity());

                    if(product.getStockQuantity() == 0) {
                        product.setProductStatus(ProductStatus.INACTIVE);
                    }

                    return new ProductSale(product, item.quantity());
                })
                .collect(Collectors.toSet());
    }
}