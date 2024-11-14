package br.com.baba.api_product.api.controller;

import br.com.baba.api_product.api.dto.TransactionDTO;
import br.com.baba.api_product.api.enums.OperationTypeEnum;
import br.com.baba.api_product.api.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<TransactionDTO> jsonTransactionDTO;

    @MockBean
    private TransactionService transactionService;

    @Mock
    private TransactionDTO transactionDTO;

    @Test
    @WithMockUser
    void shouldReturnStatus200() throws Exception {
        transactionDTO = new TransactionDTO(1L, 1L, BigDecimal.TWO, OperationTypeEnum.OUTPUT);

        var response = mockMvc.perform(post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTransactionDTO.write(transactionDTO).getJson()))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser
    void shouldReturnStatus400WhenMissingField() throws Exception {
        String json = """
                {
                	"userId": 1,
                	"quantity": 50,
                	"operation": "OUTPUT"
                }
                """;
        var response = mockMvc.perform(post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }
}