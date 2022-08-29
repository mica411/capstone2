package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer getTransferAmount ();

    List<Transfer> getAllTransfers();

    boolean createTransfer(Transfer transfer);

    Transfer getTransferByTransferId(int transferId);

}
