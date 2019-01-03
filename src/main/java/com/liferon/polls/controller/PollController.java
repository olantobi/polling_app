package com.liferon.polls.controller;

import com.liferon.polls.model.Poll;
import com.liferon.polls.payload.ApiResponse;
import com.liferon.polls.payload.PagedResponse;
import com.liferon.polls.payload.PollRequest;
import com.liferon.polls.payload.PollResponse;
import com.liferon.polls.repository.PollRepository;
import com.liferon.polls.repository.UserRepository;
import com.liferon.polls.repository.VoteRepository;
import com.liferon.polls.security.CurrentUser;
import com.liferon.polls.security.UserPrincipal;
import com.liferon.polls.service.PollService;
import com.liferon.polls.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/polls")
@Slf4j
@RequiredArgsConstructor
public class PollController {

    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    private PollService pollService;

    //private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @GetMapping("/")
    public PagedResponse<PollResponse> getPolls(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        return pollService.getAllPolls(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest) {
        Poll poll = pollService.createPoll(pollRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pollId}")
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Poll Created Successfully"));
    }

    @GetMapping("/{pollId}")
    public PollResponse getPollById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long pollId) {

        return pollService.getPollById(pollId, currentUser);
    }
}
