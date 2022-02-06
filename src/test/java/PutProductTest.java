import com.github.javafaker.Faker;
import dto.Product;

import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.java4.lesson6.db.model.Products;
import service.ProductService;
import utils.RetrofitUtils;

import java.io.IOException;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PutProductTest {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();

    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice(5555);

        Response<Product> response = null;
        try {
            response = productService.createProduct(product)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        id = response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    void updateProductInFoodCategoryTest() {
        Products productUpdate = new Products();
        productUpdate.setId((long) id);
        productUpdate.setTitle(product.getTitle());
        productUpdate.setCategory_id(1L);
        productUpdate.setPrice(9999);
        //DB
        try {
            Main.updateProduct(productUpdate);
            Products productNew = Main.getProductById((long) id);
            assertThat(productNew.getId() == id, CoreMatchers.is(true));
            assertThat(productNew.getPrice() == 9999, CoreMatchers.is(true));
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        //DB
        try {
            Main.deleteProductById((long) id);
            Products product = Main.getProductById((long) id);
            assertNull(product);
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }
}