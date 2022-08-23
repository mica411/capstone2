package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class JdbcTransferDao implements TransferDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao (DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    public Transfer getTransferAmount (){
        String sql = "SELECT amount, transfer_id FROM transfer";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        Transfer transfer = new Transfer();
        if (results.next()) {
            String transAmount = results.getString("transfer");
            transfer.setTransferAmount(new BigDecimal(transAmount));
        }
        return transfer;
    }
}
