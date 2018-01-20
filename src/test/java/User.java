/**
 * @author ZhangHang
 * @create 2018-01-15 11:42
 **/
public enum  User {
    ONE(1) {
        @Override
        public void say() {

        }
    };


   User(int id) {
        this.id=id;
    }

    private int id;

    public abstract void say();
}
