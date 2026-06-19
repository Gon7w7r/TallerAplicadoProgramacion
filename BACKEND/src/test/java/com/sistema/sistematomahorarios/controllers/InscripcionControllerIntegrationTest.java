// package com.sistema.sistematomahorarios.controllers;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.sistema.sistematomahorarios.dto.InscripcionMultipleRequestDTO;
// import com.sistema.sistematomahorarios.dto.InscripcionRequestDTO;
// import com.sistema.sistematomahorarios.dto.InscripcionResponseDTO;
// import com.sistema.sistematomahorarios.services.InscripcionService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import java.util.Arrays;
// import java.util.List;

// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.anyList;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @ExtendWith(MockitoExtension.class)
// public class InscripcionControllerIntegrationTest {

//     @Mock
//     private InscripcionService inscripcionService;

//     @InjectMocks
//     private InscripcionController inscripcionController;

//     private MockMvc mockMvc;
//     private ObjectMapper objectMapper;
//     private InscripcionRequestDTO inscripcionRequestDTO;
//     private InscripcionMultipleRequestDTO inscripcionMultipleRequestDTO;

//     @BeforeEach
//     void setUp() {
//         objectMapper = new ObjectMapper();
        
//         // Configurar MockMvc con el controller real
//         mockMvc = MockMvcBuilders.standaloneSetup(inscripcionController).build();

//         inscripcionRequestDTO = new InscripcionRequestDTO();
//         inscripcionRequestDTO.setIdAlumno(1);
//         inscripcionRequestDTO.setIdSeccion(1);
//         inscripcionRequestDTO.setIdPeriodo(1);

//         inscripcionMultipleRequestDTO = new InscripcionMultipleRequestDTO();
//         inscripcionMultipleRequestDTO.setIdAlumno(1);
//         inscripcionMultipleRequestDTO.setIdPeriodo(1);
//         inscripcionMultipleRequestDTO.setSecciones(Arrays.asList(1, 2, 3));
//     }

//     // ==================== PRUEBAS PARA inscribir ====================

//     @Test
//     void testInscribir_ConDatosValidos_DevuelveExitoso() throws Exception {
//         when(inscripcionService.inscribir(anyInt(), anyInt(), anyInt()))
//             .thenReturn("Inscripción exitosa");

//         mockMvc.perform(post("/inscripciones/inscribir")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("Inscripción exitosa"));
//     }

//     @Test
//     void testInscribir_CuandoYaInscrito_DevuelveError() throws Exception {
//         when(inscripcionService.inscribir(anyInt(), anyInt(), anyInt()))
//             .thenReturn("El alumno ya está inscrito en esta sección");

//         mockMvc.perform(post("/inscripciones/inscribir")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("El alumno ya está inscrito en esta sección"));
//     }

//     @Test
//     void testInscribir_CuandoSinCupos_DevuelveError() throws Exception {
//         when(inscripcionService.inscribir(anyInt(), anyInt(), anyInt()))
//             .thenReturn("No hay cupos disponibles");

//         mockMvc.perform(post("/inscripciones/inscribir")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("No hay cupos disponibles"));
//     }

//     @Test
//     void testInscribir_CuandoChoqueHorario_DevuelveError() throws Exception {
//         when(inscripcionService.inscribir(anyInt(), anyInt(), anyInt()))
//             .thenReturn("Existe choque de horario");

//         mockMvc.perform(post("/inscripciones/inscribir")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("Existe choque de horario"));
//     }

//     // ==================== PRUEBAS PARA obtenerInscripcionesAlumno ====================

//     @Test
//     void testObtenerInscripcionesAlumno_CuandoTieneInscripciones_DevuelveLista() throws Exception {
//         when(inscripcionService.obtenerPorAlumno(anyInt()))
//             .thenReturn(Arrays.asList(
//                 new InscripcionResponseDTO(1, "Matemáticas", 1),
//                 new InscripcionResponseDTO(2, "Física", 2)
//             ));

//         mockMvc.perform(get("/inscripciones/alumno/{idAlumno}", 1))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").isArray())
//                 .andExpect(jsonPath("$.length()").value(2))
//                 .andExpect(jsonPath("$[0].idAsignatura").value(1))
//                 .andExpect(jsonPath("$[0].nombreAsignatura").value("Matemáticas"))
//                 .andExpect(jsonPath("$[0].idSeccion").value(1));
//     }

//     @Test
//     void testObtenerInscripcionesAlumno_CuandoNoTieneInscripciones_DevuelveListaVacia() throws Exception {
//         when(inscripcionService.obtenerPorAlumno(anyInt()))
//             .thenReturn(List.of());

//         mockMvc.perform(get("/inscripciones/alumno/{idAlumno}", 1))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").isArray())
//                 .andExpect(jsonPath("$.length()").value(0));
//     }

//     // ==================== PRUEBAS PARA inscribirMultiple ====================

