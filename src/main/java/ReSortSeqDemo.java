public class ReSortSeqDemo {


    int a=0;
    boolean flag=false;
    public void method01(){
        a=1;
        flag=true;
    }
    // 多个线程环境中线程交替执行 由于编译器优化重排的存在
//     两个线程中使用能否保证一致性是无法确定的 结果无法预测
    public void method02(){
        if (flag){
            a+=5;
            System.out.println("reValue = " + a);
        }
    }


    public static void main(String[] args) {
        final ReSortSeqDemo reSortSeqDemo = new ReSortSeqDemo();
        for (int i = 0; i < 200; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    reSortSeqDemo.method01();
                    reSortSeqDemo.method02();
                }
            }).start();
        }
    }
}
