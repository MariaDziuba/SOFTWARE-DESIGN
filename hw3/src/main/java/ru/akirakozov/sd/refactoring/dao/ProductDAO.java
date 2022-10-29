package ru.akirakozov.sd.refactoring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.akirakozov.sd.refactoring.models.Product;

import static ru.akirakozov.sd.refactoring.database.SQLiteDatabase.getConnection;

public class ProductDAO {
    public void insert(Product product) throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String query =
                    "INSERT INTO PRODUCT " +
                            "(NAME, PRICE) VALUES " +
                            "(\"" + product.getName() + "\"," + product.getPrice() + ")";
            statement.executeUpdate(query);
        }
    }

    public List<Product> getAll() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String query = "SELECT * FROM PRODUCT";
            ResultSet resultSet = statement.executeQuery(query);
            return parseProducts(resultSet);
        }
    }

    public Optional<Product> findMaxPrice() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String query = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(query);
            return parseProducts(resultSet).stream().findFirst();
        }
    }

    public Optional<Product> findMinPrice() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String query = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
            ResultSet resultSet = statement.executeQuery(query);
            return parseProducts(resultSet).stream().findFirst();
        }
    }

    public long getSum() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String query = "SELECT SUM(price) as sum FROM PRODUCT";
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.getLong("sum");
        }
    }

    public int getCount() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String query = "SELECT COUNT(*) as cnt FROM PRODUCT";
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.getInt("cnt");
        }
    }

    private List<Product> parseProducts(ResultSet resultSet) throws SQLException {
        List<Product> result = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int price  = resultSet.getInt("price");
            result.add(new Product(name, price));
        }
        return result;
    }
}