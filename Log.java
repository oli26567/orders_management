package org.example.a3.Model;

public class Log {
    private int billId;
    private int orderId;
    private double totalAmount;

    public Log() {}

    public Log(int billId, int orderId, double totalAmount) {
        this.billId = billId;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    @Override
    public String toString() {
        return "Log [billId=" + billId + ", orderId=" + orderId + ", totalAmount=" + totalAmount + "]";
    }
}

