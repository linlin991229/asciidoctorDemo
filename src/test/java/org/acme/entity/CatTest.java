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

import java.time.LocalDateTime;
import java.util.function.Supplier;

@QuarkusTest
class CatTest {


    @BeforeEach
    @RunOnVertxContext
    public void before(UniAsserter asserter) {
        asserter = new UniAsserterInterceptor(asserter) {
            @Override
            protected <T> Supplier<Uni<T>> transformUni(Supplier<Uni<T>> uniSupplier) {
                return () -> Panache.withTransaction(uniSupplier);
            }
        };
        Person person = new Person("linlin", LocalDateTime.now());
        Cat cat = new Cat("wx", person);

        asserter.assertThat(() -> Panache.withTransaction(person::<Person>persist),
                entity -> Assertions.assertEquals("linlin", entity.getName()));
        asserter.assertThat(() -> Panache.withTransaction(cat::persist),
                entity -> Assertions.assertEquals(cat, entity));
    }

    @Inject
    CatRepository catRepository;

    @Test
    @RunOnVertxContext
    void findById(UniAsserter asserter) {
        asserter.assertThat(() -> Panache.withSession(() -> Cat.findById(1L)),
                System.out::println);
        asserter.assertThat(() -> Panache.withSession(() -> catRepository.findByName(1L)),
                list -> System.out.println("list = " + list));
    }

    @Test
    @RunOnVertxContext
    void find(UniAsserter asserter) {
        // 通过猫查人正常
        asserter.assertThat(() -> Panache.withSession(() -> Cat.findById(1L)),
                System.out::println);
    }

    @Test
    @RunOnVertxContext
    void findByPerson(UniAsserter asserter) {
        Person person = new Person();
        person.id = 1L;
        person.setName("lin");
        asserter.assertThat(() -> Panache.withSession(() -> Cat.findByPerson(person)),
                entity -> System.out.println("findByPerson==>" + entity));
    }

    @Test
    @RunOnVertxContext
    void findCat(UniAsserter asserter) {
        asserter = new UniAsserterInterceptor(asserter) {
            @Override
            protected <T> Supplier<Uni<T>> transformUni(Supplier<Uni<T>> uniSupplier) {
                return () -> Panache.withTransaction(uniSupplier);
            }
        };

        asserter.assertThat(Cat::findCat,
                cat -> System.out.println("cat = " + cat));

    }

    @Test
    @RunOnVertxContext
    void findByPersonResultDTO(UniAsserter asserter) {
        Person person = new Person();
        person.id = 1L;
        person.setName("lin");
        asserter.assertThat(() -> Panache.withTransaction(() -> Cat.findByPersonResultDTO(person)),
                entity -> System.out.println("findByPersonResultDTO==>" + entity));
    }

    @Test
    @RunOnVertxContext
    void findByPersonResultRecordDTO(UniAsserter asserter) {
        Person person = new Person();
        person.id = 1L;
        person.setName("lin");
        asserter.assertThat(() -> Panache.withTransaction(() -> Cat.findByPersonResultRecordDTO(person)),
                entity -> entity.forEach(item -> System.out.println("item = " + item)));
    }
}