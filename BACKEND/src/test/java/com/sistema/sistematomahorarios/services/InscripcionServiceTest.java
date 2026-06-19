// package com.sistema.sistematomahorarios.services;

// import com.sistema.sistematomahorarios.entities.*;
// import com.sistema.sistematomahorarios.repositories.*;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.time.LocalTime;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// public class InscripcionServiceTest {

//     @Mock
//     private InscripcionRepository inscripcionRepository;

//     @Mock
//     private SeccionHorarioRepository seccionHorarioRepository;

//     @Mock
//     private SeccionRepository seccionRepository;

//     @Mock
//     private AlumnoRepository alumnoRepository;

//     @Mock
//     private PeriodoRepository periodoRepository;

//     @Mock
//     private EmailService emailService;

//     @InjectMocks
//     private InscripcionService inscripcionService;

//     // Datos de prueba comunes
//     private Alumno alumno;
//     private Seccion seccion;
//     private Periodo periodo;
//     private SeccionHorario seccionHorario1;
//     private SeccionHorario seccionHorario2;

//     @BeforeEach
//     void setUp() {
//         // Crear Usuario
//         Usuario usuario = new Usuario();
//         usuario.setEmail("test@email.com");
//         usuario.setNombre("Test User");
        
//         // Crear Alumno
//         alumno = new Alumno();
//         alumno.setIdAlumno(1);
//         alumno.setUsuario(usuario);

//         // Crear Asignatura
//         Asignatura asignatura = new Asignatura();
//         asignatura.setIdAsignatura(1);
//         asignatura.setNombre("Matemáticas");
        
//         // Crear Seccion con Asignatura
//         seccion = new Seccion();
//         seccion.setIdSeccion(1);
//         seccion.setCupos(30);
//         seccion.setAsignatura(asignatura);  // <-- AGREGAR ESTO
        
//         // Crear Periodo
//         periodo = new Periodo();
//         periodo.setIdPeriodo(1);

//         // Crear Horarios
//         Horario horario1 = new Horario();
//         horario1.setDiaSemana("LUNES");
//         horario1.setHoraInicio(LocalTime.of(8, 0));
//         horario1.setHoraFin(LocalTime.of(10, 0));

//         Horario horario2 = new Horario();
//         horario2.setDiaSemana("LUNES");
//         horario2.setHoraInicio(LocalTime.of(9, 0));
//         horario2.setHoraFin(LocalTime.of(11, 0));

//         // Crear SeccionHorario
//         seccionHorario1 = new SeccionHorario();
//         seccionHorario1.setHorario(horario1);
        
//         seccionHorario2 = new SeccionHorario();
//         seccionHorario2.setHorario(horario2);
//     }

//     // Método helper para invocar hayCruceHoras (privado)
//     private boolean invocarHayCruceHoras(SeccionHorario h1, SeccionHorario h2) throws Exception {
//         java.lang.reflect.Method method = InscripcionService.class.getDeclaredMethod("hayCruceHoras", 
//                 SeccionHorario.class, SeccionHorario.class);
//         method.setAccessible(true);
//         return (boolean) method.invoke(inscripcionService, h1, h2);
//     }

//     // Método helper para invocar esMismoDia (privado)
//     private boolean invocarEsMismoDia(SeccionHorario h1, SeccionHorario h2) throws Exception {
//         java.lang.reflect.Method method = InscripcionService.class.getDeclaredMethod("esMismoDia", 
//                 SeccionHorario.class, SeccionHorario.class);
//         method.setAccessible(true);
//         return (boolean) method.invoke(inscripcionService, h1, h2);
//     }

//     // Método helper para invocar tieneChoqueHorario (privado)
//     private boolean invocarTieneChoqueHorario(Integer idAlumno, Integer idSeccionNueva) throws Exception {
//         java.lang.reflect.Method method = InscripcionService.class.getDeclaredMethod("tieneChoqueHorario", 
//                 Integer.class, Integer.class);
//         method.setAccessible(true);
//         return (boolean) method.invoke(inscripcionService, idAlumno, idSeccionNueva);
//     }

//     @Test
//     void testHayCruceHoras_CuandoHayCruce_DevuelveTrue() throws Exception {
//         // Arrange: horario1 = 8-10, horario2 = 9-11 (hay cruce)
        
//         // Act
//         boolean resultado = invocarHayCruceHoras(seccionHorario1, seccionHorario2);
        
//         // Assert
//         assertTrue(resultado, "Debería detectar cruce de horarios");
//     }

//     @Test
//     void testHayCruceHoras_CuandoNoHayCruce_DevuelveFalse() throws Exception {
//         // Arrange: horario1 = 8-10, horario2 = 10-12 (no hay cruce)
//         Horario horario3 = new Horario();
//         horario3.setDiaSemana("LUNES");
//         horario3.setHoraInicio(LocalTime.of(10, 0));
//         horario3.setHoraFin(LocalTime.of(12, 0));
        
