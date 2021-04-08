package ru.job4j.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private Timestamp created;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Item(int id, String description, Timestamp created, String status, User user) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.status = status;
        this.user = user;
    }
    public Item(String description, Timestamp created, String status, User user) {
        this.description = description;
        this.created = created;
        this.status = status;
        this.user = user;
    }

    public Item() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id && Objects.equals(description, item.description)
                && Objects.equals(created, item.created)
                && Objects.equals(status, item.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, status);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + created
                + ", status='" + status + '\''
                + '}';
    }
}
