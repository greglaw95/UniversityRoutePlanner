package com.solutions.law.universityrouteplanner.Controller;

/**
 * Created by kbb12 on 27/02/2016.
 */
public interface Structure {
    public String getName();
    public float getMinZoomAllowed();
    public double getMinLatAllowed();
    public double getMaxLatAllowed();
    public double getMinLngAllowed();
    public double getMaxLngAllowed();
    public int getLevel();
    public void setLevel(int level);
}
