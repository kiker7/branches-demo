package com.example.demo.services.impl;

import com.example.demo.data.domain.Branch;
import com.example.demo.services.BranchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BranchServiceImpl implements BranchService {

    @Value("${my.json.file}")
    private String jsonFilePath;

    private Map<Long, Branch> branches;

    @PostConstruct
    private void loadData() {

        List<Branch> list;
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Branch>> mapType = new TypeReference<List<Branch>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream(jsonFilePath);

        try {
            list = mapper.readValue(inputStream, mapType);
            this.branches = list.stream()
                    .collect(Collectors.toConcurrentMap(Branch::getBranchId, x -> x, (oldValue, newValue) -> newValue));
            log.info("Data initialization completed");
        } catch (IOException e) {
            log.error("Data initialization error:" + e.getMessage());
            this.branches = new ConcurrentHashMap<Long, Branch>();
        }
    }

    @Override
    public List<Branch> getAll() {
        return new ArrayList<>(this.branches.values());
    }

    @Override
    public Branch addBranch(Branch branch) {
        this.branches.put(branch.getBranchId(), branch);
        return branch;
    }

    @Override
    public Optional<Branch> getBranch(long id) {
        return Optional.ofNullable(this.branches.get(id));
    }

    @Override
    public Branch updateBranch(long id, Branch branch) {
        this.branches.put(id, branch);
        return branch;
    }

    @Override
    public void deleteBranch(long id) {
        this.branches.remove(id);
    }
}
