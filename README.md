En mi implementación, usé la estrategia Runnable en combinación con Thread para manejar múltiples hilos, lo cual es adecuado para tener un control directo sobre cada procesador de pedidos. Cada ProcesadorPedidos implementa Runnable y se ejecuta en un hilo propio, permitiendo iniciar y detener hilos de forma manual, lo cual es útil en un sistema pequeño como este. 