package com.example.common;

public class MyLinkedList<T> {

    // 头节点
    private final Node<T> head;

    // 尾插法
    private Node<T> tail;

    public Node<T> getHead() {
        return head;
    }


    public Node<T> getTail() {
        return tail;
    }


    public MyLinkedList() {
        head = new Node<>();
        head.next = null;
        tail = head;
    }

    /**
     * 尾部插入法
     *
     * @param value 插入元素
     */
    public  void tailInsert(T value) {
        Node<T> node = new Node<>();
        node.value = value;
        node.next = null;

        if (isEmpty()) {
            head.next = node;
        } else {
            tail.next = node;
        }

        tail = node;
    }

    /**
     * 头部插入法
     *
     * @param value 插入元素
     */
    public void headInsert(T value) {
        Node<T> node = new Node<>();
        node.value = value;
        node.next = null;

        if (isEmpty()) {
            head.next = node;
            tail = node;
        } else {
            node.next = head.next;
            head.next = node;
        }
    }

    /**
     *
     * @param i 下标
     * @return 下标位置
     */
    public Node<T> getValue(int i) {
        if (isEmpty()) {
            return null;
        }
        Node<T> next = head.next;
        int index = 0;
        while (next != null) {
            if (index == i) {
                return next;
            }
            index ++;
            next = next.next;
        }
        return null;
    }

    /**
     *
     * @param value 链表内元素值
     * @return -1代表未找到
     */
    public int getValueIndex(T value) {
        if (isEmpty()) {
            return -1;
        }
        Node<T> next = head.next;
        int index = 0;
        while (next != null) {
            if (next.value == value) {
                return index;
            }
            index ++;
            next = next.next;
        }
        return -1;
    }

    /**
     * 根据位置删除元素
     *
     * @param index 删除位置
     * @return 返回-1删除失败越界 1删除成功
     */
    public int deleteIndex(int index) {
        if (isEmpty()) {
            return -1;
        }

        int noWindex = 0;
        Node<T> node = head;
        while (node != null) {
            if (noWindex == (index-1)) {
                Node<T> target = node.next;
                if (target == null) {
                    return -1;
                }

                node.next = node.next.next;
                if (target.next == null) {
                    tail = node;
                }

                return 1;
            }
            noWindex++;
            node = node.next;
        }
        return -1;
    }

    public int deleteValue(T value) {
        if (isEmpty()){
            return -1;
        }

        Node<T> node = head;
        while (node != null) {
            Node<T> next = node.next;
            if (next.value == value) {
                if (next.next == null) {
                    tail = node;
                    return 1;
                }
                node.next = node.next.next;
                return 1;
            }
            node = node.next;
        }
        return -1;

    }

    public int linkSize() {
        int size = 0;
        Node<T> next = head;
        while (next != null) {
            if (next.next != null) {
                size++;
            }
            next = next.next;
        }
        return size;
    }

    public boolean isEmpty() {
        return head.next == null;
    }

    public static class Node<T> {
        private T value;
        private Node<T> next;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}
