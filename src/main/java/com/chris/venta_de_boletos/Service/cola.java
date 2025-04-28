package com.chris.venta_de_boletos.Service;

import com.chris.venta_de_boletos.Model.Reportes;

public class cola {

    private int maxSize;
    private Reportes[] queueArray;
    private int front;
    private int rear;
    private int nItems;

    public cola(int size) {
        maxSize = size;
        queueArray = new Reportes[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }

    public void insert(Reportes report) {
        if (isFull()) {
            System.out.println("La cola está llena, no se pueden agregar más reportes.");
            return;
        }
        rear = (rear + 1) % maxSize;
        queueArray[rear] = report;
        nItems++;
    }

    public Reportes remove() {
        if (isEmpty()) {
            System.out.println("La cola está vacía, no hay reportes para remover.");
            return null;
        }
        Reportes temp = queueArray[front];
        front = (front + 1) % maxSize;
        nItems--;
        return temp;
    }

    public Reportes peekFront() {
        if (isEmpty()) {
            System.out.println("La cola está vacía.");
            return null;
        }
        return queueArray[front];
    }

    public boolean isEmpty() {
        return (nItems == 0);
    }

    public boolean isFull() {
        return (nItems == maxSize);
    }

    public int size() {
        return nItems;
    }
}
