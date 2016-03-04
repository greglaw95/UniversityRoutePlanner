package com.solutions.law.universityrouteplanner.StartUp;

/**
 * Created by kbb12 on 15/02/2016.
 */
public class Link {
    String nodeOne;
    String nodeTwo;
    Double weight;

    public Link(String nodeOne,String nodeTwo,double weight){
        this.nodeOne=nodeOne;
        this.nodeTwo=nodeTwo;
        this.weight=weight;
    }

    public String getNodeOne() {
        return nodeOne;
    }

    public String getNodeTwo() {
        return nodeTwo;
    }

    public double getWeight() {
        return weight;
    }
}
