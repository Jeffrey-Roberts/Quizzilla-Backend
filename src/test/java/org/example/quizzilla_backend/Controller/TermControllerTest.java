package org.example.quizzilla_backend.Controller;

import org.example.quizzilla_backend.Model.Term;
import org.example.quizzilla_backend.Service.TermService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// https://hyperskill.org/learn/step/34605#related-topics
// https://hyperskill.org/learn/step/34892
// https://www.baeldung.com/mockito-exceptions
// https://hyperskill.org/learn/step/27497#implementing-put
@WebMvcTest(TermController.class)
class TermControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TermService termService;

    @Test
    @WithMockUser
    public void postTerm_ValidInput_ReturnsCreated() throws Exception {
        String termName = "test";
        String termDesc = "description";
        Term term = new Term();
        term.setName(termName);
        term.setDescription(termDesc);
        when(termService.createTerm(any(String.class), any(String.class))).thenReturn(term);

        String requestBody = "{\"name\":\"" + termName + "\", \"description\":\"" + termDesc + "\"}";

        mockMvc.perform(post("/term").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(termName))
                .andExpect(jsonPath("$.description").value(termDesc));
    }

    @Test
    @WithMockUser
    public void postTerm_EmptyInput_ReturnsBadRequest() throws Exception {
        String termName = "";
        String termDesc = "";
        String requestBody = "{\"name\":\"" + termName + "\", \"description\":\"" + termDesc + "\"}";

        when(termService.createTerm(eq(""), eq(""))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/term").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void getTerms_ReturnsOK() throws Exception {
        Term term = new Term();
        List<Term> terms = List.of(term, term, term);
        when(termService.getAllTerms()).thenReturn(terms);

        mockMvc.perform(get("/terms").with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(terms.size()));
    }

    @Test
    @WithMockUser
    public void deleteTerm_TermExists_ReturnsNoContent() throws Exception {
        Long termId = 1L;
        Term term = new Term();
        term.setId(termId);
        doNothing().when(termService).deleteTerm(termId);

        mockMvc.perform(delete("/term/{id}", termId).with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void deleteTerm_NoTermExists_ReturnsNotFound() throws Exception {
        Long id = 1L;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(termService).deleteTerm(id);

        mockMvc.perform(delete("/term/{id}", id).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void updateTerm_ValidInput_ReturnsOk() throws Exception {
        Long termId = 1L;
        String termName = "test";
        String termDescription = "description";
        Term updatedTerm = new Term();
        updatedTerm.setName(termName);
        updatedTerm.setDescription(termDescription);

        when(termService.updateTerm(eq(termId), eq(termName), eq(termDescription))).thenReturn(updatedTerm);

        String requestBody = "{\"name\":\"" + termName + "\", \"description\":\"" + termDescription + "\"}";

        mockMvc.perform(put("/term/{id}", termId).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(termName))
                .andExpect(jsonPath("$.description").value(termDescription));
    }

    @Test
    @WithMockUser
    public void updateTerm_InvalidInput_ReturnsBadRequest() throws Exception {
        Long termId = 1L;
        String termName = "";
        String termDescription = "";

        when(termService.updateTerm(eq(termId), eq(""), eq(""))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String requestBody = "{\"name\":\"" + termName + "\", \"description\":\"" + termDescription + "\"}";

        mockMvc.perform(put("/term/{id}", termId).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void updateTerm_NoTermExists_ReturnsNotFound() throws Exception {
        Long termId = 1L;
        String termName = "";
        String termDescription = "";

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(termService).updateTerm(termId, termName, termDescription);

        String requestBody = "{\"name\":\"" + termName + "\", \"description\":\"" + termDescription + "\"}";

        mockMvc.perform(put("/term/{id}", termId).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound());
    }
}