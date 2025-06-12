package org.example.transferservice.service;

import org.example.transferservice.model.TransferRestModel;

public interface TransferService {
    boolean transfer(TransferRestModel transferRestModel);
}
