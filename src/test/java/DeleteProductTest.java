import com.github.javafaker.Faker;
import dto.Product;

import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import service.ProductService;
import utils.RetrofitUtils;

import java.io.IOException;


import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteProductTest {
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
    void DeleteCategoryTest() {
        Response<ResponseBody> response = null;
        try {
            response = productService.deleteProduct(id).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}
