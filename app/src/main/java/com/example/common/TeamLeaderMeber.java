package com.example.common;

public class TeamLeaderMeber implements TeamMember{
    private TeamMember teamMember;

    public TeamLeaderMeber(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

    @Override
    public void reviewCode() {
        teamMember.reviewCode();
    }
}
