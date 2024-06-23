package com.task.watch_catalogue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WatchCatalogueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCheckoutIntegrationSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[\n" +
                        "\"001\",\n" +
                        "\"001\",\n" +
                        "\"001\",\n" +
                        "\"004\",\n" +
                        "\"003\",\n" +
                        "\"006\",\n" +
                        "\"007\",\n" +
                        "\"009\",\n" +
                        "\"\",\n" +
                        "\"002\",\n" +
                        "\"002\"\n" +
                        "]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("450.0"));

    }

    @Test
    public void testCheckoutIntegrationNullInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void testCheckoutIntegrationEmptyInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("0"));

    }
}
