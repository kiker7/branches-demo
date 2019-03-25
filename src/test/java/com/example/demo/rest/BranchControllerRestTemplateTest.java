package com.example.demo.rest;

import com.example.demo.data.domain.Branch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BranchControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate template;

    private HttpEntity<Branch> request;

    @Before
    public void setUp() {
        Branch branch = new Branch();
        branch.setBranchId(1L);
        branch.setBranchName("Test branch");
        HttpHeaders headers = new HttpHeaders();
        this.request = new HttpEntity<>(branch, headers);
    }

    @Test
    public void givenAuthRequest_shouldSucceedWith200() {
        ResponseEntity<String> result = template.withBasicAuth("Admin", "adminPass")
                .getForEntity("/branches", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void givenWrongCredentialsRequest_shouldEndWith401() {
        ResponseEntity<String> result = template.withBasicAuth("Admin", "wrongPass")
                .getForEntity("/branches", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void givenWrongRoleRequest_shouldEndWith403() {
        ResponseEntity<String> result = template.withBasicAuth("Client", "clientPass")
                .postForEntity("/branches", this.request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void givenAdminRoleRequest_shouldEndWith200() {
        ResponseEntity<String> result = template.withBasicAuth("Admin", "adminPass")
                .postForEntity("/branches", this.request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}