//     @Test
//     void testInscribirMultiple_ConDatosValidos_DevuelveResultados() throws Exception {
//         when(inscripcionService.inscribirMultiple(anyInt(), anyList(), anyInt()))
//             .thenReturn(Arrays.asList(
//                 "Sección 1: Inscripción exitosa",
//                 "Sección 2: Inscripción exitosa",
//                 "Sección 3: Inscripción exitosa"
//             ));

//         mockMvc.perform(post("/inscripciones/inscribir-multiple")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionMultipleRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").isArray())
//                 .andExpect(jsonPath("$.length()").value(3));
//     }

//     @Test
//     void testInscribirMultiple_CuandoAlgunaFalla_DevuelveResultadosMixtos() throws Exception {
//         when(inscripcionService.inscribirMultiple(anyInt(), anyList(), anyInt()))
//             .thenReturn(Arrays.asList(
//                 "Sección 1: Inscripción exitosa",
//                 "Sección 2: No hay cupos disponibles",
//                 "Sección 3: Inscripción exitosa"
//             ));

//         mockMvc.perform(post("/inscripciones/inscribir-multiple")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionMultipleRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").isArray())
//                 .andExpect(jsonPath("$.length()").value(3))
//                 .andExpect(jsonPath("$[1]").value("Sección 2: No hay cupos disponibles"));
//     }

//     @Test
//     void testInscribirMultiple_CuandoTodasFallan_DevuelveTodosErrores() throws Exception {
//         when(inscripcionService.inscribirMultiple(anyInt(), anyList(), anyInt()))
//             .thenReturn(Arrays.asList(
//                 "Sección 1: El alumno ya está inscrito en esta sección",
//                 "Sección 2: No hay cupos disponibles"
//             ));

//         mockMvc.perform(post("/inscripciones/inscribir-multiple")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionMultipleRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").isArray())
//                 .andExpect(jsonPath("$.length()").value(2))
//                 .andExpect(jsonPath("$[0]").value("Sección 1: El alumno ya está inscrito en esta sección"))
//                 .andExpect(jsonPath("$[1]").value("Sección 2: No hay cupos disponibles"));
//     }

//     // ==================== PRUEBAS PARA actualizarHorario ====================

//     @Test
//     void testActualizarHorario_ConDatosValidos_DevuelveResultados() throws Exception {
//         when(inscripcionService.actualizarHorario(anyInt(), anyList(), anyInt()))
//             .thenReturn(Arrays.asList(
//                 "Sección 1: Inscripción exitosa",
//                 "Sección 2: Inscripción exitosa"
//             ));

//         mockMvc.perform(put("/inscripciones/actualizar-horario")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionMultipleRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").isArray())
//                 .andExpect(jsonPath("$.length()").value(2));
//     }

//     @Test
//     void testActualizarHorario_CuandoAlgunaFalla_DevuelveResultadosMixtos() throws Exception {
//         when(inscripcionService.actualizarHorario(anyInt(), anyList(), anyInt()))
//             .thenReturn(Arrays.asList(
//                 "Sección 1: Inscripción exitosa",
//                 "Sección 2: No hay cupos disponibles"
//             ));

//         mockMvc.perform(put("/inscripciones/actualizar-horario")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionMultipleRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").isArray())
//                 .andExpect(jsonPath("$.length()").value(2))
//                 .andExpect(jsonPath("$[1]").value("Sección 2: No hay cupos disponibles"));
//     }

//     @Test
//     void testActualizarHorario_CuandoTodasFallan_DevuelveTodosErrores() throws Exception {
//         when(inscripcionService.actualizarHorario(anyInt(), anyList(), anyInt()))
//             .thenReturn(Arrays.asList(
//                 "Sección 1: El alumno ya está inscrito en esta sección",
//                 "Sección 2: Existe choque de horario"
//             ));

//         mockMvc.perform(put("/inscripciones/actualizar-horario")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(inscripcionMultipleRequestDTO)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").isArray())
//                 .andExpect(jsonPath("$.length()").value(2))
//                 .andExpect(jsonPath("$[0]").value("Sección 1: El alumno ya está inscrito en esta sección"))
//                 .andExpect(jsonPath("$[1]").value("Sección 2: Existe choque de horario"));
//     }

//     @Test
//     void testActualizarHorario_CuandoListaVacia_DevuelveListaVacia() throws Exception {
//         InscripcionMultipleRequestDTO requestVacio = new InscripcionMultipleRequestDTO();
//         requestVacio.setIdAlumno(1);
//         requestVacio.setIdPeriodo(1);
//         requestVacio.setSecciones(List.of());

//         when(inscripcionService.actualizarHorario(anyInt(), anyList(), anyInt()))
//             .thenReturn(List.of());

//         mockMvc.perform(put("/inscripciones/actualizar-horario")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(requestVacio)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$").isArray())
//                 .andExpect(jsonPath("$.length()").value(0));
//     }
// }