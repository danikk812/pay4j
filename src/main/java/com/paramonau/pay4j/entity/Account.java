package com.paramonau.pay4j.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Account {

    private  Integer id;
    private Integer user;
    private Status status;
    private BigDecimal balance;
    private LocalDate creationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user +
                ", status=" + status +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(user, account.user) && Objects.equals(status, account.status) && Objects.equals(balance, account.balance) && Objects.equals(creationDate, account.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, status, balance, creationDate);
    }
}
