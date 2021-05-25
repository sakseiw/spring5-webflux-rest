package guru.springframework.spring5webfluxrest.controller;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    private final VendorRepository vendorRepository;


    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    public Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    public Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream).then();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/categories/{id}")
    public Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        return vendorRepository.findById(id)
                .flatMap(v -> {
                    Vendor vendorPatch = Vendor.builder().id(v.getId()).build();
                    boolean patch = false;
                    if(StringUtils.hasLength(vendor.getFirstname()) && !vendor.getFirstname().equals(v.getFirstname())) {
                        vendorPatch.setFirstname(vendor.getFirstname());
                        patch = true;
                    } else {
                        vendorPatch.setFirstname(v.getFirstname());
                    }

                    if(StringUtils.hasLength(vendor.getLastname()) && !vendor.getFirstname().equals(v.getLastname())) {
                        vendorPatch.setLastname(vendor.getLastname());
                        patch = true;
                    } else {
                        vendorPatch.setLastname(v.getLastname());
                    }

                    return patch? vendorRepository.save(vendorPatch): Mono.just(v);

                });
    }
}
