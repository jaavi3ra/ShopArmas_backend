---
name: pruebas-unitarias-mockito
description: Crea, actualiza y ejecuta pruebas unitarias para servicios Java con JUnit 5 y Mockito. Úsala cuando se pidan pruebas unitarias para la lógica de negocio.
---

# Pruebas unitarias con Mockito

1. Lee el servicio, sus dependencias y las pruebas existentes. Usa el paquete y las convenciones del módulo. Crea o amplía `<NombreServicio>Test` bajo `src/test/java`.
2. Usa `@ExtendWith(MockitoExtension.class)`, `@Mock` para repositorios y otras dependencias, y `@InjectMocks` para el servicio real. Ejecuta las pruebas sin iniciar Spring, Docker ni una base de datos.
3. Organiza cada prueba con `// GIVEN`, `// WHEN` y `// THEN`. Usa nombres como `iniciarEstado_DeberiaCrearEstadoInicial`.
4. Comprueba el resultado con aserciones de JUnit 5. Usa `verify(...)` para interacciones relevantes, como guardar un registro. Si necesitas inspeccionar los campos enviados al repositorio, usa `ArgumentCaptor`. Configura `when(...).thenReturn(...)` solo si el escenario necesita esa respuesta.
5. Cubre el caso exitoso y los errores o límites que formen parte del comportamiento del servicio. Para excepciones, usa `assertThrows`.
6. Ejecuta las pruebas con el wrapper Maven o Gradle del proyecto. Informa qué pruebas ejecutaste y su resultado. Si una prueba revela un fallo del servicio, repórtalo antes de cambiar el código de producción.

## Ejemplo de estilo

Usa este ejemplo como referencia. Adapta las aserciones y la configuración del mock al contrato real del servicio.

```java
@ExtendWith(MockitoExtension.class)
class EstadoMascotaServiceTest {

    @Mock
    private EstadoMascotaRepository estadoMascotaRepository;

    @InjectMocks
    private EstadoMascotaService estadoMascotaService;

    @Test
    void iniciarEstado_DeberiaCrearEstadoInicial() {
        // GIVEN
        Mascota mascota = new Mascota();
        mascota.setNombre("efe");

        // WHEN
        EstadoMascota estado = estadoMascotaService.iniciarEstado(mascota);

        // THEN
        assertNotNull(estado);
        assertEquals(100, estado.getHambre());
        assertEquals(100, estado.getEnergia());
        assertEquals(100, estado.getSalud());
        assertEquals(100, estado.getFelicidad());
        verify(estadoMascotaRepository).save(estado);
    }
}
```

Si `iniciarEstado` devuelve directamente el resultado de `save`, configura el mock para devolver el objeto recibido. En ese caso, puedes usar `when(estadoMascotaRepository.save(any(EstadoMascota.class))).thenAnswer(invocation -> invocation.getArgument(0))` en el `GIVEN`.
