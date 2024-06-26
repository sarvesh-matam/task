package com.task.watch_catalogue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WatchCatalogueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCheckoutIntegrationWithoutDiscount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        [
                        "001",
                        "004",
                        "003",
                        "002"
                        ]"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("260.0"));

    }

    @Test
    void testCheckoutIntegrationWithNullInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void testCheckoutIntegrationWithEmptyInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("0"));

    }

    @Test
    void testCheckoutIntegrationWithDiscount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                "001",
                                "001",
                                "001",
                                "004",
                                "003",
                                "002",
                                "002"
                                ]"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("450.0"));
    }

    @Test
    void testCheckoutIntegrationWithEmptyId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                "001",
                                "001",
                                "001",
                                "004",
                                "",
                                "002",
                                "002"
                                ]"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("400.0"));
    }

    @Test
    void testCheckoutIntegrationWithUnavailableId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                "001",
                                "001",
                                "001",
                                "004",
                                "009",
                                "002",
                                "002"
                                ]"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("400.0"));
    }
}
