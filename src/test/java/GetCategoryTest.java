import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import ru.geekbrains.java4.lesson6.db.model.Categories;
import ru.geekbrains.java4.lesson6.db.model.Products;
import service.CategoryService;
import utils.RetrofitUtils;

import dto.GetCategoryResponse;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetCategoryTest {
    static CategoryService categoryService;
    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    //@SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {
        Response<GetCategoryResponse> response = null;
        try {
            response = categoryService.getCategory(1).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        //DB
        try {
            Categories categories = Main.getCategoryById(1);
            assertThat(categories.getId() == 1, CoreMatchers.is(true));
        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

}

