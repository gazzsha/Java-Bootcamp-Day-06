package edu.school21.repositories;

import edu.school21.models.Product;
import edu.school21.numbers.exception.IllegalNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class
ProductsRepositoryJdbcTest {
 //  final List<Product> EXPECTED_FIND_ALL_PRODUCTS;
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1,"Milk",400);
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>() {
        {
            add(new Product(1,"Milk",400));
            add(new Product(2,"Chocolate",200));
            add(new Product(3,"Water",100));
            add(new Product(4,"Snake",499));
            add(new Product(5,"Bread",100));
        }
    };

    final Product EXPECTED_UPDATED_PRODUCT = new Product(1,"Milk",250);

    final Product EXPECTED_NEW_PRODUCT = new Product(6,"Tomato",175);
//    final Product[] EXPECTED_FIND_BY_ID_PRODUCTS = {new Product(2,"Chocolate",200),
//            (new Product(3, "Water",100))};
    private ProductRepository productRepository;


    @BeforeEach
    public void init() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productRepository = new ProductRepositoryJdbcImpl(dataSource);
    }



    @Test
    void checkUpdatedProduct() throws SQLException {
        Product product = productRepository.findById(1).get();
        product.setPrice(250);
        productRepository.update(product);
        assertEquals(EXPECTED_UPDATED_PRODUCT,productRepository.findById(1).get());
    }

//    Stream<Product> argsProviderFactory() throws SQLException {
//        return Stream.of(productRepository.findById(1).get());
//    }
//
//    @ParameterizedTest(name = "Find by Id {0}")
//    @MethodSource("argsProviderFactory")
//    public void checkFindById(Product product) throws SQLException {
//        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT,product);
//    }

    @Test
    public void checkFindById() throws SQLException {
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT,productRepository.findById(1).get());
    }

    @Test
    public void checkFindByNotExistId() throws SQLException {
        assertFalse(productRepository.findById(10).isPresent());
    }

    @Test
    void checkFindAll() throws SQLException {
        Assertions.assertIterableEquals(EXPECTED_FIND_ALL_PRODUCTS,productRepository.findAll());
    }

    @Test
    void checkDelete() throws SQLException {
        productRepository.delete(1);
        assertFalse(productRepository.findById(1).isPresent());
        assertEquals(4,productRepository.findAll().size());
    }

    @Test
    void testSaveInDataBase() throws SQLException {
        Product product = new Product();
        product.setName("Tomato");
        product.setPrice(175);
        productRepository.save(product);
        assertEquals(6,productRepository.findAll().size());
        assertTrue(productRepository.findById(6).isPresent());
        assertEquals(EXPECTED_NEW_PRODUCT,productRepository.findById(6).get());

    }



//    @ParameterizedTest(name = "Find by ID - {0}")
//    @ArgumentsSource(ProductsArgumentsProvider.class)
//    void testArgumentsSource(Product product) {
//        assertEquals(EXPECTED_FIND_BY_ID_PRODUCTS[0],product);
//    }
//
//    class ProductsArgumentsProvider implements ArgumentsProvider {
//        @Override
//        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
//            return Stream.of(
//                    Arguments.of(new Product(2,"Chocolate",200)),
//                    Arguments.of(new Product(3, "Water",100)),
//                    Arguments.of(new Product(5, "Bread",500))
//            );
//        }

}