//         SeccionHorario seccionHorario3 = new SeccionHorario();
//         seccionHorario3.setHorario(horario3);
        
//         // Act
//         boolean resultado = invocarHayCruceHoras(seccionHorario1, seccionHorario3);
        
//         // Assert
//         assertFalse(resultado, "No debería detectar cruce cuando los horarios no se solapan");
//     }

//     @Test
//     void testHayCruceHoras_CuandoTerminaJustoCuandoEmpieza_DevuelveFalse() throws Exception {
//         // Arrange: horario1 = 8-10, horario2 = 10-12 (justo cuando termina uno empieza otro)
//         Horario horario3 = new Horario();
//         horario3.setDiaSemana("LUNES");
//         horario3.setHoraInicio(LocalTime.of(10, 0));
//         horario3.setHoraFin(LocalTime.of(12, 0));
        
//         SeccionHorario seccionHorario3 = new SeccionHorario();
//         seccionHorario3.setHorario(horario3);
        
//         // Act
//         boolean resultado = invocarHayCruceHoras(seccionHorario1, seccionHorario3);
        
//         // Assert
//         assertFalse(resultado, "No debería detectar cruce cuando uno termina justo cuando el otro empieza");
//     }
//     @Test
//     void testEsMismoDia_CuandoMismoDia_DevuelveTrue() throws Exception {
//         // Arrange
//         Horario horario1 = new Horario();
//         horario1.setDiaSemana("LUNES");
        
//         Horario horario2 = new Horario();
//         horario2.setDiaSemana("LUNES");
        
//         SeccionHorario sh1 = new SeccionHorario();
//         sh1.setHorario(horario1);
        
//         SeccionHorario sh2 = new SeccionHorario();
//         sh2.setHorario(horario2);
        
//         // Act
//         boolean resultado = invocarEsMismoDia(sh1, sh2);
        
//         // Assert
//         assertTrue(resultado, "Debería devolver true cuando ambos son LUNES");
//     }

//     @Test
//     void testEsMismoDia_CuandoMismoDiaEnMinusculas_DevuelveFalse() throws Exception {
//         // Arrange
//         Horario horario1 = new Horario();
//         horario1.setDiaSemana("lunes");
        
//         Horario horario2 = new Horario();
//         horario2.setDiaSemana("LUNES");
        
//         SeccionHorario sh1 = new SeccionHorario();
//         sh1.setHorario(horario1);
        
//         SeccionHorario sh2 = new SeccionHorario();
//         sh2.setHorario(horario2);
        
//         // Act
//         boolean resultado = invocarEsMismoDia(sh1, sh2);
        
//         // Assert
//         assertFalse(resultado, "Debería devolver false porque es case-sensitive (lunes vs LUNES)");
//     }


//     @Test
// void testTieneChoqueHorario_CuandoHayChoque_DevuelveTrue() throws Exception {
//     // Arrange
//     Integer idAlumno = 1;
//     Integer idSeccionNueva = 2;
    
//     // Mock: horarios de la nueva sección
//     List<SeccionHorario> nuevosHorarios = List.of(seccionHorario1); // 8-10 LUNES
//     when(seccionHorarioRepository.findBySeccionIdSeccion(idSeccionNueva))
//         .thenReturn(nuevosHorarios);
    
//     // Mock: inscripciones actuales del alumno
//     Inscripcion inscripcion = new Inscripcion();
//     Seccion seccionExistente = new Seccion();
//     seccionExistente.setIdSeccion(1);
//     inscripcion.setSeccion(seccionExistente);
//     List<Inscripcion> inscripcionesActuales = List.of(inscripcion);
//     when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//         .thenReturn(inscripcionesActuales);
    
//     // Mock: horarios de la sección existente (que choca)
//     List<SeccionHorario> horariosExistentes = List.of(seccionHorario2); // 9-11 LUNES
//     when(seccionHorarioRepository.findBySeccionIdSeccion(1))
//         .thenReturn(horariosExistentes);
    
//     // Act
//     boolean resultado = invocarTieneChoqueHorario(idAlumno, idSeccionNueva);
    
//     // Assert
//     assertTrue(resultado, "Debería detectar choque de horario");
// }

//     @Test
//     void testTieneChoqueHorario_CuandoNoHayChoque_DevuelveFalse() throws Exception {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idSeccionNueva = 2;
        
//         // Mock: horarios de la nueva sección (8-10 LUNES)
//         List<SeccionHorario> nuevosHorarios = List.of(seccionHorario1);
//         when(seccionHorarioRepository.findBySeccionIdSeccion(idSeccionNueva))
//             .thenReturn(nuevosHorarios);
        
