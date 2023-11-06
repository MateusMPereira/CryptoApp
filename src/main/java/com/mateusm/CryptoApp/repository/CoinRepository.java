package com.mateusm.CryptoApp.repository;

import com.mateusm.CryptoApp.dto.CoinTransactionDTO;
import com.mateusm.CryptoApp.entity.CoinEntity;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {

    private final static String INSERT = "insert into coin (name, price, quantity, datetime) values (?, ?, ?, ?)";

    private final static String SELECT_ALL = "select name, sum(quantity) as quantity from coin group by name";

    private final static String SELECT_BY_NAME = "select * from coin where name = ?";

    private final static String SELECT_BY_ID = "select * from coin where id = ?";

    private final static String DELETE = "delete from coin where id = ?";

    private final static String UPDATE = "update coin set name = ?, price = ?, quantity = ? where id = ?";

    private final JdbcTemplate jdbcTemplate;

    public CoinRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CoinEntity insert(CoinEntity coin) {
        Object[] attr = new Object[]{
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getDatetime()
        };
        jdbcTemplate.update(INSERT, attr);

        return coin;
    }

    public List<CoinTransactionDTO> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<CoinTransactionDTO>() {
            @Override
            public CoinTransactionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

                CoinTransactionDTO coin = new CoinTransactionDTO();
                coin.setName(rs.getString("name"));
                coin.setQuantity(rs.getBigDecimal("quantity"));

                return coin;
            }
        });
    }

    public List<CoinEntity> getByName(String name) {
        Object[] attr = new Object[] { name };
        return jdbcTemplate.query(SELECT_BY_NAME, new RowMapper<CoinEntity>() {
            @Override
            public CoinEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                CoinEntity coin = new CoinEntity();

                coin.setId(rs.getInt("id"));
                coin.setName(rs.getString("name"));
                coin.setPrice(rs.getBigDecimal("price"));
                coin.setQuantity(rs.getBigDecimal("quantity"));
                coin.setDatetime(rs.getTimestamp("datetime"));

                return coin;
            }
        }, attr);
    }

    public CoinEntity getById(int id) {
        Object[] attr = new Object[] { id };
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new RowMapper<CoinEntity>() {
            @Override
            public CoinEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                CoinEntity coin = new CoinEntity();

                coin.setId(rs.getInt("id"));
                coin.setName(rs.getString("name"));
                coin.setPrice(rs.getBigDecimal("price"));
                coin.setQuantity(rs.getBigDecimal("quantity"));
                coin.setDatetime(rs.getTimestamp("datetime"));

                return coin;
            }
        }, attr);
    }

    public CoinEntity delete(int id) {
        CoinEntity coin = new CoinEntity();

        coin = this.getById(id);
        jdbcTemplate.update(DELETE, id);

        return coin;
    }

    public CoinEntity update(CoinEntity coin) {
        Object[] attr = new Object[] {
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getId()
        };
        jdbcTemplate.update(UPDATE, attr);
        return coin;
    }
}