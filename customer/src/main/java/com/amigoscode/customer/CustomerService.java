package com.amigoscode.customer;

import com.amigoscode.clients.fraud1.FraudCheckResponse;
import com.amigoscode.clients.fraud1.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder().firstName(
                request.firstName()).lastName(request.lastName()).email(request.email()).build();
        customerRepository.saveAndFlush(customer);


        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

    }
}
