package com.enigma.ezycamp.controller;

import com.enigma.ezycamp.dto.request.SearchRequest;
import com.enigma.ezycamp.dto.response.WebResponse;
import com.enigma.ezycamp.entity.Customer;
import com.enigma.ezycamp.security.AuthenticatedUser;
import com.enigma.ezycamp.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final AuthenticatedUser authenticatedUser;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or @authenticatedUser.hasCustomerId(#id)")
    @GetMapping(path = "/{id}")
    public ResponseEntity<WebResponse<Customer>> findCustomerById(@PathVariable String id){
        Customer customer = customerService.getCustomerById(id);
        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .statusCode(HttpStatus.OK.value()).message("Berhasil mendapatkan data customer")
                .data(customer).build();
        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<Customer>>> findAllCustomer(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "name", required = false) String name
    ){
        SearchRequest request = SearchRequest.builder().sortBy(sortBy).size(size)
                .page(page).direction(direction).name(name).build();
        Page<Customer> customers =
    }
}
