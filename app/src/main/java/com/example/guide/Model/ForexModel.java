package com.example.guide.Model;

import java.util.HashMap;
import java.util.List;

public class ForexModel {
    private List<String> pairList;
    private List<Object> forexList;
    private HashMap<String, String> counntryNameMap;

    public List<String> getPairList() {
        return pairList;
    }

    public void setPairList(List<String> pairList) {
        this.pairList = pairList;
    }

    public List<Object> getForexList() {
        return forexList;
    }

    public void setForexList(List<Object> forexList) {
        this.forexList = forexList;
    }

    public HashMap<String, String> getCounntryNameMap() {
        return counntryNameMap;
    }

    public void setCounntryNameMap(HashMap<String, String> counntryNameMap) {
        this.counntryNameMap = counntryNameMap;
    }

    public ForexModel(List<String> pairList, List<Object> forexList, HashMap<String, String> counntryNameMap) {
        this.pairList = pairList;
        this.forexList = forexList;
        this.counntryNameMap = counntryNameMap;
    }

    public ForexModel() {

    }
}

