import com.github.javafaker.Faker;
import dto.Product;
import lombok.SneakyThrows;
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
import java.util.ArrayList;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;

public class GetProductTest {
    static ProductService productService;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @Test
    void getProductTest() {
        // ToDo Service Get Product return 500 alwayse! Need to be fixed on Server
        Response<ArrayList<Product>> response = null;
        try {
            response = productService.getProduct().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}