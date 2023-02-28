package com.gis.gesP;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gis.gesP.controller.TeacherController;
import com.gis.gesP.exception.InvalidRequestException;
import com.gis.gesP.exception.RessourceNotFoundExeption;
import com.gis.gesP.model.Teacher;
import com.gis.gesP.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    TeacherRepository teacherRepository;

    Teacher teacher1 = new Teacher((long) 1, "Tchalong", "15B2", "D", "");
    Teacher teacher2 = new Teacher((long) 2, "Angoh", "02B2", "A", "");
    Teacher teacher3 = new Teacher((long) 3, "Ulrich", "10B2", "F", "");

    // ... Test methods TBA
    @Test
    public void getAllTeacher_success() throws Exception {
        List<Teacher> Teachers = new ArrayList<>(Arrays.asList(teacher1, teacher2, teacher3));

        Mockito.when(teacherRepository.findAll()).thenReturn(Teachers);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name").value("Ulrich"));
    }

    @Test
    public void getTeachertById_success() throws Exception {
        Mockito.when(teacherRepository.findById(teacher1.getId())).thenReturn(java.util.Optional.of(teacher1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/teacher/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("Tchalong"));
    }

    @Test
    public void createTeacher_success() throws Exception {
        Teacher teacher = Teacher.builder()
                .name("John Doe")
                .matricule("47b5")
                .grade("A")
                .subject("")
                .build();

        Mockito.when(teacherRepository.save(teacher)).thenReturn(teacher);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(teacher));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void updatePatientRecord_success() throws Exception {
        Teacher updatedTeacher = Teacher.builder()
                .id(1L)
                .name("Rayven Zambo")
                .matricule("47b5")
                .grade("A")
                .subject("")
                .build();

        Mockito.when(teacherRepository.findById(teacher1.getId())).thenReturn(Optional.of(teacher1));
        Mockito.when(teacherRepository.save(updatedTeacher)).thenReturn(updatedTeacher);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedTeacher));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("Rayven Zambo"));
    }

    @Test
    public void updatePatientRecord_nullId() throws Exception {
        Teacher updatedTeacher = Teacher.builder()
                .id(4L)
                .name("John Doe")
                .matricule("47b5")
                .grade("A")
                .subject("")
                .build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedTeacher));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result ->
                        assertEquals("Teacher or ID must not be null!", result.getResolvedException().getMessage()));
    }

    @Test
    public void updatePatientRecord_recordNotFound() throws Exception {
        Teacher updatedTeacher = Teacher.builder()
                .id(5L)
                .name("John Doe")
                .matricule("47b5")
                .grade("A")
                .subject("")
                .build();

        Mockito.when(teacherRepository.findById(updatedTeacher.getId())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedTeacher));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof RessourceNotFoundExeption))
                .andExpect(result ->
                        assertEquals("Teacher with ID 5 does not exist.", result.getResolvedException().getMessage()));
    }
}
