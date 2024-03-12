package org.example.model;

public class PairOfIntegers {
    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    int min;
    int max;

    public PairOfIntegers(String value) {

        String[] array = value.split(",");
        if(array == null || array.length != 2) {
            return;
        }
        setMin(Integer.parseInt(array[0]));
        setMax(Integer.parseInt(array[1]));
    }
}
