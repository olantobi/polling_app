package com.liferon.polls.service;

import com.liferon.polls.model.Poll;
import com.liferon.polls.payload.PagedResponse;
import com.liferon.polls.payload.PollRequest;
import com.liferon.polls.payload.PollResponse;
import com.liferon.polls.payload.VoteRequest;
import com.liferon.polls.security.UserPrincipal;

public interface PollService {
    PagedResponse<PollResponse> getAllPolls(UserPrincipal currentUser, int page, int size);

    PagedResponse<PollResponse> getPollsCreatedBy(String username, UserPrincipal currentUser, int page, int size);

    PagedResponse<PollResponse> getPollsVotedBy(String username, UserPrincipal currentUser, int page, int size);

    Poll createPoll(PollRequest pollRequest);

    PollResponse getPollById(Long pollId, UserPrincipal currentUser);

    PollResponse castVoteAndGetUpdatedPoll(Long pollId, VoteRequest voteRequest, UserPrincipal currentUser);
}
