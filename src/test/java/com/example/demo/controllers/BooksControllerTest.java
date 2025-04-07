package com.example.demo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.DemoApplication;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = DemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBooks () throws Exception {
        mockMvc.perform(get("/books")
                            .param("page", "0")
                            .param("size", "5")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content.length()").value(5))
            .andExpect(jsonPath("$.data.content[0].id").value(1))
            .andExpect(jsonPath("$.data.content[0].bookName").value("Book 1"))
            .andExpect(jsonPath("$.data.content[0].author").value("Author 1"))
            .andExpect(jsonPath("$.data.content[0].price").value(19.99))
            .andExpect(jsonPath("$.data.content[0].createdDate").value("2025-04-01T10:00:00"))
            .andExpect(jsonPath("$.data.content[0].updatedDate").value("2025-04-01T10:00:00"))
            .andExpect(jsonPath("$.data.content[0].deleted").value(false))
            .andExpect(jsonPath("$.data.content[1].bookName").value("Book 2"))
            .andExpect(jsonPath("$.data.content[2].bookName").value("Book 3"));
    }

}
