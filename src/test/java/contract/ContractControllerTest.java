package contract;

import com.owczarczak.footballers.contract.ContractRepository;
import com.owczarczak.footballers.footballer.FootballerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractControllerTest {

    @Autowired
    ContractRepository repository;

    @Autowired
    MockMvc mockMvc;

//    @BeforeEach


    @Test
    void shouldGetAllContracts() {

    }


}

