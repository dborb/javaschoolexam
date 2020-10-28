package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;

public class PyramidBuilder {

    /**
     * solve n^2+n-2*val = 0 equation in integer
     * @param val -- length of given List
     * @return positive root (if it is) or -1 if isn't
     */
    long isСorrectNumberOfInteger(int val){
        double d = Math.pow(1 + 8 * val, 0.5);
        long l = (int)d;
        d = d - l;
        if(d == 0){
            return (-1 + l) / 2;
        }
        return -1;

    }

    /**
     * Builds a pyramid with sorted values, where inputNumbers are sorted values
     *
     * @param inputNumbers to be used in the pyramid
     * @param pyramid pyramid itself
     * @return 2d array with pyramid inside
     */
    int[][] createPyramid(int [][] pyramid, List<Integer> inputNumbers){
        int start = pyramid.length - 1;
        int i, k;
        int pos = 0;
        for(i = 0; i < pyramid.length; ++i){
            for(k = 0; k <= i; ++k){
                pyramid[i][start + k * 2] = inputNumbers.get(pos++);
            }
            start--;
        }


        return pyramid;
    }

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        long size = isСorrectNumberOfInteger(inputNumbers.size());
        if(size == -1){
            throw new CannotBuildPyramidException();
        }

        try{
            inputNumbers.sort(Integer::compareTo);
        }catch (NullPointerException e){
            throw new CannotBuildPyramidException();
        }

        int width = 2 * (int)size - 1;

        return createPyramid(new int[(int)size][width], inputNumbers);
    }


}
