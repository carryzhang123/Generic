package hang.thread.tiantask;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZhangHang
 * @create 2018-05-02 17:50
 **/
public enum RunnableKey {
    FIRST("first"),
    SECOND("second");
    private String runnableKeyName;

    private static final ExecutorService ACTOR_EXECUTORS = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final HashMap<Integer, Actor> ACTORS = new HashMap<>(RunnableKey.values().length);

    static {
        for (RunnableKey value : values()) {
            value.actor = new Actor(ACTOR_EXECUTORS);
            ACTORS.put(value.threadId(), value.actor);
        }
    }

    private Actor actor;

    public static Actor getActor(int threadId) {
        return ACTORS.get(threadId);
    }

    public Actor getActor() {
        return actor;
    }

    public int threadId() {
        return this.ordinal();
    }

    RunnableKey(String runnableKeyName) {
        this.runnableKeyName = runnableKeyName;
    }

    public String getRunnableKeyName() {
        return runnableKeyName;
    }

    public void setRunnableKeyName(String runnableKeyName) {
        this.runnableKeyName = runnableKeyName;
    }
}
