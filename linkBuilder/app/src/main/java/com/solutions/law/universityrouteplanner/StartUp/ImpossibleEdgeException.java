package com.solutions.law.universityrouteplanner.StartUp;

/**
 * Created by kbb12 on 15/02/2016.
 */
public class ImpossibleEdgeException extends RuntimeException {

    Link link;
    public ImpossibleEdgeException(Link link){
        this.link=link;
    }
}
