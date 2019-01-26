import java.util.*;

public class Arithmetic {
    public int nowtime;
    public List<Point> points= new ArrayList<>();;
    public int lastpoint=0;   // 上一次找到的空闲分区结束位置

    public Arithmetic(int nowtime) {
        this.nowtime = nowtime;
    }

    // 短作业优先(不可抢占)
    public List<Process> SJF(List<Process> listOfProcess) {
        List<Process> finish = new ArrayList<>();   // 进程完成列表
        List<Process> sametime = new ArrayList<>();     // 同时到达的进程
        sortByRtime(listOfProcess, 0, listOfProcess.size() - 1);
        nowtime = listOfProcess.get(0).getRtime();  // 设置当前时间为第一个到达进程的到达时间
        while (listOfProcess.size() > 0) {
            prepare(listOfProcess, sametime);
            sortByServertime(sametime, 0, sametime.size() - 1);
            Process p = sametime.get(0);
            nowtime += p.getNtime();
            p.setFtime(nowtime);
            finish.add(p);
            sametime.remove(0);
        }
        allprepare(sametime, finish);
        nowtime = 0;
        return finish;
    }

    // 最高响应比优先
    public List<Process> HRRF(List<Process> listOfProcess) {
        List<Process> finish = new ArrayList<>();   // 进程完成列表
        List<Process> highrate = new ArrayList<>();
        sortByRtime(listOfProcess, 0, listOfProcess.size() - 1);
        nowtime = listOfProcess.get(0).getRtime() + listOfProcess.get(0).getNtime();  // 设置当前时间(已完成第一个进程)
        listOfProcess.get(0).setFtime(nowtime);
        finish.add(listOfProcess.get(0));
        listOfProcess.remove(0);
        while (listOfProcess.size() > 0) {
            Iterator<Process> it = listOfProcess.iterator();
            while (it.hasNext()) {
                Process p = it.next();
                if (p.getRtime() <= nowtime) {
                    double rate = (double) (nowtime - p.getRtime() + p.getNtime()) / (double) p.getNtime();
                    p.setRate(rate);
                    highrate.add(p);
                    it.remove();
                }
            }
            if (highrate.size() > 0) {
                calculateRate(highrate, nowtime);
                sortByRate(highrate, 0, highrate.size() - 1);
                Process p = highrate.get(0);
                nowtime += p.getNtime();
                p.setFtime(nowtime);
                finish.add(p);
                highrate.remove(0);
            }
        }
        Iterator<Process> ra = highrate.iterator();
        while (ra.hasNext()) {
            calculateRate(highrate, nowtime);
            sortByRate(highrate, 0, highrate.size() - 1);
            Process p = ra.next();
            nowtime += p.getNtime();
            p.setFtime(nowtime);
            finish.add(p);
            ra.remove();
        }
        nowtime = 0;
        return finish;
    }

    // 计算响应比
    public void calculateRate(List<Process> listOfProcess, int now) {
        for (int i = 0; i < listOfProcess.size(); i++) {
            Process p = listOfProcess.get(i);
            double rate = (double) (now - p.getRtime() + p.getNtime()) / (double) p.getNtime();
            listOfProcess.get(i).setRate(rate);
        }
    }

    // 优先级调度
    public List<Process> Priority(List<Process> listOfProcess) {
        List<Process> finish = new ArrayList<>();   // 进程完成列表
        List<Process> sametime = new ArrayList<>();     // 同时到达的进程
        sortByRtime(listOfProcess, 0, listOfProcess.size() - 1);
        nowtime = listOfProcess.get(0).getRtime();  // 设置当前时间为第一个到达进程的到达时间
        while (listOfProcess.size() > 0) {
            prepare(listOfProcess, sametime);    // 将可调度进程加入就绪表
            sortByPriority(sametime, 0, sametime.size() - 1);
            Process p = sametime.get(0);
            nowtime += p.getNtime();
            p.setFtime(nowtime);
            finish.add(p);
            sametime.remove(0);
        }
        allprepare(sametime, finish);
        return finish;
    }

