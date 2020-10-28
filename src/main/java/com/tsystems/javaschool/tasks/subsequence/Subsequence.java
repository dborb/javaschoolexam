package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.ListIterator;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if(x == null || y == null){
            throw new IllegalArgumentException();
        }
        ListIterator<Object> xIterator = x.listIterator();
        ListIterator<Object> yIterator = y.listIterator();
        Object xElement = null;
        Object yElement;

        if(xIterator.hasNext()){
            xElement = xIterator.next();
        }

        for(;yIterator.hasNext() && xIterator.hasNext();){
            yElement = yIterator.next();
            if(yElement.equals(xElement)){
                xElement = xIterator.next();
            }
        }

        return !xIterator.hasNext();
    }
}
