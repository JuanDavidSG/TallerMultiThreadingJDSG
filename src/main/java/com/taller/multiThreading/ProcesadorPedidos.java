package com.taller.multiThreading;

import java.util.Queue;

// Clase que implementa Runnable y gestiona el procesamiento de pedidos en un hilo separado
public class ProcesadorPedidos implements Runnable {
    private final Queue<Pedido> colaPedidos;// Cola de pedidos compartida
    private final String nombreProcesador; // Nombre del procesador para identificarlo en los logs
    private volatile boolean ejecutando = true; // Controla el ciclo de procesamiento
 
    // Constructor que inicializa la cola y el nombre del procesador
    public ProcesadorPedidos(Queue<Pedido> colaPedidos, String nombreProcesador) {
        this.colaPedidos = colaPedidos;
        this.nombreProcesador = nombreProcesador;
    }

    // Método run que define el trabajo que realizará el hilo
    @Override
    public void run() {
        while (ejecutando) {
            Pedido pedido = colaPedidos.poll(); // Extrae un pedido de la cola si está disponible
            if (pedido != null) {
                procesarPedido(pedido);
            } else {
                try {
                    Thread.sleep(1000); // Pausa para evitar sobrecargar la CPU
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restablece el estado de interrupción
                    break;
                }
            }
        }
    }

    // Método para procesar un pedido específico
    private void procesarPedido(Pedido pedido) {
        try {
            System.out.printf("%s: Procesando %s%n", 
                            nombreProcesador, pedido.toString());
            Thread.sleep(2000); // Simula el tiempo de procesamiento del pedido
            pedido.setEstado("PROCESADO");
            System.out.printf("%s: Completado %s%n", 
                            nombreProcesador, pedido.toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            pedido.setEstado("ERROR");
        }
    }

    // Método para detener el procesamiento, finalizando el ciclo en run()
    public void detener() {
        ejecutando = false; // Cambia "ejecutando" a false para salir del ciclo
    }
}