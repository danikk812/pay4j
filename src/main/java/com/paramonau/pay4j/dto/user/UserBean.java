package com.paramonau.pay4j.dto.user;

import java.time.LocalDate;
import java.util.Objects;

public class UserBean {

    private String login;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthDate;
    private String phone;
    private String imagePath;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", phone='" + phone + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBean userBean = (UserBean) o;
        return Objects.equals(login, userBean.login) && Objects.equals(name, userBean.name) && Objects.equals(surname, userBean.surname) && Objects.equals(patronymic, userBean.patronymic) && Objects.equals(birthDate, userBean.birthDate) && Objects.equals(phone, userBean.phone) && Objects.equals(imagePath, userBean.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, surname, patronymic, birthDate, phone, imagePath);
    }
}
