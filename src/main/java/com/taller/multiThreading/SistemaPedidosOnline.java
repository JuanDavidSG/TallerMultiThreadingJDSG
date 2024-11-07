package com.taller.multiThreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

// Clase principal que inicia el sistema de pedidos y crea hilos para el procesamiento
public class SistemaPedidosOnline {
    public static void main(String[] args) throws InterruptedException {
        Queue<Pedido> colaPedidos = new ConcurrentLinkedQueue<>(); // Cola de pedidos compartida y segura para múltiples hilos
        
        int numProcesadores = 3;
        List<Thread> threads = new ArrayList<>(); // Esta es una lista para almacenar los hilos creados
        List<ProcesadorPedidos> procesadores = new ArrayList<>(); // Lista para almacenar las instancias de ProcesadorPedidos

        // Aquí se realiza la creación e inicio de los hilos de procesamiento
        for (int i = 0; i < numProcesadores; i++) {
            ProcesadorPedidos procesador = new ProcesadorPedidos(colaPedidos, 
                                                                "Procesador-" + (i + 1));
            Thread thread = new Thread(procesador); // Asocia el procesador con un nuevo hilo
            threads.add(thread); 
            procesadores.add(procesador); // Agrega el procesador a la lista
            thread.start(); // Inicia el hilo de procesamiento
        }

        // Simulación de la llegada de nuevos pedidos cada 500 ms
        for (int i = 1; i <= 10; i++) {
            colaPedidos.offer(new Pedido(i, "Producto-" + i, 100.0 * i));
            Thread.sleep(500);
        }

        Thread.sleep(10000); // Tiempo de espera para permitir que se procesen los pedidos

        // Detiene todos los procesadores
        for (ProcesadorPedidos procesador : procesadores) {
            procesador.detener();
        }

        // Espera a que todos los hilos terminen
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Sistema de procesamiento de pedidos finalizado.");
    }
}