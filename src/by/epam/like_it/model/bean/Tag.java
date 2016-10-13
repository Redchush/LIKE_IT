package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.RealEntity;

public class Tag implements RealEntity{

    private Long id;
    private String name;

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tag tag = (Tag) o;

        //noinspection SimplifiableIfStatement
        if (id != null ? !id.equals(tag.id) : tag.id != null) {
            return false;
        }
        return name != null ? name.equals(tag.name) : tag.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");

        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }


    @Override
    public void initRequiredDefault() {
        /*NOP*/
    }

    @Override
    public void initAllDefault() {
        /*NOP*/
    }
}