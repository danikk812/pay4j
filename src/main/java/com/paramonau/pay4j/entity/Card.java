package com.paramonau.pay4j.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Card {

    private Integer id;
    private Integer account;
    private String number;
    private String ownerName;
    private LocalDate expirationDate;
    private int cvv;
    private Status status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", account=" + account +
                ", number='" + number + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", expirationDate=" + expirationDate +
                ", cvv=" + cvv +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cvv == card.cvv && Objects.equals(id, card.id) && Objects.equals(account, card.account) && Objects.equals(number, card.number) && Objects.equals(ownerName, card.ownerName) && Objects.equals(expirationDate, card.expirationDate) && Objects.equals(status, card.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, number, ownerName, expirationDate, cvv, status);
    }
}
