package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
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
            transfer.setAmount(new BigDecimal(transAmount));
        }
        return transfer;
    }

    @Override
    public List<Transfer> getAllTransfers(){
        List<Transfer> transfers= new ArrayList<>();
        String sql= "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount"+
                " FROM transfer;";
        SqlRowSet results= jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Transfer transfer= mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }


    @Override
    public boolean createTransfer(Transfer transfer){
        String sql= "INSERT INTO transfer( transfer_type_id, transfer_status_id, account_from, account_to, amount)"+
                " VALUES( ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, transfer.getTransferTypeId(),transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount())==1;
    }

    @Override
    public Transfer getTransferByTransferId(int transferId){
        Transfer transfer= null;
        String sql ="SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount"+
                "FROM transfer WHERE transfer_id=?";
        SqlRowSet results= jdbcTemplate.queryForRowSet(sql,transferId);
        if(results.next()){
            transfer=mapRowToTransfer(results);
        }
        return transfer;
    }
    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
      transfer.setTransferId(rs.getInt("transfer_id"));
      transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
      transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
      transfer.setAccountFrom(rs.getInt("account_from"));
      transfer.setAccountTo(rs.getInt("account_to"));
      transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }

}
