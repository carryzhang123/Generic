package hang.spring.sanqilaod.model;

/**
 * @author ZhangHang
 * @create 2018-06-07 16:05
 **/
public class PetInfo {
    private String level;
    private String exp;

    public static PetInfo valueOf(String level, String exp) {
        PetInfo petInfo = new PetInfo();
        petInfo.setExp(exp);
        petInfo.setLevel(level);
        return petInfo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
