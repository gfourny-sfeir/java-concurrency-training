package fr.concurrency.training.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.concurrency.training.model.Customer;
import fr.concurrency.training.repository.CustomerRepository;
import lombok.val;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

/**
 * @author gfourny
 */
class DBTest extends AbastractITSpring {

    //Valeur = spring.datasource.hikari.maximum-pool-size
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void shouldGetAllCustomers() {

        val customers = Instancio.ofList(Customer.class)
                .size(1_000)
                .set(field(Customer::getId), null)
                .generate(field(Customer::getEmail), gen -> gen.net().email())
                .create();

        //FIXME nombre de thread trop élevé, plus de connexion bdd dispo
        customers.stream()
                .map(customer -> CompletableFuture.supplyAsync(() -> customerRepository.save(customer), executorService))
                .toList()
                .forEach(CompletableFuture::join);

        //FIXME nombre de thread trop élevé, plus de connexion bdd dispo
        //customers.parallelStream().forEach(customer -> customerRepository.save(customer));

        val customerList = customerRepository.findAll();

        assertThat(customerList).isNotNull().isNotEmpty().hasSize(1_000);
    }
}