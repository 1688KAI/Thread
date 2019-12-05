
public class SingletonDemo {
    private static volatile SingletonDemo instance = null;
    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法 SingletonDemo()");
    }
    //DCL (Double Check Lock 双端检锁机制)
    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> SingletonDemo.getInstance(), String.valueOf(i)).start();
        }
    }
}
