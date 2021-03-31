package com.example.mq1.util;

public class AVLProducer<T> {

    private AVL<T> root;

    public AVL<T> createAVLRoot(T value){
        root = new AVL<T>(value);
        return root;
    }

    /**
     * 前序遍历
     */
    public void preOrderTraverse(){
        this.preOrderTraverse(this.root);
    }

    /**
     * 前序遍历的实际实现算法
     * @param root
     * @return
     */
    private int preOrderTraverse(AVL<T> root){
        if (!EmptyUtils.isEmpty(root.getLeftChild())){
            preOrderTraverse(root.getLeftChild());
        }
        System.out.print(root.getValue());
        if (!EmptyUtils.isEmpty(root.getRightChild())){
            preOrderTraverse(root.getRightChild());
        }
        return 0;
    }

    /**
     * 中序遍历
     */
    public void inOrderTraverse(){
        this.inOrderTraverse(this.root);
    }

    /**
     * 中序遍历的实际实现算法
     * @param root
     * @return
     */
    private int inOrderTraverse(AVL<T> root){
        System.out.print(root.getValue());
        if (!EmptyUtils.isEmpty(root.getLeftChild())){
            inOrderTraverse(root.getLeftChild());
        }
        if (!EmptyUtils.isEmpty(root.getRightChild())){
            inOrderTraverse(root.getRightChild());
        }
        return 0;
    }

    /**
     * 添加一个新的节点
     * @param newNode
     * @return
     */
    public int push(AVL<T> newNode){
        pushNewNode(this.root, newNode);
        return 0;
    }

    public int push(T value){
        pushNewNode(this.root, new AVL<T>(value));
        return 0;
    }

    /**
     * 添加节点的实际实现递归算法
     * @param root
     * @param newNode
     */
    private void pushNewNode(AVL<T> root, AVL<T> newNode){
        if ((Integer)newNode.getValue() > (Integer) root.getValue()){
            if (EmptyUtils.isEmpty(root.getRightChild())){
                root.setRightChild(newNode);
            }else {
                pushNewNode(root.getRightChild(), newNode);
            }
        }else {
            if (EmptyUtils.isEmpty(root.getLeftChild())){
                root.setLeftChild(newNode);
            }else {
                pushNewNode(root.getLeftChild(), newNode);
            }
        }
    }


    /**
     * 删除一个指定的节点
     * @param specify
     * @return
     */
    public int pop(AVL<T> specify){

        return 0;
    }


    public int getDepth(AVL<T> node){
        if (EmptyUtils.isEmpty(node))return 0;

        int max = 0;
        int maxL = 0;
        int maxR = 0;

        if (!EmptyUtils.isEmpty(node.getLeftChild()))maxL = getDepth(node.getLeftChild());
        if (!EmptyUtils.isEmpty(node.getRightChild()))maxR = getDepth(node.getRightChild());

        if (maxL > maxR)max = maxL + 1;
        else max = maxR + 1;

        return max;
    }
}
