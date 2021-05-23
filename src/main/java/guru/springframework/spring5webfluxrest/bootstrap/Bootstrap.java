package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repository.CategoryRepository;
import guru.springframework.spring5webfluxrest.repository.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count().block() == 0) {
            loadCategories();
        }

        if(vendorRepository.count().block() == 0) {
            loadVendors();
        }


        log.info("Categories Loaded = " + categoryRepository.count().block() );
        log.info("Vendors Loaded = " + vendorRepository.count().block() );

    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder().firstname("Joe").lastname("Buck").build()).block();
        vendorRepository.save(Vendor.builder().firstname("Michael").lastname("Weston").build()).block();
        vendorRepository.save(Vendor.builder().firstname("Jessie").lastname("Waters").build()).block();
        vendorRepository.save(Vendor.builder().firstname("Bill").lastname("Nershi").build()).block();
        vendorRepository.save(Vendor.builder().firstname("Jimmy").lastname("Buffet").build()).block();
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().description("Fruits").build()).block();
        categoryRepository.save(Category.builder().description("Nuts").build()).block();
        categoryRepository.save(Category.builder().description("Breads").build()).block();
        categoryRepository.save(Category.builder().description("Meats").build()).block();
        categoryRepository.save(Category.builder().description("Eggs").build()).block();
    }
}
