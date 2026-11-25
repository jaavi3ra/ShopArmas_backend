package com.shoparmas.gta.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoparmas.gta.DTO.CategoriaDTO;
import com.shoparmas.gta.model.Categoria;
import com.shoparmas.gta.repository.CategoriaRepository;
import com.shoparmas.gta.service.CategoriaService;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService service;

    @Test
    void obtenerCategoriaPorId_DeberiaMapearEntidad() {
        // GIVEN
        Categoria entity = entidadExistente();
        when(categoriaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        CategoriaDTO resultado = service.obtenerCategoriaPorId(7);

        // THEN
        assertEquals(7, resultado.getIdCategoria());
        assertEquals("Equipamiento", resultado.getNombreCategoria());
    }

    @Test
    void obtenerCategoriaPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(categoriaRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerCategoriaPorId(7));

        // THEN
        assertEquals("Categoría no encontrada con ID: 7", error.getMessage());
    }

    @Test
    void eliminarCategoria_DeberiaEliminarEntidadExistente() {
        // GIVEN
        Categoria entity = entidadExistente();
        when(categoriaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarCategoria(7);

        // THEN
        verify(categoriaRepository).delete(entity);
    }

    @Test
    void crearCategoria_DeberiaGuardarDatosDeLaSolicitud() {
        // GIVEN
        CategoriaDTO solicitud = new CategoriaDTO();
        solicitud.setNombreCategoria("Accesorios");
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        CategoriaDTO resultado = service.crearCategoria(solicitud);

        // THEN
        ArgumentCaptor<Categoria> captor = ArgumentCaptor.forClass(Categoria.class);
        verify(categoriaRepository).save(captor.capture());
        assertEquals("Accesorios", captor.getValue().getNombreCategoria());
        assertEquals("Accesorios", resultado.getNombreCategoria());
    }

    @Test
    void actualizarCategoria_DeberiaGuardarCambios() {
        // GIVEN
        Categoria entity = entidadExistente();
        when(categoriaRepository.findById(7)).thenReturn(Optional.of(entity));
        CategoriaDTO solicitud = new CategoriaDTO();
        solicitud.setNombreCategoria("Accesorios");
        when(categoriaRepository.save(entity)).thenReturn(entity);

        // WHEN
        CategoriaDTO resultado = service.actualizarCategoria(7, solicitud);

        // THEN
        verify(categoriaRepository).save(entity);
        assertEquals(7, resultado.getIdCategoria());
        assertEquals("Accesorios", resultado.getNombreCategoria());
    }

    private Categoria entidadExistente() {
        Categoria entity = new Categoria();
        entity.setIdCategoria(7);
        entity.setNombreCategoria("Equipamiento");
        return entity;
    }
}
