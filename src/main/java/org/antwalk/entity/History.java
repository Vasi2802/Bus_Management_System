package org.antwalk.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class History {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id")
	private long historyId;

	@Column(name = "employee_id")
	private long employeeId;

	@Column(name = "receipt_date")
	private LocalDate receiptDate;

    @Column(name = "bus_id")
    private long busId;
    
    @Column(name = "route_id")
    private long routeId;

    @Column(name = "route_description")
    private String routeDescription;

    @Column(name = "transaction_type")
    private String transactionType;

    public History() {
    }

    public History(long historyId, long employeeId, LocalDate receiptDate, long busId, long routeId,
            String routeDescription, String transactionType) {
        this.historyId = historyId;
        this.employeeId = employeeId;
        this.receiptDate = receiptDate;
        this.busId = busId;
        this.routeId = routeId;
        this.routeDescription = routeDescription;
        this.transactionType = transactionType;
    }

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public long getBusId() {
        return busId;
    }

    public void setBusId(long busId) {
        this.busId = busId;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "History [historyId=" + historyId + ", employeeId=" + employeeId + ", receiptDate=" + receiptDate
                + ", busId=" + busId + ", routeId=" + routeId + ", routeDescription=" + routeDescription
                + ", transactionType=" + transactionType + "]";
    }

    
}
