package com.example.demo.services;

import com.example.demo.data.domain.Branch;

import java.util.List;
import java.util.Optional;


public interface BranchService {

    List<Branch> getAll();

    Branch addBranch(Branch branch);

    Optional<Branch> getBranch(long id);

    Branch updateBranch(long id, Branch branch);

    void deleteBranch(long id);
}
