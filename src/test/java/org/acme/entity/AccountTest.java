package org.acme.entity;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.quarkus.test.vertx.UniAsserterInterceptor;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.acme.CatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Supplier;

@QuarkusTest
class AccountTest {

    @BeforeEach
    @RunOnVertxContext
    void before(UniAsserter asserter) {
        asserter = new UniAsserterInterceptor(asserter) {
            @Override
            protected <T> Supplier<Uni<T>> transformUni(Supplier<Uni<T>> uniSupplier) {
                return () -> Panache.withTransaction(uniSupplier);
            }
        };

        asserter.execute(() -> new Account("lin", BigDecimal.valueOf(1000)).persist());
        asserter.execute(() -> new Account("yj", BigDecimal.valueOf(1000)).persist());
    }

    @Test
    @RunOnVertxContext
    void transactionMoney(UniAsserter asserter) {
        asserter = new UniAsserterInterceptor(asserter) {
            @Override
            protected <T> Supplier<Uni<T>> transformUni(Supplier<Uni<T>> uniSupplier) {
                return () -> Panache.withTransaction(uniSupplier);
            }
        };
        Account lin = new Account();
        lin.id = 1L;
        Account yj = new Account();
        yj.id = 3L;
        asserter.assertThat(() -> Account.transactionMoney(lin, yj), flag -> Assertions.assertEquals(true, flag));
    }

    @Inject
    CatRepository catRepository;


    @Test
    @RunOnVertxContext
    void transactionMoney2(UniAsserter asserter) {
        asserter = new UniAsserterInterceptor(asserter) {
            @Override
            protected <T> Supplier<Uni<T>> transformUni(Supplier<Uni<T>> uniSupplier) {
                return () -> Panache.withTransaction(uniSupplier);
            }
        };
        Account lin = new Account();
        lin.id = 1L;
        Account yj = new Account();
        yj.id = 3L;
        catRepository.update(lin,yj).subscribe().with(System.out::println);
    }
}