//         // Mock: inscripciones actuales del alumno
//         Inscripcion inscripcion = new Inscripcion();
//         Seccion seccionExistente = new Seccion();
//         seccionExistente.setIdSeccion(1);
//         inscripcion.setSeccion(seccionExistente);
//         List<Inscripcion> inscripcionesActuales = List.of(inscripcion);
//         when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//             .thenReturn(inscripcionesActuales);
        
//         // Mock: horarios de la sección existente (10-12 LUNES - no choca)
//         Horario horario3 = new Horario();
//         horario3.setDiaSemana("LUNES");
//         horario3.setHoraInicio(LocalTime.of(10, 0));
//         horario3.setHoraFin(LocalTime.of(12, 0));
        
//         SeccionHorario seccionHorario3 = new SeccionHorario();
//         seccionHorario3.setHorario(horario3);
        
//         List<SeccionHorario> horariosExistentes = List.of(seccionHorario3);
//         when(seccionHorarioRepository.findBySeccionIdSeccion(1))
//             .thenReturn(horariosExistentes);
        
//         // Act
//         boolean resultado = invocarTieneChoqueHorario(idAlumno, idSeccionNueva);
        
//         // Assert
//         assertFalse(resultado, "No debería detectar choque de horario cuando no se solapan");
//     }

//     @Test
//     void testTieneChoqueHorario_CuandoDiasDiferentes_DevuelveFalse() throws Exception {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idSeccionNueva = 2;
        
//         // Mock: horarios de la nueva sección (8-10 LUNES)
//         List<SeccionHorario> nuevosHorarios = List.of(seccionHorario1);
//         when(seccionHorarioRepository.findBySeccionIdSeccion(idSeccionNueva))
//             .thenReturn(nuevosHorarios);
        
//         // Mock: inscripciones actuales del alumno
//         Inscripcion inscripcion = new Inscripcion();
//         Seccion seccionExistente = new Seccion();
//         seccionExistente.setIdSeccion(1);
//         inscripcion.setSeccion(seccionExistente);
//         List<Inscripcion> inscripcionesActuales = List.of(inscripcion);
//         when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//             .thenReturn(inscripcionesActuales);
        
//         // Mock: horarios de la sección existente (8-10 MARTES - día diferente)
//         Horario horario3 = new Horario();
//         horario3.setDiaSemana("MARTES");
//         horario3.setHoraInicio(LocalTime.of(8, 0));
//         horario3.setHoraFin(LocalTime.of(10, 0));
        
//         SeccionHorario seccionHorario3 = new SeccionHorario();
//         seccionHorario3.setHorario(horario3);
        
//         List<SeccionHorario> horariosExistentes = List.of(seccionHorario3);
//         when(seccionHorarioRepository.findBySeccionIdSeccion(1))
//             .thenReturn(horariosExistentes);
        
//         // Act
//         boolean resultado = invocarTieneChoqueHorario(idAlumno, idSeccionNueva);
        
//         // Assert
//         assertFalse(resultado, "No debería detectar choque cuando son días diferentes");
//     }

//     @Test
//     void testTieneChoqueHorario_CuandoAlumnoSinInscripciones_DevuelveFalse() throws Exception {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idSeccionNueva = 2;
        
//         // Mock: horarios de la nueva sección
//         List<SeccionHorario> nuevosHorarios = List.of(seccionHorario1);
//         when(seccionHorarioRepository.findBySeccionIdSeccion(idSeccionNueva))
//             .thenReturn(nuevosHorarios);
        
//         // Mock: alumno sin inscripciones
//         when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//             .thenReturn(List.of());
        
//         // Act
//         boolean resultado = invocarTieneChoqueHorario(idAlumno, idSeccionNueva);
        
//         // Assert
//         assertFalse(resultado, "No debería haber choque si el alumno no tiene inscripciones");
//     }
//     @Test
//     void testInscribir_CuandoTodoCorrecto_DevuelveExitoso() {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idSeccion = 1;
//         Integer idPeriodo = 1;
        
//         // Agregar Asignatura a la sección
//         Asignatura asignatura = new Asignatura();
//         asignatura.setIdAsignatura(1);
//         seccion.setAsignatura(asignatura);
        
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, idSeccion))
//             .thenReturn(false);
        
//         when(inscripcionRepository.countBySeccionIdSeccion(idSeccion))
//             .thenReturn(5L);
        
//         when(seccionRepository.findById(idSeccion))
//             .thenReturn(Optional.of(seccion));
        
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(any(), any()))
//             .thenReturn(false);
        
//         // Mock para tieneChoqueHorario
//         when(seccionHorarioRepository.findBySeccionIdSeccion(idSeccion))
//             .thenReturn(List.of(seccionHorario1));
//         when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//             .thenReturn(List.of());
        
//         when(alumnoRepository.findById(idAlumno))
//             .thenReturn(Optional.of(alumno));
//         when(periodoRepository.findById(idPeriodo))
//             .thenReturn(Optional.of(periodo));
        
