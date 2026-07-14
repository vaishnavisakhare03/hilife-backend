package com.example.hilife.service;

import com.example.hilife.dto.CommitteeRequest;
import com.example.hilife.dto.CommitteeResponse;
import java.util.List;

import com.example.hilife.entity.Committee;
import com.example.hilife.entity.Gallery;
import com.example.hilife.entity.AppUser;
import com.example.hilife.exception.EventNotFoundException;
import com.example.hilife.repository.CommitteeRepository;
import com.example.hilife.repository.GalleryRepository;
import com.example.hilife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitteeService{

    private final CommitteeRepository committeeRepository;
    private final UserRepository userRepository;
    private final GalleryRepository galleryRepository;

//    public CommitteeResponse createCommitteeMember(CommitteeRequest request) {
//
//        AppUser user = userRepository.findById(
//                        request.getUserId())
//                .orElseThrow(() ->
//                        new RuntimeException("User not found"));
//
//        Gallery photo = galleryRepository.findById(
//                        request.getPhotoId())
//                .orElseThrow(() ->
//                        new RuntimeException("Photo not found"));
//
//        Committee committee = new Committee();
//
//        committee.setPosition(request.getPosition());
//        committee.setUser(user);
//        committee.setPhoto(photo);
//
//        Committee savedCommittee =
//                committeeRepository.save(committee);
//
//        return mapToResponse(savedCommittee);
//    }

    public CommitteeResponse createCommitteeMember(CommitteeRequest request) {

        AppUser user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Committee committee = new Committee();

        committee.setPosition(request.getPosition());
        committee.setUser(user);

        // Photo is optional
        if (request.getPhotoId() != null) {

            Gallery photo = galleryRepository.findById(request.getPhotoId())
                    .orElseThrow(() ->
                            new RuntimeException("Photo not found"));

            committee.setPhoto(photo);
        }

        Committee savedCommittee =
                committeeRepository.save(committee);

        return mapToResponse(savedCommittee);
    }
    public List<CommitteeResponse> getAllCommitteeMembers() {

        return committeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CommitteeResponse getCommitteeMemberById(Long id) throws Throwable {

        Committee committee = committeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException(
                                "Committee member not found"));

        return mapToResponse(committee);
    }

    public CommitteeResponse updatePhoto(
            Long committeeId,
            Long photoId
    ) {

        Committee committee =
                committeeRepository.findById(committeeId)
                        .orElseThrow(() ->
                                new RuntimeException("Committee member not found"));

        Gallery gallery =
                galleryRepository.findById(photoId)
                        .orElseThrow(() ->
                                new RuntimeException("Photo not found"));

        committee.setPhoto(gallery);

        Committee saved =
                committeeRepository.save(committee);

        return mapToResponse(saved);
    }

    public void deleteCommitteeMember(Long id) {

        committeeRepository.deleteById(id);
    }

    private CommitteeResponse mapToResponse(
            Committee committee) {

        CommitteeResponse.CommitteeResponseBuilder builder =
                CommitteeResponse.builder()
                        .id(committee.getId())
                        .position(committee.getPosition())
                        .userId(committee.getUser().getId())
                        .userName(committee.getUser().getName())
                        .contactNumber(committee.getUser().getPhoneNumber())
                        .tower(committee.getUser().getTower())
                .flatNumber(committee.getUser().getFlatNumber())
                .role(committee.getUser().getRole().name());

        if (committee.getPhoto() != null) {

            builder.photoId(
                    committee.getPhoto().getId()
            );

            builder.photoUrl(
                    committee.getPhoto().getImageUrl()
            );
        }

        return builder.build();
    }
}