package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryJdbcImpl implements  ProductRepository {

    private final DataSource dataSource;

    public ProductRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> productList;
        try (Connection connection = dataSource.getConnection()) {
            productList = new ArrayList<>();
            final String SQLQuery = "select * from bootcamp.product";
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                productList.add(product);
            }
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Integer id) throws SQLException {
        Product product = null;
        try (Connection connection = dataSource.getConnection()) {
            final String SQLQuery = "select * from bootcamp.product where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
            }
        }
        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            final String SQLQuery = "UPDATE bootcamp.product SET name = ?, price = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void save(Product product) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            final String SQLQuery = "INSERT INTO bootcamp.product(name,price) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            final String SQLQuery = "DELETE FROM bootcamp.product where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