//         when(inscripcionRepository.save(any(Inscripcion.class)))
//             .thenReturn(new Inscripcion());
        
//         // Act
//         String resultado = inscripcionService.inscribir(idAlumno, idSeccion, idPeriodo);
        
//         // Assert
//         assertEquals("Inscripción exitosa", resultado);
//         verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
//     }

//     @Test
//     void testInscribir_CuandoYaInscritoEnSeccion_DevuelveMensajeError() {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idSeccion = 1;
//         Integer idPeriodo = 1;
        
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, idSeccion))
//             .thenReturn(true);
        
//         // Act
//         String resultado = inscripcionService.inscribir(idAlumno, idSeccion, idPeriodo);
        
//         // Assert
//         assertEquals("El alumno ya está inscrito en esta sección", resultado);
//         verify(inscripcionRepository, never()).save(any(Inscripcion.class));
//     }

//     @Test
//     void testInscribir_CuandoYaInscritoEnAsignatura_DevuelveMensajeError() {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idSeccion = 1;
//         Integer idPeriodo = 1;
        
//         // Necesitamos una asignatura para la sección
//         Asignatura asignatura = new Asignatura();
//         asignatura.setIdAsignatura(1);
//         seccion.setAsignatura(asignatura);
        
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, idSeccion))
//             .thenReturn(false);
        
//         when(seccionRepository.findById(idSeccion))
//             .thenReturn(Optional.of(seccion));
        
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(idAlumno, 1))
//             .thenReturn(true);
        
//         // Act
//         String resultado = inscripcionService.inscribir(idAlumno, idSeccion, idPeriodo);
        
//         // Assert
//         assertEquals("El alumno ya está inscrito en esta asignatura", resultado);
//         verify(inscripcionRepository, never()).save(any(Inscripcion.class));
//     }

//     @Test
//     void testInscribir_CuandoNoHayCupos_DevuelveMensajeError() {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idSeccion = 1;
//         Integer idPeriodo = 1;
        
//         // Agregar Asignatura a la sección
//         Asignatura asignatura = new Asignatura();
//         asignatura.setIdAsignatura(1);
//         seccion.setAsignatura(asignatura);
        
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, idSeccion))
//             .thenReturn(false);
        
//         when(inscripcionRepository.countBySeccionIdSeccion(idSeccion))
//             .thenReturn(30L);
        
//         when(seccionRepository.findById(idSeccion))
//             .thenReturn(Optional.of(seccion));
        
//         // Act
//         String resultado = inscripcionService.inscribir(idAlumno, idSeccion, idPeriodo);
        
//         // Assert
//         assertEquals("No hay cupos disponibles", resultado);
//         verify(inscripcionRepository, never()).save(any(Inscripcion.class));
//     }

//     @Test
//     void testInscribir_CuandoHayChoqueHorario_DevuelveMensajeError() {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idSeccion = 1;
//         Integer idPeriodo = 1;
        
//         // Necesitamos una asignatura para la sección
//         Asignatura asignatura = new Asignatura();
//         asignatura.setIdAsignatura(1);
//         seccion.setAsignatura(asignatura);
        
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, idSeccion))
//             .thenReturn(false);
        
//         when(inscripcionRepository.countBySeccionIdSeccion(idSeccion))
//             .thenReturn(5L);
        
//         when(seccionRepository.findById(idSeccion))
//             .thenReturn(Optional.of(seccion));
        
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(any(), any()))
//             .thenReturn(false);
        
//         // Mock para tieneChoqueHorario = true
//         when(seccionHorarioRepository.findBySeccionIdSeccion(idSeccion))
//             .thenReturn(List.of(seccionHorario1));
        
//         Inscripcion inscripcion = new Inscripcion();
//         Seccion seccionExistente = new Seccion();
//         seccionExistente.setIdSeccion(2);
//         inscripcion.setSeccion(seccionExistente);
//         List<Inscripcion> inscripcionesActuales = List.of(inscripcion);
//         when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//             .thenReturn(inscripcionesActuales);
        
//         when(seccionHorarioRepository.findBySeccionIdSeccion(2))
//             .thenReturn(List.of(seccionHorario2));
        
//         // Act
//         String resultado = inscripcionService.inscribir(idAlumno, idSeccion, idPeriodo);
        
//         // Assert
//         assertEquals("Existe choque de horario", resultado);
//         verify(inscripcionRepository, never()).save(any(Inscripcion.class));
//     }

//     @Test
//     void testActualizarHorario_CuandoTodoCorrecto_DevuelveResultados() {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idPeriodo = 1;
//         List<Integer> seccionesNuevas = List.of(1, 2);
        
//         // Crear Sala
//         Sala sala = new Sala();
//         sala.setIdSala(1);
//         sala.setNombre("Sala A-101");
        
