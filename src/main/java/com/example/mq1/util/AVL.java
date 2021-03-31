package com.example.mq1.util;

public class AVL<T> {

    private T value;
    private int EF = 0; //平衡因子,默认为零

    private AVL<T> leftChild;
    private AVL<T> rightChild;

    public AVL(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getEF() {
        return EF;
    }

    public void setEF(int EF) {
        this.EF = EF;
    }

    public AVL<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(AVL<T> leftChild) {
        this.leftChild = leftChild;
    }

    public AVL<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(AVL<T> rightChild) {
        this.rightChild = rightChild;
    }
}
