import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.dao.ProductsMapper;
import ru.geekbrains.java4.lesson6.db.model.Categories;
import ru.geekbrains.java4.lesson6.db.model.CategoriesExample;
import ru.geekbrains.java4.lesson6.db.model.Products;
import ru.geekbrains.java4.lesson6.db.model.ProductsExample;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {
    static String resource = "mybatisConfig.xml";
    static SqlSession session;
    public static void main(String[] args) throws IOException {
        SqlSessionFactory sqlSessionFactory;
        //NyBatis Configuration file
        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }
    public static Integer countNumberOfAllCategories() throws IOException {
        CategoriesMapper categoriesMapper = getCategoriesMapper(resource);
        CategoriesExample example = new CategoriesExample();
        return Math.toIntExact(categoriesMapper.countByExample(example));
    }
    public static CategoriesMapper getCategoriesMapper(String resource) throws IOException {
        InputStream inputStream  = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        //sqlSessionFactory.openSession();
        SqlSession session = sqlSessionFactory.openSession();
        return session.getMapper(CategoriesMapper.class);
    }
    public static ProductsMapper getProductsMapper(String resource) throws IOException {
        InputStream inputStream  = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
        return session.getMapper(ProductsMapper.class);
    }

    public static Products getProductById(Long id) throws IOException {
        ProductsMapper productsMapper = getProductsMapper(resource);
        Products res = productsMapper.selectByPrimaryKey(id);
        session.close();
        return res;
    }

    public static Categories getCategoryById(Integer id) throws IOException {
        CategoriesMapper categoriesMapper = getCategoriesMapper(resource);
        return categoriesMapper.selectByPrimaryKey(id);
    }
    public static List<Products> getProducts() throws IOException {
        ProductsMapper productsMapper = getProductsMapper(resource);
        ProductsExample productsExample = new ProductsExample();
        List<Products> res = productsMapper.selectByExample(productsExample);
        session.close();
        return res;
    }

    public static void deleteProductById(Long id) throws IOException {
        ProductsMapper productsMapper = getProductsMapper(resource);
        productsMapper.deleteByPrimaryKey(id);
        session.commit();
        session.close();
    }

    public static Products updateProduct(Products record) throws IOException {
        ProductsMapper productsMapper = getProductsMapper(resource);
       int  res = productsMapper.updateByPrimaryKey(record);
       session.commit();
       session.close();
        return record;
    }
}