//         // Crear Asignatura
//         Asignatura asignatura = new Asignatura();
//         asignatura.setIdAsignatura(1);
//         asignatura.setNombre("Matemáticas");
        
//         // Crear inscripciones actuales
//         Inscripcion inscripcionActual1 = new Inscripcion();
//         Seccion seccionActual1 = new Seccion();
//         seccionActual1.setIdSeccion(999);
//         seccionActual1.setAsignatura(asignatura);
//         seccionActual1.setSala(sala);
//         inscripcionActual1.setSeccion(seccionActual1);
        
//         Inscripcion inscripcionActual2 = new Inscripcion();
//         Seccion seccionActual2 = new Seccion();
//         seccionActual2.setIdSeccion(888);
//         seccionActual2.setAsignatura(asignatura);
//         seccionActual2.setSala(sala);
//         inscripcionActual2.setSeccion(seccionActual2);
        
//         List<Inscripcion> inscripcionesActuales = List.of(inscripcionActual1, inscripcionActual2);
        
//         // ✅ TODOS los stubs con lenient()
//         lenient().when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//             .thenReturn(inscripcionesActuales);
        
//         // Horarios MARTES para evitar choque
//         Horario horarioMartes = new Horario();
//         horarioMartes.setDiaSemana("MARTES");
//         horarioMartes.setHoraInicio(LocalTime.of(8, 0));
//         horarioMartes.setHoraFin(LocalTime.of(10, 0));
        
//         SeccionHorario seccionHorarioMartes = new SeccionHorario();
//         seccionHorarioMartes.setHorario(horarioMartes);
        
//         lenient().when(seccionHorarioRepository.findBySeccionIdSeccion(999))
//             .thenReturn(List.of(seccionHorarioMartes));
//         lenient().when(seccionHorarioRepository.findBySeccionIdSeccion(888))
//             .thenReturn(List.of(seccionHorarioMartes));
        
//         // Mock para nuevas inscripciones
//         for (Integer idSeccion : seccionesNuevas) {
//             lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, idSeccion))
//                 .thenReturn(false);
//             lenient().when(inscripcionRepository.countBySeccionIdSeccion(idSeccion))
//                 .thenReturn(5L);
            
//             Seccion seccionMock = new Seccion();
//             seccionMock.setIdSeccion(idSeccion);
//             seccionMock.setCupos(30);
//             seccionMock.setAsignatura(asignatura);
//             seccionMock.setSala(sala);
//             lenient().when(seccionRepository.findById(idSeccion))
//                 .thenReturn(Optional.of(seccionMock));
            
//             lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(any(), any()))
//                 .thenReturn(false);
            
//             lenient().when(seccionHorarioRepository.findBySeccionIdSeccion(idSeccion))
//                 .thenReturn(List.of(seccionHorario1));
            
//             lenient().when(alumnoRepository.findById(idAlumno))
//                 .thenReturn(Optional.of(alumno));
//             lenient().when(periodoRepository.findById(idPeriodo))
//                 .thenReturn(Optional.of(periodo));
            
//             lenient().when(inscripcionRepository.save(any(Inscripcion.class)))
//                 .thenReturn(new Inscripcion());
//         }
        
//         // Mock: emailService
//         doNothing().when(emailService).enviarCorreoInscripcion(any(), any(), any());
        
//         // Act
//         List<String> resultados = inscripcionService.actualizarHorario(idAlumno, seccionesNuevas, idPeriodo);
        
//         // Assert
//         assertNotNull(resultados);
//         assertEquals(2, resultados.size());
//         assertTrue(resultados.get(0).contains("Inscripción exitosa"));
//         assertTrue(resultados.get(1).contains("Inscripción exitosa"));
        
//         verify(inscripcionRepository, times(1)).deleteAllInBatch(any());
//         verify(inscripcionRepository, times(1)).flush();
//         verify(emailService, times(1)).enviarCorreoInscripcion(any(), any(), any());
//     }
//     @Test
//     void testActualizarHorario_CuandoAlgunaFalla_DevuelveMensajesMixtos() {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idPeriodo = 1;
//         List<Integer> seccionesNuevas = List.of(1, 2);
        
//         // Necesitamos una asignatura para la sección
//         Asignatura asignatura = new Asignatura();
//         asignatura.setIdAsignatura(1);
//         seccion.setAsignatura(asignatura);
        
//         // Mock: inscripciones actuales
//         List<Inscripcion> inscripcionesActuales = List.of(new Inscripcion());
//         when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//             .thenReturn(inscripcionesActuales);
        
//         // Mock: sección 1 exitosa
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, 1))
//             .thenReturn(false);
//         when(inscripcionRepository.countBySeccionIdSeccion(1))
//             .thenReturn(5L);
        
