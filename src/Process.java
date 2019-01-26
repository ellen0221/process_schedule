public class Process {  // 进程类

    private String name;    // 进程名
    private int size;       // 所需内存大小
    private int firstpoint; // 分配的内存起始地址
    private int gtime;      // 分配时间
    private boolean acquired = false;   // 是否得到内存分配
    private int priority;   // 优先级
    private int ntime;      // 服务时间
    private int rtime;      // 到达时间
    private int remainServiceTime; //还需要服务的时间
    private int ftime;      // 完成时间
    private int T;          // 周转时间 = 到达时间 - 完成时间
    private double W;          // 带权周转时间 = 周转时间/服务时间
    private double rate = 0;  // 响应比，默认为0

    public int getSize() {
        return size;
    }

    public int getFirstpoint() {
        return firstpoint;
    }

    public void setFirstpoint(int firstpoint) {
        this.firstpoint = firstpoint;
    }

    public int getGtime() {
        return gtime;
    }

    public void setGtime(int gtime) {
        this.gtime = gtime;
    }

    public void setAcquired(boolean acquired) {
        this.acquired = acquired;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getNtime() {
        return ntime;
    }

    public int getRtime() {
        return rtime;
    }

    public int getRemainServiceTime() {
        return remainServiceTime;
    }

    public void setRemainServiceTime(int remainServiceTime) {
        this.remainServiceTime = remainServiceTime;
    }

    public int getFtime() {
        return ftime;
    }

    public void setFtime(int ftime) {
        this.ftime = ftime;
    }

    public int getT() {
        T = ftime-rtime;
        return T;
    }

    public double getW() {
        W = (double) T/ (double) ntime;
        return W;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public Process(String name, int priority, int ntime, int rtime, int remainServiceTime) {
        this.name = name;
        this.priority = priority;
        this.ntime = ntime;
        this.rtime = rtime;
        this.remainServiceTime = remainServiceTime;
    }

    public Process(String name, int ntime, int size, int remainServiceTime) {
        this.name = name;
        this.ntime = ntime;
        this.size = size;
        this.remainServiceTime = remainServiceTime;
    }
}
