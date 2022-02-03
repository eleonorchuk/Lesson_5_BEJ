package service;
import dto.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;


public interface ProductService {
    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @GET("products")
    Call<ArrayList<Product>> getProduct();

    @PUT("products")
    Call<Product> updateProduct(@Body Product createProductRequest);

    @GET("products/{id}")
    Call<Product> getProductId(@Path("id") int id);
}