//         Seccion seccion1 = new Seccion();
//         seccion1.setIdSeccion(1);
//         seccion1.setCupos(30);
//         seccion1.setAsignatura(asignatura);
//         when(seccionRepository.findById(1))
//             .thenReturn(Optional.of(seccion1));
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(any(), any()))
//             .thenReturn(false);
//         when(seccionHorarioRepository.findBySeccionIdSeccion(1))
//             .thenReturn(List.of(seccionHorario1));
//         when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//             .thenReturn(List.of());
//         when(alumnoRepository.findById(idAlumno))
//             .thenReturn(Optional.of(alumno));
//         when(periodoRepository.findById(idPeriodo))
//             .thenReturn(Optional.of(periodo));
//         when(inscripcionRepository.save(any(Inscripcion.class)))
//             .thenReturn(new Inscripcion());
        
//         // Mock: sección 2 falla por cupos llenos
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, 2))
//             .thenReturn(false);
//         when(inscripcionRepository.countBySeccionIdSeccion(2))
//             .thenReturn(30L);
        
//         Seccion seccion2 = new Seccion();
//         seccion2.setIdSeccion(2);
//         seccion2.setCupos(30);
//         seccion2.setAsignatura(asignatura);
//         when(seccionRepository.findById(2))
//             .thenReturn(Optional.of(seccion2));
        
//         // Act
//         List<String> resultados = inscripcionService.actualizarHorario(idAlumno, seccionesNuevas, idPeriodo);
        
//         // Assert
//         assertNotNull(resultados);
//         assertEquals(2, resultados.size());
//         assertTrue(resultados.get(0).contains("Inscripción exitosa"));
//         assertTrue(resultados.get(1).contains("No hay cupos disponibles"));
        
//         verify(emailService, times(1)).enviarCorreoInscripcion(any(), any(), any());
//     }

//     @Test
//     void testActualizarHorario_CuandoNoHayInscripcionesExitosas_NoEnviaCorreo() {
//         // Arrange
//         Integer idAlumno = 1;
//         Integer idPeriodo = 1;
//         List<Integer> seccionesNuevas = List.of(1);
        
//         // Mock: inscripciones actuales
//         List<Inscripcion> inscripcionesActuales = List.of(new Inscripcion());
//         when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
//             .thenReturn(inscripcionesActuales);
        
//         // Mock: sección falla (sin cupos)
//         when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, 1))
//             .thenReturn(false);
//         when(inscripcionRepository.countBySeccionIdSeccion(1))
//             .thenReturn(30L);
        
//         // Crear sección con Asignatura
//         Asignatura asignatura = new Asignatura();
//         asignatura.setIdAsignatura(1);
        
//         Seccion seccion = new Seccion();
//         seccion.setIdSeccion(1);
//         seccion.setCupos(30);
//         seccion.setAsignatura(asignatura);
//         when(seccionRepository.findById(1))
//             .thenReturn(Optional.of(seccion));
        
//         // Act
//         List<String> resultados = inscripcionService.actualizarHorario(idAlumno, seccionesNuevas, idPeriodo);
        
//         // Assert
//         assertNotNull(resultados);
//         assertEquals(1, resultados.size());
//         assertTrue(resultados.get(0).contains("No hay cupos disponibles"));
        
