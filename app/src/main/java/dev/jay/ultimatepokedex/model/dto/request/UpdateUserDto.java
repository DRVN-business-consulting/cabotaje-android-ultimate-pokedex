package dev.jay.ultimatepokedex.model.dto.request;

public class UpdateUserDto {
    private String password;
    private String name;
    private String address;
    private int age;
    public UpdateUserDto(String password, String name, String address, int age) {
        this.password = password;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UpdateUserDto{" +
                "password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
