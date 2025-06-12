package org.example.core.events;

import java.math.BigDecimal;
import java.util.Objects;

public class DepositRequestedEvent {
    private String senderId;
    private String recipientId;
    private BigDecimal amount;

    public DepositRequestedEvent() {
    }

    public DepositRequestedEvent(String senderId, String recipientId, BigDecimal amount) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
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
        DepositRequestedEvent that = (DepositRequestedEvent) o;
        return Objects.equals(senderId, that.senderId) && Objects.equals(recipientId, that.recipientId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, recipientId, amount);
    }

    @Override
    public String toString() {
        return "DepositRequestedEvent{" +
                "senderId='" + senderId + '\'' +
                ", recipientId='" + recipientId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