    // 将可调度的进程加入sametime列表
    public void prepare(List<Process> listOfProcess, List<Process> sametime) {
        if (listOfProcess.size() > 0) {
            Iterator<Process> it = listOfProcess.iterator();
            while (it.hasNext()) {
                Process p = it.next();
                if (p.getRtime() <= nowtime) {
                    sametime.add(p);
                    it.remove();
                }
            }
        }
    }

    // 将就绪列表中的进程按顺序运行
    public void allprepare(List<Process> sametime, List<Process> finish) {
        while (sametime.size() > 0) {
            Process p = sametime.get(0);
            nowtime += p.getNtime();
            p.setFtime(nowtime);
            finish.add(p);
            sametime.remove(0);
        }
    }

    // 轮转调度
    public List<Process> RR(List<Process> listOfProcess, int timeslice) {
        nowtime = 0;
        List<Process> finish = new ArrayList<>();   // 进程完成列表
        List<Process> now = new ArrayList<>();      // 就绪列表
        sortByRtime1(listOfProcess, 0, listOfProcess.size() - 1);
        while (listOfProcess.size() > 0 || now.size() > 0) {
            prepare(listOfProcess, now);
            Process currProcess = now.get(0);
            now.remove(currProcess);
            if ((currProcess.getRemainServiceTime()-timeslice)>=0) {
                nowtime += timeslice;
                currProcess.setRemainServiceTime(currProcess.getRemainServiceTime() - timeslice);
            } else {
                nowtime += currProcess.getRemainServiceTime();
                currProcess.setRemainServiceTime(0);
            }
            if (currProcess.getRemainServiceTime() > 0) {
                now.add(currProcess);
            } else {
                currProcess.setFtime(nowtime);
                finish.add(currProcess);
            }
            if (now.size() == 0 && listOfProcess.size() > 0) {
                nowtime = listOfProcess.get(listOfProcess.size() - 1).getRtime();
            }
        }
        return finish;
    }

    // 进程执行算法
    public int executeProcess(Process currProcess, int currTime, int timeslice) {
        if (currProcess.getRemainServiceTime() - timeslice >= 0) {
            currTime += timeslice;
        } else {
            currTime += currProcess.getRemainServiceTime();
        }
        return currTime;
    }


    // 先来先服务
    public List<Process> FCFS(List<Process> listOfProcess) {
        List<Process> finishProcess = new ArrayList<>();
        sortByRtime(listOfProcess, 0, listOfProcess.size() - 1);
        nowtime = listOfProcess.get(0).getRtime();    // 到达时间

        while (listOfProcess.size() > 0) {
            for (int i = 0; i < listOfProcess.size(); i++) {
                if (listOfProcess.get(0).getRtime() <= nowtime) {   // 到达时间<=当前时间，则可以进行调度
                    Process p = listOfProcess.get(0);
                    int ntime = p.getNtime();    // 服务时间
                    nowtime += ntime;  // 调度列表中第一个进程：更新当前时间
                    p.setFtime(nowtime);
                    finishProcess.add(p);
                    listOfProcess.remove(0);    // 进程运行结束
                } else {
                    nowtime = listOfProcess.get(0).getRtime();
                }
            }
        }
        nowtime = 0;
        return finishProcess;
    }

    // 计算平均周转时间
    public double AVG_T(List<Process> listOfProcess) {
        double t = 0;
        for (int i = 0; i < listOfProcess.size(); i++) {
            t += listOfProcess.get(i).getT();
        }
        return t / listOfProcess.size();
    }

    // 计算平均带权周转时间
    public double AVG_W(List<Process> listOfProcess) {
        double w = 0;
        for (int i = 0; i < listOfProcess.size(); i++) {
            w += listOfProcess.get(i).getW();
        }
        return w / listOfProcess.size();
    }

