package org.acme.entity;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.quarkus.test.vertx.UniAsserterInterceptor;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@QuarkusTest
class PersonTest {

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

    @Test
    @RunOnVertxContext
    void persist(UniAsserter asserter) {
        Uni<PanacheEntityBase> person =
                Panache.withTransaction(() -> new Person("lin", LocalDateTime.now()).persist());
        person.subscribe().with(System.out::println);
    }

    @Test
    @RunOnVertxContext
    public void testFindByName(UniAsserter asserter) {
        Panache.withTransaction(() -> new Person("linlin", LocalDateTime.now()).persist())
                .subscribe().with(x ->
                        Panache.withTransaction(() -> Person.findByNameWayNameQuery("linlin")).subscribe().with(System.out::println)
                );
    }

    @Test
    @RunOnVertxContext
    public void findCatByPerson(UniAsserter asserter) {
        asserter.assertThat(() -> Panache.withSession(() -> Person.<Person>findById(1L)),
                System.out::println);
    }

    @Test
    @RunOnVertxContext
    public void findCatByPerson2(UniAsserter asserter){
//        asserter.assertThat(() -> Panache.withSession(() -> Person.find()),
//                System.out::println);
    }


}