//         verify(emailService, never()).enviarCorreoInscripcion(any(), any(), any());
//     }

    // @Test
    // void testInscribirMultiple_CuandoTodasExitosas_DevuelveResultadosExitosos() {
    //     // Arrange
    //     Integer idAlumno = 1;
    //     Integer idPeriodo = 1;
    //     List<Integer> secciones = List.of(1, 2, 3);
        
    //     // Mock para cada sección - todas exitosas
    //     for (Integer idSeccion : secciones) {
    //         // ✅ Usar lenient() para todos los stubs
    //         lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, idSeccion))
    //             .thenReturn(false);
    //         lenient().when(inscripcionRepository.countBySeccionIdSeccion(idSeccion))
    //             .thenReturn(5L);
            
    //         Asignatura asignatura = new Asignatura();
    //         asignatura.setIdAsignatura(1);
            
    //         Seccion seccionMock = new Seccion();
    //         seccionMock.setIdSeccion(idSeccion);
    //         seccionMock.setCupos(30);
    //         seccionMock.setAsignatura(asignatura);
    //         lenient().when(seccionRepository.findById(idSeccion))
    //             .thenReturn(Optional.of(seccionMock));
            
    //         lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(any(), any()))
    //             .thenReturn(false);
            
    //         lenient().when(seccionHorarioRepository.findBySeccionIdSeccion(idSeccion))
    //             .thenReturn(List.of(seccionHorario1));
    //         lenient().when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
    //             .thenReturn(List.of());
            
    //         lenient().when(alumnoRepository.findById(idAlumno))
    //             .thenReturn(Optional.of(alumno));
    //         lenient().when(periodoRepository.findById(idPeriodo))
    //             .thenReturn(Optional.of(periodo));
            
    //         lenient().when(inscripcionRepository.save(any(Inscripcion.class)))
    //             .thenReturn(new Inscripcion());
    //     }
        
    //     // Mock: emailService
    //     doNothing().when(emailService).enviarCorreoInscripcion(any(), any(), any());
        
    //     // Act
    //     List<String> resultados = inscripcionService.inscribirMultiple(idAlumno, secciones, idPeriodo);
        
    //     // Assert
    //     assertNotNull(resultados);
    //     assertEquals(3, resultados.size());
    //     assertTrue(resultados.get(0).contains("Inscripción exitosa"));
    //     assertTrue(resultados.get(1).contains("Inscripción exitosa"));
    //     assertTrue(resultados.get(2).contains("Inscripción exitosa"));
        
    //     // Verificar que se envió el correo
    //     verify(emailService, times(1)).enviarCorreoInscripcion(any(), any(), any());
    //     verify(inscripcionRepository, times(3)).save(any(Inscripcion.class));
    // }

    // @Test
    // void testInscribirMultiple_CuandoAlgunaFalla_DevuelveResultadosMixtos() {
    //     // Arrange
    //     Integer idAlumno = 1;
    //     Integer idPeriodo = 1;
    //     List<Integer> secciones = List.of(1, 2, 3);
        
    //     Asignatura asignatura = new Asignatura();
    //     asignatura.setIdAsignatura(1);
        
    //     // Sección 1: Exitosa
    //     lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, 1))
    //         .thenReturn(false);
    //     lenient().when(inscripcionRepository.countBySeccionIdSeccion(1))
    //         .thenReturn(5L);
        
    //     Seccion seccion1 = new Seccion();
    //     seccion1.setIdSeccion(1);
    //     seccion1.setCupos(30);
    //     seccion1.setAsignatura(asignatura);
    //     lenient().when(seccionRepository.findById(1))
    //         .thenReturn(Optional.of(seccion1));
    //     lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(any(), any()))
    //         .thenReturn(false);
    //     lenient().when(seccionHorarioRepository.findBySeccionIdSeccion(1))
    //         .thenReturn(List.of(seccionHorario1));
    //     lenient().when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
    //         .thenReturn(List.of());
    //     lenient().when(alumnoRepository.findById(idAlumno))
    //         .thenReturn(Optional.of(alumno));
    //     lenient().when(periodoRepository.findById(idPeriodo))
    //         .thenReturn(Optional.of(periodo));
    //     lenient().when(inscripcionRepository.save(any(Inscripcion.class)))
    //         .thenReturn(new Inscripcion());
        
    //     // Sección 2: Falla por cupos llenos
    //     lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, 2))
    //         .thenReturn(false);
    //     lenient().when(inscripcionRepository.countBySeccionIdSeccion(2))
    //         .thenReturn(30L);
        
    //     Seccion seccion2 = new Seccion();
    //     seccion2.setIdSeccion(2);
    //     seccion2.setCupos(30);
    //     seccion2.setAsignatura(asignatura);
    //     lenient().when(seccionRepository.findById(2))
    //         .thenReturn(Optional.of(seccion2));
        
    //     // Sección 3: Exitosa
    //     lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, 3))
    //         .thenReturn(false);
    //     lenient().when(inscripcionRepository.countBySeccionIdSeccion(3))
    //         .thenReturn(5L);
        
    //     Seccion seccion3 = new Seccion();
    //     seccion3.setIdSeccion(3);
    //     seccion3.setCupos(30);
    //     seccion3.setAsignatura(asignatura);
    //     lenient().when(seccionRepository.findById(3))
    //         .thenReturn(Optional.of(seccion3));
    //     lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(any(), any()))
    //         .thenReturn(false);
    //     lenient().when(seccionHorarioRepository.findBySeccionIdSeccion(3))
    //         .thenReturn(List.of(seccionHorario1));
    //     lenient().when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
    //         .thenReturn(List.of());
    //     lenient().when(alumnoRepository.findById(idAlumno))
    //         .thenReturn(Optional.of(alumno));
    //     lenient().when(periodoRepository.findById(idPeriodo))
    //         .thenReturn(Optional.of(periodo));
    //     lenient().when(inscripcionRepository.save(any(Inscripcion.class)))
    //         .thenReturn(new Inscripcion());
        
    //     // Mock: emailService
    //     doNothing().when(emailService).enviarCorreoInscripcion(any(), any(), any());
        
    //     // Act
    //     List<String> resultados = inscripcionService.inscribirMultiple(idAlumno, secciones, idPeriodo);
        
    //     // Assert
    //     assertNotNull(resultados);
    //     assertEquals(3, resultados.size());
    //     assertTrue(resultados.get(0).contains("Inscripción exitosa"));
    //     assertTrue(resultados.get(1).contains("No hay cupos disponibles"));
    //     assertTrue(resultados.get(2).contains("Inscripción exitosa"));
        
    //     verify(emailService, times(1)).enviarCorreoInscripcion(any(), any(), any());
    //     verify(inscripcionRepository, times(2)).save(any(Inscripcion.class));
    // }

    // @Test
    // void testInscribirMultiple_CuandoTodasFallan_NoEnviaCorreo() {
    //     // Arrange
    //     Integer idAlumno = 1;
    //     Integer idPeriodo = 1;
    //     List<Integer> secciones = List.of(1, 2);
        
    //     // Sección 1: Falla por ya inscrito
    //     lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, 1))
    //         .thenReturn(true);
        
    //     // Sección 2: Falla por cupos llenos
    //     lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, 2))
    //         .thenReturn(false);
    //     lenient().when(inscripcionRepository.countBySeccionIdSeccion(2))
    //         .thenReturn(30L);
        
    //     Asignatura asignatura = new Asignatura();
    //     asignatura.setIdAsignatura(1);
        
    //     Seccion seccion2 = new Seccion();
    //     seccion2.setIdSeccion(2);
    //     seccion2.setCupos(30);
    //     seccion2.setAsignatura(asignatura);
    //     lenient().when(seccionRepository.findById(2))
    //         .thenReturn(Optional.of(seccion2));
        
    //     // Act
    //     List<String> resultados = inscripcionService.inscribirMultiple(idAlumno, secciones, idPeriodo);
        
    //     // Assert
    //     assertNotNull(resultados);
    //     assertEquals(2, resultados.size());
    //     assertTrue(resultados.get(0).contains("El alumno ya está inscrito en esta sección"));
    //     assertTrue(resultados.get(1).contains("No hay cupos disponibles"));
        
    //     verify(emailService, never()).enviarCorreoInscripcion(any(), any(), any());
    //     verify(inscripcionRepository, never()).save(any(Inscripcion.class));
    // }

    // @Test
    // void testInscribirMultiple_CuandoListaVacia_DevuelveListaVacia() {
    //     // Arrange
    //     Integer idAlumno = 1;
    //     Integer idPeriodo = 1;
    //     List<Integer> secciones = List.of();
        
    //     // Act
    //     List<String> resultados = inscripcionService.inscribirMultiple(idAlumno, secciones, idPeriodo);
        
    //     // Assert
    //     assertNotNull(resultados);
    //     assertTrue(resultados.isEmpty());
        
    //     verify(emailService, never()).enviarCorreoInscripcion(any(), any(), any());
    //     verify(inscripcionRepository, never()).save(any(Inscripcion.class));
    // }

    // @Test
    // void testInscribirMultiple_CuandoUnaSeccionFallaPorChoque_DevuelveMensajeError() {
    //     // Arrange
    //     Integer idAlumno = 1;
    //     Integer idPeriodo = 1;
    //     List<Integer> secciones = List.of(1);
        
    //     // Sección 1: Falla por choque de horario
    //     lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, 1))
    //         .thenReturn(false);
    //     lenient().when(inscripcionRepository.countBySeccionIdSeccion(1))
    //         .thenReturn(5L);
        
    //     Asignatura asignatura = new Asignatura();
    //     asignatura.setIdAsignatura(1);
        
    //     Seccion seccion1 = new Seccion();
    //     seccion1.setIdSeccion(1);
    //     seccion1.setCupos(30);
    //     seccion1.setAsignatura(asignatura);
    //     lenient().when(seccionRepository.findById(1))
    //         .thenReturn(Optional.of(seccion1));
    //     lenient().when(inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(any(), any()))
    //         .thenReturn(false);
        
    //     // Mock para tieneChoqueHorario = true
    //     lenient().when(seccionHorarioRepository.findBySeccionIdSeccion(1))
    //         .thenReturn(List.of(seccionHorario1));
        
    //     Inscripcion inscripcion = new Inscripcion();
    //     Seccion seccionExistente = new Seccion();
    //     seccionExistente.setIdSeccion(2);
    //     inscripcion.setSeccion(seccionExistente);
    //     List<Inscripcion> inscripcionesActuales = List.of(inscripcion);
    //     lenient().when(inscripcionRepository.findByAlumnoIdAlumno(idAlumno))
    //         .thenReturn(inscripcionesActuales);
        
    //     lenient().when(seccionHorarioRepository.findBySeccionIdSeccion(2))
    //         .thenReturn(List.of(seccionHorario2));
        
    //     // Act
    //     List<String> resultados = inscripcionService.inscribirMultiple(idAlumno, secciones, idPeriodo);
        
    //     // Assert
    //     assertNotNull(resultados);
    //     assertEquals(1, resultados.size());
    //     assertTrue(resultados.get(0).contains("Existe choque de horario"));
        
    //     verify(emailService, never()).enviarCorreoInscripcion(any(), any(), any());
    //     verify(inscripcionRepository, never()).save(any(Inscripcion.class));
    // }
    
// }