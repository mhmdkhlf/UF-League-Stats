package org.example.messages;

public record TeamStanding(String teamName, int wins, int draws, int losses, int goalsFor, int goalAgainst, int goalDifference, int points) {
}
