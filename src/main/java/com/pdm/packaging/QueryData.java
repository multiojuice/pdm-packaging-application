package com.pdm.packaging;

import java.util.*;

public class QueryData {
    private int resultCount;
    private List<Object> data;

    public QueryData(int count, List<Object> input) {
        resultCount = count;
        data = input;
    }

    public QueryData() {
        resultCount = 0;
        data = new ArrayList<>();
    }

    public void setData(List<Object> input) {
        this.data = input;
    }

    public void setCount(int count) {
        this.resultCount = count;
    }

    public boolean addData(Object o) {
        resultCount++;
        return data.add(o);
    }

    public int getCount() {
        return resultCount;
    }

    public List<Object> getData() {
        return data;
    }
}
