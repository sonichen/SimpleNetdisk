package cn.itcast.domain;

public class Page {
    public int start = 0;
    public int count = 5;
    public int last = 0;
    private String userName;
    private String filePath=null;
    private String check;
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getLast() {
        return last;
    }
    public void setLast(int last) {
        this.last = last;
    }

    public void calculateLast(int total) {

        if(0==total%count) {
            last = total-count;
        }else {
            last = total-total%count;
        }
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", last=" + last +
                ", userName='" + userName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}