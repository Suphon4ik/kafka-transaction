package org.example.transferservice.model;

import java.math.BigDecimal;
import java.util.Objects;

public class TransferRestModel {

    private String senderId;
    private String recipientId;
    private BigDecimal amount;

    public TransferRestModel() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferRestModel that = (TransferRestModel) o;
        return Objects.equals(senderId, that.senderId) && Objects.equals(recipientId, that.recipientId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, recipientId, amount);
    }

    @Override
    public String toString() {
        return "TransferRestModel{" +
                "senderId='" + senderId + '\'' +
                ", recipientId='" + recipientId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
