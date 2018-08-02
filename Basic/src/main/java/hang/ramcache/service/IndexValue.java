package hang.ramcache.service;

/**
 * @author ZhangHang
 * @create 2018-01-30 14:37
 **/
public class IndexValue {
    private final String name;
    private final Object value;

    public static IndexValue valueOf(String name, Object value) {
        if (name == null) {
            throw new IllegalArgumentException("索引名不能为null");
        } else {
            return new IndexValue(name, value);
        }
    }

    private IndexValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public <T> T getValue(Class<T> clz) {
        return (T) this.value;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            IndexValue other = (IndexValue) obj;
            if (this.name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!this.name.equals(other.name)) {
                return false;
            }
            if (this.value == null) {
                if (other.value != null) {
                    return false;
                }
            } else if (!this.value.equals(other.value)) {
                return false;
            }
            return true;
        }
    }
}
