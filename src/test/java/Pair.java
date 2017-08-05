/**
 * @author ZhangHang
 * @create 2017-08-01 20:59
 **/
class Pair{
    private final String name;
    private final int number;

    public Pair(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public Pair(String name, String number) throws NumberFormatException {
        this.name = name;
        this.number = Integer.parseInt(number);
    }

//    public int compareTo(Object o) {
//        if (o instanceof Pair) {
//            // int cmp = Double.compare(number, ((Pair) o).number);
//            int cmp = number - ((Pair) o).number;
//            if (cmp != 0) {// number是第一要比较的，相当于先比较value。如果相同再比较键
//                return cmp;
//            }
//            return name.compareTo(((Pair) o).name);
//        }
//        throw new ClassCastException("Cannot compare Pair with "
//                + o.getClass().getName());
//    }

    public String toString() {
        return name + ' ' + number;
    }
}
