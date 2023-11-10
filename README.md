# Event Sourcing Kata

El objetivo de esta kata es practicar Event Sourcing a través de un ejemplo con código dirigido por test para familiarizarnos con esta estrategía de persistencia y sus implicaciones en el diseño/arquitectura de nuestro código, fundamentalmente del diseño de los agregados.

El ejemplo consiste en modelar una casa de subastas online, partiremos de un modelado de eventos donde hemos descubierto los siguientes eventos:

- Creación de la subasta: Descripción del item a subastar y precio inicial de partida.
- Puja: Cantidad de dinero pujada
- Cierre de la subasta: Puede ser cerrada con un ganador o haber quedado desierta

Pasos: Primera parte de la kata

- Crear un auction y guardar/recuperar el auction persistiendo en un almacen de eventos
- Permitir que se pueda pujar en el auction
- Permitir que se puede cerrar la subasta

Pasos: Segunda parte de la kata
    
- Añadir información de la persona que puja (sólo el nombre)

Pasos: Tercera parte de la kata

- Crear una snapshot a partir de un número de eventos