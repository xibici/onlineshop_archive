package com.xjh.domain;
import java.util.List;
/*分表查询*/
public class PageBean<T> {
    private int pageNum;
    private int pageSize;
    private Long totalSize;
    private int pageCount;
    private List<T> data;
    private int startPage;
    private int endPage;
    @Override
    public String toString() {
        return "PageBean{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", pageCount=" + pageCount +
                ", data=" + data +
                '}';
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public PageBean(int pageNum, int pageSize, Long totalSize, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.data = data;
        //计算总页数
        this.pageCount = (int)(totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1);//总页数
        //计算导航的开始页和结束页
        //1.正常的情况  当前页-4     +5
        this.startPage=pageNum-4;
        this.endPage=pageNum+5;
        //2.当前页码小于5
        if(pageNum<5){
            this.startPage=1;
            this.endPage=10;
        }

        //3.当前页大于  快到总页数 总页数-5
        if(pageNum>(pageCount-5)){
            this.startPage=pageCount-9;
            this.endPage=pageCount;
        }
        //4.总页数小于10
        if (pageCount<10){
            this.startPage=1;
            this.endPage=pageCount;
        }


    }
}

