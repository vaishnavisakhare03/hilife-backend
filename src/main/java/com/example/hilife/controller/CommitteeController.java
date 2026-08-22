package com.example.hilife.controller;

import com.example.hilife.dto.CommitteeRequest;
import com.example.hilife.dto.CommitteeResponse;
import com.example.hilife.service.CommitteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/committee")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommitteeController {

    private final CommitteeService committeeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CommitteeResponse createCommitteeMember(
            @RequestBody CommitteeRequest request) {

        return committeeService
                .createCommitteeMember(request);
    }

    @GetMapping
    public List<CommitteeResponse> getAllCommitteeMembers() {

        return committeeService
                .getAllCommitteeMembers();
    }

    @GetMapping("/{id}")
    public CommitteeResponse getCommitteeMember(
            @PathVariable Long id) throws Throwable {

        return committeeService
                .getCommitteeMemberById(id);
    }

    @PutMapping("/{committeeId}/photo/{photoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public CommitteeResponse updatePhoto(
            @PathVariable Long committeeId,
            @PathVariable Long photoId
    ) {
        return committeeService.updatePhoto(
                committeeId,
                photoId
        );
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCommitteeMember(
            @PathVariable Long id
    ) {

        committeeService.deleteCommitteeMember(id);

        return ResponseEntity.noContent().build();
    }
}