package org.acme;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.quarkus.test.vertx.UniAsserterInterceptor;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.acme.entity.Person;
import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Supplier;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("Hello from RESTEasy Reactive"));
    }

    @Inject
    Mutiny.SessionFactory sessionFactory;


    @Test
    @RunOnVertxContext
    public void testQuery(UniAsserter asserter) {
        asserter.assertThat(() -> sessionFactory.withSession(s -> s.createQuery(
                        "from Person g where g.name = :name").setParameter("name", "lin").getResultList()),
                list -> Assertions.assertEquals(list.size(), 0));
    }

    @Test
    @RunOnVertxContext
    public void testPanacheMocking(UniAsserter asserter) {
        Person person = new Person("name", LocalDateTime.now());
        Person person1 = new Person("lin", LocalDateTime.now());
        System.out.println(person);
        asserter.assertThat(() -> sessionFactory.withTransaction(session -> person.persist()),
                entity -> Assertions.assertEquals(entity, person)
        );
        asserter.assertThat(()->sessionFactory.withSession(session -> session.find(Person.class,1L)),
                entity->Assertions.assertEquals(person,entity)
                );
    }
    @Test
    public void test(){
        System.out.println(LocalDateTime.now());
        LocalDateTime ldt = LocalDateTime.now();
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
    }

    static class TransactionalUniAsserterInterceptor extends UniAsserterInterceptor {

        public TransactionalUniAsserterInterceptor(UniAsserter asserter) {
            super(asserter);
        }

        @Override
        protected <T> Supplier<Uni<T>> transformUni(Supplier<Uni<T>> uniSupplier) {
            // Assert/execute methods are invoked within a database transaction
            return () -> Panache.withTransaction(uniSupplier);
        }
    }
}