/**
 * @author ZhangHang
 * @create 2018-01-15 12:30
 **/
public class ActionObserver {

    public enum ObserverType {
        ONE(1 << 2);
        private final int value;

        ObserverType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private int id;
    private int observerType;

    public ActionObserver(ObserverType... observerTypes) {

        if (observerTypes != null) {
            for (ObserverType type : observerTypes) {
                this.observerType |= type.getValue();
            }
        }
    }
}