    // 按到达时间排序（到达时间小的在前）
    public void sortByRtime(List<Process> listOfProcess, int low, int high) {
        int start = low;
        int end = high;
        int key = listOfProcess.get(start).getRtime();

        while (end > start) {

            // 从后往前
            while (end > start && listOfProcess.get(end).getRtime() >= key) {
                end--;
            }
            // 若end到达时间<=key，即end进程先到达，则交换end和start，将到达时间最小的进程放在队列的最前面，且此时key所对应的进程以后的进程到达时间都比其晚
            if (listOfProcess.get(end).getRtime() <= key) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }

            // 从前往后
            while (end > start && listOfProcess.get(start).getRtime() <= key) {
                start++;
            }
            // 若在start进程和key所对应的进程间有比key更大的，则交换二者
            if (listOfProcess.get(start).getRtime() >= key) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }
        }

        // 递归排序
        if (low < start) {
            sortByRtime(listOfProcess, low, start - 1);
        }
        if (high > end) {
            sortByRtime(listOfProcess, end + 1, high);
        }

    }

    // 根据分区始址排序
    public void sortByAddress(List<Point> listOfProcess, int low, int high) {
        int start = low;
        int end = high;
        int key = listOfProcess.get(start).getHead();

        while (end > start) {

            // 从后往前
            while (end > start && listOfProcess.get(end).getHead() >= key) {
                end--;
            }
            if (listOfProcess.get(end).getHead() <= key) {
                Point p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }

            // 从前往后
            while (end > start && listOfProcess.get(start).getHead() <= key) {
                start++;
            }
            if (listOfProcess.get(start).getHead() >= key) {
                Point p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }
        }

        // 递归排序
        if (low < start) {
            sortByAddress(listOfProcess, low, start - 1);
        }
        if (high > end) {
            sortByAddress(listOfProcess, end + 1, high);
        }

    }

    // 按到达时间排序（后到的在前）
    public void sortByRtime1(List<Process> listOfProcess, int low, int high) {
        int start = low;
        int end = high;
        int key = listOfProcess.get(start).getRtime();

        while (end > start) {

            // 从后往前
            while (end > start && listOfProcess.get(end).getRtime() <= key) {
                end--;
            }
            // 若end到达时间>=key，即end进程后到达，则交换end和start，将到达时间最大的进程放在队列的最前面，且此时key所对应的进程以后的进程到达时间都比其早
            if (listOfProcess.get(end).getRtime() >= key) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }

            // 从前往后
            while (end > start && listOfProcess.get(start).getRtime() >= key) {
                start++;
            }
            // 若在start进程和key所对应的进程间有比key更小的，则交换二者
            if (listOfProcess.get(start).getRtime() <= key) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }
        }

        // 递归排序
        if (low < start) {
            sortByRtime(listOfProcess, low, start - 1);
        }
        if (high > end) {
            sortByRtime(listOfProcess, end + 1, high);
        }
    }

    // 按服务时间长短排序(服务时间短的在前)
    public void sortByServertime(List<Process> listOfProcess, int low, int high) {
        int start = low;
        int end = high;
        int servertime = listOfProcess.get(start).getNtime();

        while (end > start) {

            while (end > start && listOfProcess.get(end).getNtime() >= servertime) {
                end--;
            }
            // 若end进程的服务时间更短，则交换end和start
            if (listOfProcess.get(end).getNtime() <= servertime) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }

            while (end > start && listOfProcess.get(start).getNtime() <= servertime) {
                start++;
            }
            // 若start比servertime对应的进程的服务时间更长，则交换
            if (listOfProcess.get(start).getNtime() >= servertime) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }
        }

        if (low < start) {
            sortByServertime(listOfProcess, low, start - 1);
        }
        if (high > end) {
            sortByServertime(listOfProcess, end + 1, high);
        }
    }

    // 根据优先级排序(优先数高的在前)
    public void sortByPriority(List<Process> listOfProcess, int low, int high) {
        int start = low;
        int end = high;
        int key = listOfProcess.get(start).getPriority();

        while (end > start) {
            // 从后往前
            while (end > start && listOfProcess.get(end).getPriority() <= key) {
                end--;
            }
            if (end > start && listOfProcess.get(end).getPriority() >= key) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }

            // 从前往后
            while (end > start && listOfProcess.get(start).getPriority() >= key) {
                start++;
            }
            if (end > start && listOfProcess.get(start).getPriority() <= key) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }
        }

        if (low < start) {
            sortByPriority(listOfProcess, low, start - 1);
        }
        if (high > end) {
            sortByPriority(listOfProcess, end + 1, high);
        }
    }

    // 根据响应比排序（响应比高的在前）
    public void sortByRate(List<Process> listOfProcess, int low, int high) {
        int start = low;
        int end = high;
        double key = listOfProcess.get(start).getRate();

        while (end > start) {
            // 从后往前
            while (end > start && listOfProcess.get(end).getRate() <= key) {
                end--;
            }
            if (end > start && listOfProcess.get(end).getRate() >= key) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }

            // 从前往后
            while (end > start && listOfProcess.get(start).getRate() >= key) {
                start++;
            }
            if (end > start && listOfProcess.get(start).getRate() <= key) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }
        }

        if (low < start) {
            sortByRate(listOfProcess, low, start - 1);
        }
        if (high > end) {
            sortByRate(listOfProcess, end + 1, high);
        }
    }

    // 按照需要的内存大小排序（大的在前）
    public void sortBySize(List<Process> listOfProcess, int low, int high) {
        int start = low;
        int end = high;
        int size = listOfProcess.get(start).getSize();

        while (end > start) {

            while (end > start && listOfProcess.get(end).getSize() <= size) {
                end--;
            }
            // 若end进程的服务时间更短，则交换end和start
            if (listOfProcess.get(end).getSize() >= size) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }

            while (end > start && listOfProcess.get(start).getSize() >= size) {
                start++;
            }
            // 若start比servertime对应的进程的服务时间更长，则交换
            if (listOfProcess.get(start).getSize() <= size) {
                Process p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }
        }

        if (low < start) {
            sortBySize(listOfProcess, low, start - 1);
        }
        if (high > end) {
            sortBySize(listOfProcess, end + 1, high);
        }
    }

    // 按照空闲区内存大小排序（大的在前）
    public void sortBySize1(List<Point> listOfProcess, int low, int high) {
        int start = low;
        int end = high;
        int size = listOfProcess.get(start).getSize();

        while (end > start) {

            while (end > start && listOfProcess.get(end).getSize() <= size) {
                end--;
            }

            if (listOfProcess.get(end).getSize() >= size) {
                Point p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }

            while (end > start && listOfProcess.get(start).getSize() >= size) {
                start++;
            }

            if (listOfProcess.get(start).getSize() <= size) {
                Point p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }
        }

        if (low < start) {
            sortBySize1(listOfProcess, low, start - 1);
        }
        if (high > end) {
            sortBySize1(listOfProcess, end + 1, high);
        }
    }

    // 按照空闲区内存大小排序（小的在前）
    public void sortBySize2(List<Point> listOfProcess, int low, int high) {
        int start = low;
        int end = high;
        int size = listOfProcess.get(start).getSize();

        while (end > start) {

            while (end > start && listOfProcess.get(end).getSize() >= size) {
                end--;
            }

            if (listOfProcess.get(end).getSize() <= size) {
                Point p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }

            while (end > start && listOfProcess.get(start).getSize() <= size) {
                start++;
            }

            if (listOfProcess.get(start).getSize() >= size) {
                Point p = listOfProcess.get(end);
                listOfProcess.set(end, listOfProcess.get(start));
                listOfProcess.set(start, p);
            }
        }

        if (low < start) {
            sortBySize2(listOfProcess, low, start - 1);
        }
        if (high > end) {
            sortBySize2(listOfProcess, end + 1, high);
        }
    }

    // 回收内存,返回空闲区列表
    public void collection(Process p) {
        int head = p.getFirstpoint();   //0
        int end = head + p.getSize();     //10
        int size = p.getSize();
        boolean iscollect = false;  // 标记是否回收
        for (int i = points.size() - 1; i >= 0; i--) {
            Point po = points.get(i);
            if (po.getHead() == end) {
                points.remove(po);
                po.setHead(head);
                po.setSize(po.getSize() + size);
                points.add(po);
                iscollect = true;
                break;
            } else if ((po.getHead() + po.getSize()) == head) {
                points.remove(po);
                po.setSize(po.getSize() + size);
                points.add(po);
                iscollect = true;
                break;
            }
        }
        if (!iscollect) {
            points.add(new Point(head, p.getSize()));
        }
        sortByAddress(points, 0, points.size() - 1);
        Collections.reverse(points);
        for (int i = points.size() - 1; i > 0; i--) {
            if ((points.get(i).getHead() + points.get(i).getSize()) == points.get(i - 1).getHead()) {
                points.get(i - 1).setSize(points.get(i).getSize() + points.get(i - 1).getSize());
                points.get(i - 1).setHead(points.get(i).getHead());
                points.remove(i);
            }
        }

        for (int x=0; x<points.size(); x++) {
            System.out.println("points " + x + " : " + points.get(x).getHead());
            System.out.println("points " + x + " : " + points.get(x).getSize());
        }
    }


    // 首次适应
    public List<Process> FF(List<Process> listOfProcess, List<Point> points1) {
        nowtime = 0;
        points.addAll(points1);
        List<Process> run = new ArrayList<>();
        List<Process> finish = new ArrayList<>();
        sortBySize(listOfProcess, 0, listOfProcess.size() - 1);
        while (listOfProcess.size() > 0 || run.size() > 0) {
            for (int i = listOfProcess.size() - 1; i >= 0; i--) {
                if (points.size() > 1) {
                    sortByAddress(points, 0, points.size() - 1);
                }
                Process p = listOfProcess.get(i);
                int size = p.getSize();
                for (int j = points.size() - 1; j >= 0; j--) {
                    if (points.get(j).getSize() >= size) {
                        p.setFirstpoint(points.get(j).getHead());
                        p.setAcquired(true);
                        p.setGtime(nowtime);
                        points.get(j).setSize(points.get(j).getSize() - size);
                        points.get(j).setHead(points.get(j).getHead() + size);
                        listOfProcess.remove(p);
                        run.add(p);
                        break;
                    }
                }
            }
            if (run.size() > 0) {
                for (int j = run.size() - 1; j >= 0; j--) {
                    // 判断是否回收内存
                    if (run.get(j).getRemainServiceTime() == 0) {
                        Process p1 = run.get(j);
                        collection(p1);
                        finish.add(p1);
                        run.remove(p1);
                    } else {
                        run.get(j).setRemainServiceTime(run.get(j).getRemainServiceTime() - 1);
                    }
                }
                nowtime++;
            }
        }
        points.removeAll(points);
        return finish;
    }

    // 循环首次适应
    public List<Process> NF(List<Process> listOfProcess, List<Point> points1) {
        nowtime = 0;
        points.addAll(points1);
        List<Process> run = new ArrayList<>();
        List<Process> finish = new ArrayList<>();
        sortBySize(listOfProcess, 0, listOfProcess.size() - 1);
        while (listOfProcess.size() > 0 || run.size() > 0) {
            for (int i = listOfProcess.size() - 1; i >= 0; i--) {
                if (points.size() > 1) {
                    sortByAddress(points, 0, points.size() - 1);
                }
                Process p = listOfProcess.get(i);
                int size = p.getSize();
                if (lastpoint<0) {
                    lastpoint=points.size()-1;
                }
                    for (int j = lastpoint; j >= 0; j--) {
                        if (points.get(j).getSize() >= size) {
                            p.setFirstpoint(points.get(j).getHead());
                            p.setAcquired(true);
                            p.setGtime(nowtime);
                            points.get(j).setSize(points.get(j).getSize() - size);
                            points.get(j).setHead(points.get(j).getHead() + size);
                            listOfProcess.remove(p);
                            run.add(p);
                            lastpoint--;
                            break;
                        }
                    }
            }
            if (run.size() > 0) {
                for (int j = run.size() - 1; j >= 0; j--) {
                    // 判断是否回收内存
                    if (run.get(j).getRemainServiceTime() == 0) {
                        Process p1 = run.get(j);
                        System.out.println("run: " + p1.getName());
                        collection(p1);
                        finish.add(p1);
                        run.remove(p1);
                    } else {
                        run.get(j).setRemainServiceTime(run.get(j).getRemainServiceTime() - 1);
                    }
                }
                nowtime++;
            }
        }
        return finish;
    }

    // 最佳适应算法
    public List<Process> BF(List<Process> listOfProcess, List<Point> points1) {
        nowtime = 0;
        points.addAll(points1);
        List<Process> run = new ArrayList<>();
        List<Process> finish = new ArrayList<>();
        sortBySize(listOfProcess, 0, listOfProcess.size() - 1);
        sortBySize1(points, 0, points.size()-1); // 将空闲分区按分区由大到小排序
        while (listOfProcess.size() > 0 || run.size() > 0) {
            for (int i = listOfProcess.size() - 1; i >= 0; i--) {
                if (points.size() > 1) {
                    sortByAddress(points, 0, points.size() - 1);
                }
                Process p = listOfProcess.get(i);
                int size = p.getSize();
                for (int j = points.size() - 1; j >= 0; j--) {
                    if (points.get(j).getSize() >= size) {
                        p.setFirstpoint(points.get(j).getHead());
                        p.setAcquired(true);
                        p.setGtime(nowtime);
                        points.get(j).setSize(points.get(j).getSize() - size);
                        points.get(j).setHead(points.get(j).getHead() + size);
                        listOfProcess.remove(p);
                        run.add(p);
                        break;
                    }
                }
            }
            if (run.size() > 0) {
                for (int j = run.size() - 1; j >= 0; j--) {
                    // 判断是否回收内存
                    if (run.get(j).getRemainServiceTime() == 0) {
                        Process p1 = run.get(j);
                        collection(p1);
                        finish.add(p1);
                        run.remove(p1);
                    } else {
                        run.get(j).setRemainServiceTime(run.get(j).getRemainServiceTime() - 1);
                    }
                }
                nowtime++;
            }
        }
        points.removeAll(points);

        return finish;
    }

    // 最差适应算法
    public List<Process> WF(List<Process> listOfProcess, List<Point> points1) {
        nowtime = 0;
        points.addAll(points1);
        List<Process> run = new ArrayList<>();
        List<Process> finish = new ArrayList<>();
        sortBySize(listOfProcess, 0, listOfProcess.size() - 1);
        sortBySize2(points, 0, points.size()-1);
        while (listOfProcess.size() > 0 || run.size() > 0) {
            for (int i = listOfProcess.size() - 1; i >= 0; i--) {
                if (points.size() > 1) {
                    sortByAddress(points, 0, points.size() - 1);
                }
                Process p = listOfProcess.get(i);
                int size = p.getSize();
                for (int j = points.size() - 1; j >= 0; j--) {
                    if (points.get(j).getSize() >= size) {
                        p.setFirstpoint(points.get(j).getHead());
                        p.setAcquired(true);
                        p.setGtime(nowtime);
                        points.get(j).setSize(points.get(j).getSize() - size);
                        points.get(j).setHead(points.get(j).getHead() + size);
                        listOfProcess.remove(p);
                        run.add(p);
                        break;
                    }
                }
            }
            if (run.size() > 0) {
                for (int j = run.size() - 1; j >= 0; j--) {
                    // 判断是否回收内存
                    if (run.get(j).getRemainServiceTime() == 0) {
                        Process p1 = run.get(j);
                        collection(p1);
                        finish.add(p1);
                        run.remove(p1);
                    } else {
                        run.get(j).setRemainServiceTime(run.get(j).getRemainServiceTime() - 1);
                    }
                }
                nowtime++;
            }
        }
        points.removeAll(points);
        return finish;
    }
}
